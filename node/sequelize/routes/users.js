'use strict';
var express = require('express');
var router = express.Router();

var Sequelize = require('sequelize-yugabytedb');
var models = require('../models');

// GET users listing
router.get('/', function(req, res, next) {
  models.users
      .findAll()
      .then(users => {
        var responseBody = {
          content:[]
        }
        users.forEach(user => {
          responseBody.content.push(user.dataValues);
        });

        res.send(responseBody);
      })
      .catch(err => {
        console.error('Error: ', err);
        res.status(500).send(err);
      });
});

// create a user
router.post('/', (req, res, next) => {
  models.sequelize.transaction({isolationLevel: Sequelize.Transaction.ISOLATION_LEVELS.SERIALIZABLE}, t => {
    return models.users.create(req.body, {transaction: t})
  })
  .then(result => {
    res.send(result);
  })
  .catch(err => {
    console.error('Error: ', err);
    res.status(500).send(err);
  });
});

module.exports = router;
