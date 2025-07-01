'use strict';
var express = require('express');
var router = express.Router();
const { PrismaPg } = require('@yugabytedb/prisma-adapter');
const { PrismaClient } = require('@prisma/client');

const connectionString = `${process.env.DATABASE_URL}`

const adapter = new PrismaPg({ connectionString })

const prisma = new PrismaClient({ adapter })

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