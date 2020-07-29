from django.shortcuts import render

from ybstoreapp.models import Users, Products, Orders, Orderline
from rest_framework import viewsets
from ybstoreapp.serializers import UserSerializer, ProductSerializer, OrderSerializer
        
class UserViewSet(viewsets.ModelViewSet):
    queryset = Users.objects.all()
    serializer_class = UserSerializer

class ProductViewSet(viewsets.ModelViewSet):
    queryset = Products.objects.all()
    serializer_class = ProductSerializer

class OrderViewSet(viewsets.ModelViewSet):
    queryset = Orders.objects.all()
    serializer_class = OrderSerializer
