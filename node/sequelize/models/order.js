'use strict';
const uuid = require('uuid/v4');

module.exports = (sequelize, DataTypes) => {
	const Order = sequelize.define('orders', {
    orderId: {
			type: DataTypes.UUID,
			field: 'id',
			defaultValue: uuid(),
			primaryKey: true
		},
    orderTotal: DataTypes.DECIMAL(10,2),
		userId: DataTypes.INTEGER,
	}, {});
	Order.associate = function(models) {
	// associations can be defined here
	};
	return Order;
};
