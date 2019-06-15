import logging
from sqlalchemy.orm import sessionmaker, relationship
from model import User, Order, OrderLine, Product
import config as cfg
from sqlalchemy import create_engine
import traceback


class DataAccessUtil:

    def __init__(self):
        engine = create_engine('postgresql://%s:%s@%s:%s/%s' % 
                            (cfg.db_user, cfg.db_password, cfg.db_host, cfg.db_port, cfg.database), echo=True)
        self.Session = sessionmaker(bind=engine)

    def get_users(self):
        session = self.Session()

        try:
            users = session.query(User).all()
            users_json = {
                'users': [
                    user.to_json() for user in users
                ]
            }
            session.commit()
            return users_json
        except Exception as e:     
            logging.error('*** Exception in get_users: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

    def list_orders_for_user(self, rq_user_id):
        session = self.Session()

        try:
            user = session.query(User).get(rq_user_id)

            if user is None:
                msg = 'Unable to find user with id = %s' % rq_user_id
                logging.error(msg)
                raise Exception(msg)

            user_json = user.to_json()

            user_json['orders'] = [
                order.to_json() for order in user.orders
            ]
            return user_json
        except Exception as e:     
            logging.error('*** Exception in list_orders_for_user: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

    def create_order(self, rq_user_id, products):
        session = self.Session()

        try:
            user = session.query(User).get(rq_user_id)

            if user is None:
                raise Exception('Unable to find user with id = %s' % rq_user_id)

            order_line_list = []
            user_order = Order(user_id=rq_user_id, order_total=0)

            for prod_def in products:
                db_product = session.query(Product).get(prod_def['productId'])
                if db_product is None:
                    raise Exception('Product not found with ProductID: %s' % prod_def['productId'])
                order_line = OrderLine(product=db_product, order=user_order, quantity=prod_def['quantity'])
                order_line_list.append(order_line)
                user_order.order_total = user_order.order_total + db_product.price * prod_def['quantity']

            session.add(user_order)
            session.add_all(order_line_list)
            session.commit()

            the_order = session.query(Order).get(user_order.order_id)

            return the_order.to_json()

        except Exception as e:     
            logging.error('*** Exception in create_order: %s' % e)
            traceback.print_exc()

            session.rollback()
            raise
        finally:
            session.close()

    def create_product(self, to_add):
        session = self.Session()

        try:
            product = Product(product_name=to_add['productName'], description=to_add['description'], price=to_add['price'])
            self.add_object(product)
            session.commit()

            return product.to_json()
        except Exception as e:     
            logging.error('*** Exception in create_product: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()
 
    def add_object(self, to_add):
        session = self.Session()

        try:
            session.add(to_add)
            session.commit()
            return to_add.to_json()
        except Exception as e:
            logging.error('** Exception while trying to add object: %s Error: %e' % (str(to_add), e))
            raise
        finally:
            session.close()

    def list_products(self):
        session = self.Session()

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
            logging.error('*** Exception in list_products: %s' % e)
            session.rollback()
            raise
        finally:
            session.close()

