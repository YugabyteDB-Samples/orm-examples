'use strict';
var express = require('express');
var router = express.Router();
const { PrismaClient }  = require('@prisma/client')

const prisma = new PrismaClient({
  log: ['query', 'info', 'warn', 'error'],
})


router.get('/', function(req, res, next) {
    prisma.order
        .findMany({
            include: {
                orderLines: {
                    select : {
                        productId: true,
                        quantity: true,
                    }
                }
            }
        })
        .then((orders) => {
            var response = {
                content:[]
              }
            orders.forEach(order => {
            response.content.push(order);
            });
            res.send(response);
        })
        .catch(err => {
            console.error('Error: ', err);
            res.status(500).send(err);
        });
});

router.post('/', function(req, res, next) {
    let productIds = [] 
    req.body.products.forEach(product => {
        productIds.push(product.productId);
    })
    let user_id = parseInt(req.body.userId)
    prisma.product
        .findMany({
            where: {
                productId: {
                    in: productIds
                }
            }
        })
        .then((products) => {
            var productInfos = [];
            var totalOrderSum = 0.0;
            var allOrderLines = []
            products.forEach(fetchedProduct => {
                var orderProduct = req.body.products.find( orderlineProduct=> {
                    return fetchedProduct.productId == orderlineProduct.productId
                });
                var productInfo = {
                    productId: fetchedProduct.productId,
                    units: orderProduct.units,
                    price: fetchedProduct.price,
                    productName: fetchedProduct.productName,
                }
                productInfos.push(productInfo);
                allOrderLines.push({
                    productId: productInfo.productId,
                    quantity: productInfo.units
                });

                totalOrderSum += productInfo.price * orderProduct.units;
            });

            prisma.order
                .create({
                    data: {
                        orderTotal: totalOrderSum,
                        userId: user_id,
                        orderLines: {
                            create: allOrderLines 
                        }
                    },
                    include: {
                        orderLines: true
                    }
                })
                .then((order) => {
                    res.json(order)
                })
                .catch((err) => {
                    console.log("Error: ", err);
                    res.status(500).send(err);
                })
        })
        .catch((err) => {
            console.log("Error: ", err);
            res.status(500).send(err);
        })
});

module.exports = router