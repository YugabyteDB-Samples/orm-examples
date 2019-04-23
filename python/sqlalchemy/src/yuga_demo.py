#!/usr/bin/env python
# coding: utf-8

from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship
from http.server import HTTPServer, BaseHTTPRequestHandler
import json
from io import BytesIO
import jsonpickle
from yuga_demo_model import User, Order, Product, OrderLine

## ######################################################
## Configure the parameters below to connect to Yugabyte database: 
## 
listen_port = 8000
db_user = 'postgres'
db_password = 'pwd'
database = 'alchemy_orm_sample'
db_host = 'localhost'
## Yugabyte
db_port = 5433
## Postgresql
#db_port = 5432

## #####################################################

# ## SQLAlchemy ORM to database
# With SQLAlchemy we can deal with objects, rather than dealing with rows and columns
# 
# Set up the session object, then the session communicates with the database.  Objects are "mapped" to the database tables using annotations (__tablename__), and by extending the Base object.

from sqlalchemy import create_engine
#dialect+driver://username:password@host:port/database
engine = create_engine('postgresql://%s:%s@%s:%s/%s' % 
                       (db_user, db_password, db_host, db_port, database), echo=True)
Session = sessionmaker(bind=engine)
# Base = declarative_base()


def add_object(session, to_add):
    session.add(to_add)
    print('Added: %s' % str(to_add) )


def add_objects(session, object_list):
    session.add_all(object_list)
    print('Added (list): ')
    [print(str(obj)) for obj in object_list]

class UserOrdersDAO:

    def get_users(self):
        session = Session()

        try:
            users = session.query(User).all()
            # print('====>> Got list of users: %s First user: ' % (len(users), users[0].to_json()))
            users_json = {
                'users': [
                    user.to_json() for user in users
                ]
            }
            session.commit()
            return users_json
        except Exception as e:     
            print('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

    def list_orders_for_user(self, rq_user_id):
        session = Session()

        try:
            user = session.query(User).get(rq_user_id)

            if user is None:
                print('Unable to find user with id = %s' % rq_user_id)
                # TODO: Raise an error

            user_json = user.to_json()

            user_json['orders'] = [
                order.to_json() for order in user.orders
            ]
            return user_json
        except Exception as e:     
            print('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

    def create_order(self, rq_user_id, products):
        session = Session()

        try:
            user = session.query(User).get(rq_user_id)

            if user is None:
                raise Exception('Unable to find user with id = %s' % rq_user_id)

            order_line_list = []
            user_order = Order(user_id=rq_user_id, order_total=0)

            print('Processing %s products' % len(products))
            for prod_def in products:
                db_product = session.query(Product).get(prod_def['productId'])
                if db_product is None:
                    raise Exception('Product not found with ProductID: %s' % prod_def['productId'])
                order_line = OrderLine(product=db_product, order=user_order, units=prod_def['units'])
                order_line_list.append(order_line)
                user_order.order_total = user_order.order_total + db_product.price

            session.add(user_order)
            session.add_all(order_line_list)
            session.commit()

            the_order = session.query(Order).get(user_order.order_id)

            return the_order.to_json()

        except Exception as e:     
            print('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

    def create_product(self, to_add):
        session = Session()

        try:
            product = Product(product_name=to_add['productName'], description=to_add['description'], price=to_add['price'])
            self.add_object(product)
            session.commit()

            return product.to_json()
        except Exception as e:     
            print('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()
 
    def add_object(self, to_add):
        session = Session()

        try:
            session.add(to_add)
            session.commit()
            return to_add.to_json()
        except Exception as e:
            print('** Exception while trying to add object: %s Error: %e' % (str(to_add), e))
            raise
        finally:
            session.close()

    def list_products(self):
        session = Session()

        try:
            products = session.query(Product).all()
            product_json = {
                'products': [
                    product.to_json() for product in products
                ]
            }
            session.commit()
            return product_json
        except Exception as e:     
            print('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()



class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):

    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'json')
        self.end_headers()

    def _return_error(self):
        self.send_response(400)

    def do_GET(self):
        print('do_Get called with path: %s' % self.path)
        dao = UserOrdersDAO()

        if self.path == '/users':
            self.handle_list_users(dao)

        if self.path == '/products':
            self.handle_list_products(dao)

        if self.path == '/list-products':
            self.handle_list_all_products(dao)

    def do_POST(self):

        print('>> do_POST')
        content_length = int(self.headers['Content-Length'])
        body = json.loads(self.rfile.read(content_length))

        print('body: ' % body)

        dao = UserOrdersDAO()

        print('doPost called with url: %s and data: %s' % (self.path, body))

        try:
            if self.path == '/users':
                self.handle_create_user(dao, body)

            if self.path == '/orders':
                self.handle_create_order(dao, body)

            if self.path == '/list-orders':
                self.handle_list_orders(dao, body)

            if self.path == '/products':
                self.handle_create_product(dao, body)

            if self.path == '/list-products':
                self.handle_list_all_products(dao)
        except: 
            self._return_error()

    # =======================================================
    ## Handlers below
    # =======================================================

    def handle_create_user(self, dao, body):
        self.handle_write_output(
            dao.add_object(User(first_name=body['firstName'], last_name=body['lastName'], email=body['email'])))

    def handle_list_users(self, dao):
        self.handle_write_output(dao.get_users())

    def handle_list_all_products(self, dao):
        self.handle_write_output(
            dao.list_products()
        )

    def handle_create_product(self, dao, body):
        self.handle_write_output(
            dao.create_product(body)
        )

    def handle_list_orders(self, dao, body):
        self.handle_write_output(
            dao.list_orders_for_user(body['userId'])
        )

    def handle_list_products(self, dao, body):
        self.handle_write_output(
            dao.list_orders_for_user(body['userId'])
        )
        

    def handle_write_output(self, results_json):
        self._set_headers()
        response = BytesIO()
        response.write(jsonpickle.encode(results_json).encode())
        self.wfile.write(response.getvalue())

    def handle_create_order(self, dao, body):
        if 'userId' not in body:
            print('UserId required!')
            # TODO: Return an error here!

        self.handle_write_output(dao.create_order(body['userId'], body['products']))

# ### HTTP Server
httpd = HTTPServer(('localhost', listen_port), SimpleHTTPRequestHandler)
print('Listening on port %s' % listen_port)
httpd.serve_forever()

