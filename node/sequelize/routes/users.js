var express = require('express');
var router = express.Router();


const Sequelize = require('sequelize');
var models = require('../models');

/* GET users listing. */
router.get('/', function(req, res, next) {
  models.users
      .findAll()
      .then(users => {
        responseBody = {
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
  models.users
      .create(req.body)
      .then(user => {
        res.send(user);
      })
      .catch(err => {
        console.error('Error: ', err);
        res.status(500).send(err);
      })
});

module.exports = router;
