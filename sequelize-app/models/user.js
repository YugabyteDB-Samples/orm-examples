'use strict';
module.exports = (sequelize, DataTypes) => {
  const User = sequelize.define('users', {
    userId: {
			type: DataTypes.INTEGER,
			field: 'id',
			autoIncrement: true,
			primaryKey: true,
		},
    firstName: DataTypes.STRING,
    lastName: DataTypes.STRING,
    email: DataTypes.STRING
  }, {});
  User.associate = function(models) {
    // associations can be defined here
  };
  return User;
};
