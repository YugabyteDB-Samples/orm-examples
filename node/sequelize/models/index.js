require('dotenv').config();

'use strict';

/**
 * Smart‑driver aware Sequelize bootstrap for YugabyteDB.
 *
 * Configuration via environment variables (in order of precedence):
 * 
 * 1. Standard PostgreSQL variables:
 *    - PGHOST: Database host (e.g., "127.0.0.1")
 *    - PGPORT: Database port (default: 5433)
 * 
 *    - PGUSER: Database user (default: yugabyte)
 *    - PGPASSWORD: Database password (default: yugabyte)
 *    - PGDATABASE: Database name (default: ysql_sequelize)
 * 
 * 2. Environment variables for the load balancing properties:
 *    - PGLOADBALANCE: Enable load balancing (Valid values: any, only-primary, prefer-primary, prefer-rr, only-rr)
 *    - PGTOPOLOGYKEYS: Specify the node placements to target (regions/zones) (e.g., "aws.us-east-1.us-east-1a")
 *    - PGFALLBACKTOTOPOLOGYKEYSONLY: Fallback to topology keys only (default: false)
 *    - PGYBSERVERSREFRESHINTERVAL: Metadata refresh interval in seconds (default: 5)
 *    - PGFAILEDHOSTRECONNECTDELAYSECS: Delay in reconnecting to failed hosts, in seconds (default: 5)
 */

const fs = require('fs');
const path = require('path');
const sequelizePkg = require('sequelize-yugabytedb');

const { Sequelize } = sequelizePkg;

const basename = path.basename(__filename);
const env = process.env.NODE_ENV || 'development';
const baseConfig = require(path.join(__dirname, '..', 'config', 'config.json'))[
  env
];

const db = {};

const DEFAULT_YB_PORT = 5433;

const normalizeBooleanEnv = value =>
  typeof value === 'string' ? value.toLowerCase() === 'true' : Boolean(value);

const parseHosts = rawHosts =>
  rawHosts
    .split(',')
    .map(entry => entry.trim())
    .filter(Boolean)
    .map(entry => {
      const [host, rawPort] = entry.split(':');
      return {
        host,
        port: Number(rawPort) || DEFAULT_YB_PORT,
      };
    });

const HOSTS = (() => {
  // Priority: PGHOST/PGPORT env vars > config.json > default 3-node setup
  if (process.env.PGHOST) {
    const port = process.env.PGPORT || DEFAULT_YB_PORT;
    return parseHosts(`${process.env.PGHOST}:${port}`);
  }

  let hosts;
  if (baseConfig.host) {
    // config.json can specify single host or comma-separated multi-host for load balancing
    // Examples: "127.0.0.1" or "127.0.0.1:5433,127.0.0.2:5433,127.0.0.3:5433"
    const hostSpec =
      baseConfig.port && !String(baseConfig.host).includes(':')
        ? `${baseConfig.host}:${baseConfig.port}`
        : baseConfig.host;
    hosts = parseHosts(hostSpec);
  } else {
    // Default to the local 3-node setup (127.0.0.[1-3]:5433) provisioned in docs.
    hosts = parseHosts('127.0.0.1:5433,127.0.0.2:5433,127.0.0.3:5433');
  }

  // PGPORT env var overrides all port values
  if (process.env.PGPORT) {
    const overridePort = Number(process.env.PGPORT);
    hosts = hosts.map(h => ({ ...h, port: overridePort }));
  }

  return hosts;
})();

// Load balance mode: any, prefer-primary, prefer-rr, only-primary, only-rr
const READ_LOAD_BALANCE_MODE = process.env.PGLOADBALANCE || "any";

// Optional topology awareness and driver tuning (only set if needed)
const TOPOLOGY_KEYS = process.env.PGTOPOLOGYKEYS || "";
const FALLBACK_TO_TOPOLOGY_KEYS_ONLY = normalizeBooleanEnv(process.env.PGFALLBACKTOTOPOLOGYKEYSONLY) || false;
const SERVER_REFRESH_INTERVAL = process.env.PGYBSERVERSREFRESHINTERVAL ? Number(process.env.PGYBSERVERSREFRESHINTERVAL) : 5;
const FAILED_HOST_RECONNECT_DELAY_SECS = process.env.PGFAILEDHOSTRECONNECTDELAYSECS ? Number(process.env.PGFAILEDHOSTRECONNECTDELAYSECS) : 5;

// Utility function to set environment variables if not already set
// The @yugabytedb/pg driver reads these directly from process.env
const ensureEnv = (key, value) => {
  if (
    typeof value !== 'undefined' &&
    value !== null &&
    value !== '' &&
    !process.env[key]
  ) {
    process.env[key] = String(value);
  }
};

// Set environment variables for @yugabytedb/pg driver
ensureEnv('PGLOADBALANCE', READ_LOAD_BALANCE_MODE);
ensureEnv('PGTOPOLOGYKEYS', TOPOLOGY_KEYS);
ensureEnv(
  'PGFALLBACKTOTOPOLOGYKEYSONLY', 
  FALLBACK_TO_TOPOLOGY_KEYS_ONLY ? 'true' : undefined,
);
ensureEnv('PGYBSERVERSREFRESHINTERVAL', SERVER_REFRESH_INTERVAL);
ensureEnv(
  'PGFAILEDHOSTRECONNECTDELAYSECS',
  FAILED_HOST_RECONNECT_DELAY_SECS,
);

function createSmartSequelizeInstance() {
  const [primaryHost] = HOSTS;

  const username = process.env.PGUSER || baseConfig.username || 'yugabyte';
  const password = process.env.PGPASSWORD || (baseConfig.password && baseConfig.password !== '' ? baseConfig.password : 'yugabyte');
  const database = process.env.PGDATABASE || baseConfig.database || 'ysql_sequelize';

  const logLevel = (process.env.LOG_LEVEL || '').toLowerCase();
  
  const loggingType =
    logLevel === 'silly'
      ? console.log
      : false;

  // Use YugabyteDB smart driver for automatic load balancing
  // Driver reads config from process.env/environment variables (set via ensureEnv) and discovers all nodes
  return new Sequelize(database, username, password, {
    host: primaryHost.host,
    port: primaryHost.port,
    dialect: 'postgres',
    logging: loggingType,
    pool: {
      max: 10,
      min: 2,
      idle: 10000,
      acquire: 30000,
    },
    dialectOptions: {
      statement_timeout: 60000,
    },
  });
}

// Alternative method: Using Connection String
function createSequelizeWithConnectionString() {
  const username = process.env.PGUSER || baseConfig.username || 'yugabyte';
  const password = process.env.PGPASSWORD || (baseConfig.password && baseConfig.password !== '' ? baseConfig.password : 'yugabyte');
  const database = process.env.PGDATABASE || baseConfig.database || 'ysql_sequelize';
  
  // Use first host for simple connection
  const { host, port } = HOSTS[0];
  
  // Build connection string with load balance parameters
  const connectionString = 
    `postgres://${encodeURIComponent(username)}:${encodeURIComponent(password)}@${host}:${port}/${database}` +
    `?loadBalance=${READ_LOAD_BALANCE_MODE}` +
    (TOPOLOGY_KEYS ? `&topologyKeys=${encodeURIComponent(TOPOLOGY_KEYS)}` : '') +
    `&ybServersRefreshInterval=${SERVER_REFRESH_INTERVAL}`;
  
  const logLevel = (process.env.LOG_LEVEL || '').toLowerCase();
  const loggingType = logLevel === 'silly' ? console.log : false;
  
  return new Sequelize(connectionString, {
    dialect: 'postgres',
    logging: loggingType,
    pool: {
      max: 10,
      min: 2,
      idle: 10000,
      acquire: 30000,
    },
    dialectOptions: {
      statement_timeout: 60000,
    },
  });
}

// Choose which method to use:
const sequelize = createSmartSequelizeInstance();
// Alternative: Uncomment to use connection string instead:
// const sequelize = createSequelizeWithConnectionString();

fs
  .readdirSync(__dirname)
  .filter(file => {
    return (
      file.indexOf('.') !== 0 &&
      file !== basename &&
      file.slice(-3) === '.js'
    );
  })
  .forEach(file => {
    // const model = sequelize['import'](path.join(__dirname, file));
    // REASON for not using above line of code: sequelize.import has been
    // deprecated. See: https://github.com/sequelize/sequelize/pull/12175
    const model = require(path.join(__dirname, file))(
      sequelize,
      sequelizePkg.DataTypes,
    );
    db[model.name] = model;
  });

Object.keys(db).forEach(modelName => {
  if (db[modelName].associate) {
    db[modelName].associate(db);
  }
});

db.sequelize = sequelize;
db.Sequelize = Sequelize;

module.exports = db;
