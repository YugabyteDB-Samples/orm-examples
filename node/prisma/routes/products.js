'use strict';
var express = require('express');
var router = express.Router();
const { PrismaClient }  = require('@prisma/client')

const prisma = new PrismaClient({
  log: ['query', 'info', 'warn', 'error'],
})

router.get('/', function(req, res, next) {
    prisma.product
        .findMany()
        .then((products) => {
            var response = {
                content:[]
              }
            products.forEach(product => {
            response.content.push(product);
            });
      
            res.send(response);
        })
        .catch(err => {
            console.error('Error: ', err);
            res.status(500).send(err);
        });
});


router.post('/', function(req, res, next) {
    prisma.product
        .create({
            data: req.body
        })
        .then(productCreated => {
            res.send(productCreated);
        })
        .catch(err => {
            console.error('Error: ', err);
            res.status(500).send(err);
        });
});

module.exports = router