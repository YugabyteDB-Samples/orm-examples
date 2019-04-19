'use strict';
module.exports = (sequelize, DataTypes) => {
  const Product = sequelize.define('products', {
    productId: {
			type: DataTypes.INTEGER,
			field: 'id',
			autoIncrement: true,
			primaryKey: true,
		},
    productName: DataTypes.STRING,
    description: DataTypes.STRING,
    price: {
			type: DataTypes.DECIMAL(10,2),
		}
  }, {});
  Product.associate = function(models) {
    // associations can be defined here
  };
  return Product;
};
