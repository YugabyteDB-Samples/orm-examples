'use strict';
var express = require('express');
var router = express.Router();
const { PrismaPg } = require('@yugabytedb/prisma-adapter');
const { PrismaClient } = require('@prisma/client');

const connectionString = `${process.env.DATABASE_URL}`

const adapter = new PrismaPg({ connectionString })

const prisma = new PrismaClient({ adapter })

router.get('/', function(req, res, next) {
    prisma.user
        .findMany()
        .then((users) => {
            var response = {
                content:[]
              }
            users.forEach(user => {
            response.content.push(user);
            });
      
            res.send(response);
        })
        .catch(err => {
            console.error('Error: ', err);
            res.status(500).send(err);
        });
});


router.post('/', function(req, res, next) {
    prisma.user
        .create({
            data: req.body
        })
        .then(userCreated => {
            res.send(userCreated);
        })
        .catch(err => {
            console.error('Error: ', err);
            res.status(500).send(err);
        });
});

module.exports = router