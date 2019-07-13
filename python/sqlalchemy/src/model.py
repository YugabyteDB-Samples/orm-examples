from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, Float, ForeignKey
from sqlalchemy.orm import sessionmaker, relationship
import config as cfg


Base = declarative_base()
Base.metadata.schema = 'ysql_sqlalchemy'

class User(Base):
    __tablename__ = 'users'
    __table_args__ = {"schema": "{0}".format(cfg.schema)}

    user_id = Column(Integer, primary_key=True)
    first_name = Column(String)
    last_name = Column(String)
    email = Column(String)
    orders = relationship('Order', foreign_keys=[user_id], primaryjoin='Order.user_id == User.user_id', uselist=True)
    
    def __repr__(self):
        return "<User(id=%s first_name='%s', last_name='%s', email='%s')>" % \
            (self.user_id, self.first_name, self.last_name, self.email)  

    def to_json(self):
        user_json = {
            "user_id": self.user_id,
            "first_name": self.first_name,
            "last_name": self.last_name,
            "email": self.email
        }
        return user_json

class Order(Base):
    __tablename__ = "orders"
    __table_args__ = {"schema": "{0}".format(cfg.schema)}
    
    order_id = Column(Integer, primary_key=True)
    user_id = Column(Integer)  
    order_lines = relationship("OrderLine", foreign_keys=[order_id], primaryjoin='OrderLine.order_id == Order.order_id', 
        back_populates='order', uselist=True)
    order_total = Column(Float)
    
    def __repr__(self):
        return "<Order(order_id=%s user_id=%s order_total: %s)>" % \
    (self.order_id, self.user_id, self.order_total)

    def to_json(self):
        order_json = {
            "order_id": self.order_id,
            "user_id": self.user_id,
            "order_total": self.order_total
        }
        order_json['order_lines'] = [
            line.to_json() for line in self.order_lines
        ]

        return order_json

class Product(Base):
    __tablename__ = "products"
    __table_args__ = {"schema": "{0}".format(cfg.schema)}
    
    product_id = Column(Integer, primary_key=True)
    product_name = Column(String)
    description = Column(String)
    price = Column(Float)
    
    def __repr__(self):
        return "<Product(product_id=%s, product_name=%s, descripton=%s, price=%s)>" % \
            (self.product_id, self.product_name, self.description, self.price)

    def to_json(self):
        return {
            "product_id": self.product_id,
            "product_name": self.product_name,
            "description": self.description,
            "price": self.price
        }

class OrderLine(Base):
    __tablename__ = "order_lines"
    __table_args__ = {"schema": "{0}".format(cfg.schema)}

    line_id = Column(Integer, primary_key=True)
    order_id = Column(Integer)
    product_id = Column(Integer)
    quantity = Column(Integer)

    order = relationship("Order", foreign_keys=[order_id], primaryjoin='Order.order_id == OrderLine.order_id')
    product = relationship("Product", foreign_keys=[product_id], primaryjoin='Product.product_id == OrderLine.product_id')

    def __repr__(self):
        return "<OrderLine(line_id=%s order=%s, product=%s)>" % (self.line_id, self.order_id, self.product)

    def to_json(self):
        return {
            "line_id": self.line_id,
            "order_id": self.order_id,
            "product": self.product.to_json()
        }
