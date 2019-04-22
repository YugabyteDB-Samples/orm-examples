var express = require('express');
var router = express.Router();

const Sequelize = require('sequelize');
var models = require('../models');

// GET products listing
router.get('/', function(req, res, next) {
	models.products
	.findAll()
	.then(products => {
		responseBody = {
			content:[]
		}
		products.forEach(product => {
			responseBody.content.push(product.dataValues);
		});

		res.send(responseBody);
	})
	.catch(err => {
		console.error('Error: ', err);
		res.status(500).send(err);
	});
});

// create a product
router.post('/', (req, res, next) => {
	models.products
	.create(req.body)
	.then(product => {
		res.send(product);
	})
	.catch(err => {
		console.error('Error: ', err);
		res.status(500).send(err);
	})
});

module.exports = router;
