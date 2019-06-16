var express = require('express');
var router = express.Router();

const Sequelize = require('sequelize');
var models = require('../models');

// GET orders listing
router.get('/', function(req, res, next) {
	models.orders
	.findAll()
	.then(orders => {
		responseBody = {
			content:[]
		}
		orders.forEach(order => {
			responseBody.content.push(order.dataValues);
		});

		res.send(responseBody);
	})
	.catch(err => {
		console.error('Error: ', err);
		res.status(500).send(err);
	});
});

// create a order
router.post('/', (req, res, next) => {
	// first construct productIds array
	var productIds = [];
	req.body.products.forEach(product => {
		productIds.push(product.productId);
	});

	// next lookup products based on productIds
	models.products
	.findAll({
		where: {
			productId: productIds
		}
	})
	.then(products => {
		var productInfos = [];
		var orderTotal = 0.0;
		products.forEach(dbProduct => {
			var orderProduct = req.body.products.find( p => {
				return dbProduct.productId == p.productId
			});
			console.log('DBProduct ID: ' + dbProduct.productId + ' Order ProductID: ' + orderProduct.productId);
			var productInfo = {
				productId: dbProduct.productId,
				units: orderProduct.units,
				price: dbProduct.price,
				productName: dbProduct.productName,
			}
			productInfos.push(productInfo);

			orderTotal += productInfo.price * orderProduct.units;
		});

		// persist order
		models.orders
		.create({
				orderTotal: orderTotal,
				userId: req.body.userId
		})
		.then( order => {
			// persist order lines.
			// First construct order lines array
			var orderLines = [];
			productInfos.forEach( productInfo => {
				orderLines.push({
					orderId: order.orderId,
					productId: productInfo.productId,
					quantity: productInfo.units
				});
			});

			//then persist to order lines record
			models.orderlines
			.bulkCreate(orderLines)
			.then( l => {
				console.log('Order lines created');
				res.send('TBD');
			});
		});
	});

});
module.exports = router;
