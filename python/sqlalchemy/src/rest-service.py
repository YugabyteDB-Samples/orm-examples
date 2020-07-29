
import logging
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship
from http.server import HTTPServer, BaseHTTPRequestHandler
import json
from io import BytesIO
import jsonpickle
from model import User, Order, OrderLine, Product
import config as cfg
from data_access_util import DataAccessUtil
from sqlalchemy import create_engine
from sqlalchemy import MetaData
from model import Base
import sys
import traceback


logging.getLogger('sqlalchemy.engine.base.Engine').setLevel(logging.WARNING)


def init():

    engine = create_engine('postgresql://{0}:{1}@{2}:{3}/postgres'.format(cfg.db_user, cfg.db_password, cfg.db_host, cfg.db_port))                            

    existing_databases = engine.execute("SELECT datname FROM pg_database;")
    # Results are a list of single item tuples, so unpack each tuple
    existing_databases = [d[0] for d in existing_databases]

    created_database = False
    # Create database if not exists
    if cfg.database not in existing_databases:
        conn = engine.connect()
        conn.execute('commit')
        conn.execute("CREATE DATABASE {0};".format(cfg.database))
        logging.debug("Created database {0}".format(cfg.database))
        created_database = True

    # Go ahead and use this engine
    engine = create_engine('postgresql://{0}:{1}@{2}:{3}/{4}'.format(cfg.db_user, cfg.db_password, cfg.db_host, cfg.db_port, cfg.database))                            

    if created_database == True:
        conn = engine.connect()
        conn.execute('commit')
        conn.execute("CREATE SCHEMA {0}".format(cfg.schema))

    Session = sessionmaker(ddwqbind=engine)
    Base.metadata.create_all(engine)


engine = create_engine('postgresql://{0}:{1}@{2}:{3}/postgres'.format(cfg.db_user, cfg.db_password, cfg.db_host, cfg.db_port))                            
Session = sessionmaker(ddwqbind=engine)

metadata = MetaData(bind=engine)

def add_object(session, to_add):
    session.add(to_add)
    logging.debug('Added: %s' % str(to_add) )


def add_objects(session, object_list):
    session.add_all(object_list)
    logging.debug('Added (list): ')
    [logging.debug(str(obj)) for obj in object_list]


class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):

    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'json')
        self.end_headers()

    def _return_error(self):
        self.send_response(400)

    def do_GET(self):
        logging.debug('do_Get called with path: %s' % self.path)
        dao = DataAccessUtil()

        if self.path == '/users':
            self.handle_list_users(dao)

        if self.path == '/products':
            self.handle_list_products(dao)

        if self.path == '/list-products':
            self.handle_list_all_products(dao)

    def do_POST(self):

        logging.debug('>> do_POST')
        content_length = int(self.headers['Content-Length'])
        body = json.loads(self.rfile.read(content_length))

        logging.debug('body: ' % body)
        dao = DataAccessUtil()

        logging.debug('doPost called with url: %s and data: %s' % (self.path, body))

        try:
            if self.path == '/users':
                self.handle_create_user(dao, body)

            if self.path == '/orders':
                self.handle_create_order(dao, body)

            if self.path == '/list-orders':
                self.handle_list_orders(dao, body)

            if self.path == '/products':
                self.handle_create_product(dao, body)

            if self.path == '/list-products':
                self.handle_list_all_products(dao)
        except: 
            self._return_error()

    # =======================================================
    ## Handlers below
    # =======================================================

    def handle_create_user(self, dao, body):
        try:
            self.handle_write_output(            
                dao.add_object(User(first_name=body['firstName'], last_name=body['lastName'], email=body['email'])))
        except:
            traceback.print_exc()
            

    def handle_list_users(self, dao):
        self.handle_write_output(dao.get_users())

    def handle_list_all_products(self, dao):
        self.handle_write_output(
            dao.list_products()
        )

    def handle_create_product(self, dao, body):
        self.handle_write_output(
            dao.create_product(body)
        )

    def handle_list_orders(self, dao, body):
        self.handle_write_output(
            dao.list_orders_for_user(body['userId'])
        )

    def handle_list_products(self, dao, body):
        self.handle_write_output(
            dao.list_products()
        )
        

    def handle_write_output(self, results_json):
        self._set_headers()
        response = BytesIO()
        response.write(jsonpickle.encode(results_json).encode())
        self.wfile.write(response.getvalue())

    def handle_create_order(self, dao, body):
        if 'userId' not in body:
            logging.debug('UserId required!')
            # TODO: Return an error here!

        self.handle_write_output(dao.create_order(body['userId'], body['products']))


init()
# ### HTTP Server
httpd = HTTPServer(('localhost', cfg.listen_port), SimpleHTTPRequestHandler)
logging.info('>>> Listening on port %s' % cfg.listen_port)
httpd.serve_forever()



