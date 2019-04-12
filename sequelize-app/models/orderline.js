'use strict';
module.exports = (sequelize, DataTypes) => {
  const OrderLine = sequelize.define('orderlines', {
    orderId: {
			type: DataTypes.UUID,
			unique: 'compositeKey',
			allowNull: false
		},
    productId:{
			type: DataTypes.INTEGER,
			unique: 'compositeKey',
			allowNull: false
		}
  }, {});
  OrderLine.associate = function(models) {
    // associations can be defined here
  };
  return OrderLine;
};
