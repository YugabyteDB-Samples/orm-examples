from django.db import models
import uuid
from datetime import datetime

class Users(models.Model):
    userId = models.AutoField(db_column='user_id', primary_key=True, serialize=False)
    firstName = models.CharField(max_length=50, db_column='first_name')
    lastName = models.CharField(max_length=50, db_column='last_name')
    email = models.CharField(max_length=100, db_column='user_email')
    
    class Meta:
        db_table = "users"
    
    def __str__(self):
        return '%d %s %s %s' % (self.userId, self.firstName, self.lastName, self.email)
    
 
class Products(models.Model):
    productId = models.AutoField(db_column='product_id', primary_key=True, serialize=False)
    productName = models.CharField(db_column='product_name', max_length=50)
    description = models.CharField(db_column='description', max_length=100)
    price = models.DecimalField(db_column='price', max_digits=10, decimal_places=2)
    
    class Meta:
        db_table = "products"
        
    def __str__(self):
        return '%d %s %s %f' % (self.productId, self.productName, self.description, self.price)
    
    
class Orders(models.Model):
    orderId = models.UUIDField(db_column='order_id', default=uuid.uuid4, primary_key=True, serialize=False)
    orderTime = models.DateTimeField(db_column='order_time', default=datetime.now)
    userId = models.ForeignKey(Users, db_column='user_id', on_delete=models.CASCADE)
    orderTotal = models.DecimalField(db_column='order_total', max_digits=10, decimal_places=2, serialize=False)
    
    class Meta:
        db_table = "orders"
    
    def __str__(self):
        return '%s %s %d %f' % (self.orderId, str(self.orderTime), self.userId, self.orderTotal)
    
       
class Orderline(models.Model):
    orderlineId = models.AutoField(db_column='orderline_id', primary_key=True, serialize=False)
    orderId = models.ForeignKey(Orders, db_column='order_id', on_delete=models.CASCADE)
    productId = models.ForeignKey(Products, db_column='product_id', on_delete=models.CASCADE)
    units = models.IntegerField(db_column='units')

    class Meta:
        db_table = 'orderline'
        unique_together = (('orderId', 'productId'))
        
    def __str__(self):
        return '%s %d %d' % (self.orderId, self.productId, self.units)
