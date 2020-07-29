from rest_framework import serializers, status
from ybstoreapp.models import Users, Products, Orders, Orderline
from django.core.exceptions import ValidationError

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = ('userId', 'firstName', 'lastName', 'email')
        
class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Products
        fields = ('productId', 'productName', 'description', 'price')

class OrderSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orders
        fields = ('orderId', 'userId', 'orderTime', 'orderTotal')
    
    def create(self, validated_data):

        orderTotal = 0.0
        for product in self.context['request'].data['products']:
            try:
                prdInstance = Products.objects.get(pk = product['productId'])
                orderTotal += float(prdInstance.price * product['units'])
            except Products.DoesNotExist:
                errMsg = '\"productId\": ["Invalid pk \"%d\" - object does not exist."]' % product['productId']
                raise serializers.ValidationError(errMsg)

        validated_data.update( {'orderTotal' : orderTotal} )

        # create Order record first 
        ordInstance = serializers.ModelSerializer.create(self, validated_data)
    
        # create Orderline record(s) using Order record created above
        for product in self.context['request'].data['products']:
            prdInstance = Products.objects.get(pk = product['productId'])
            Orderline.objects.create(orderId=ordInstance, productId=prdInstance, units=product['units'])

        return ordInstance
    
class OrderLineSerializer(serializers.ModelSerializer):
    class Meta:
        model = Orderline
        fields = ('orderlineId', 'orderId', 'productId', 'units')
