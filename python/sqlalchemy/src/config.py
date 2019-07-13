import logging


listen_port = 8080
db_user = 'postgres'
db_password = None
database = 'ysql_sqlalchemy'
schema = 'ysql_sqlalchemy'
db_host = 'localhost'
db_port = 5433

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s:%(levelname)s:%(message)s"
    )


