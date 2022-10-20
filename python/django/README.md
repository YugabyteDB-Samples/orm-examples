### Pre-requisites and Dependencies
* YugabyteDB (https://docs.yugabyte.com/latest/quick-start/install/)
* Python3 (https://www.python.org/downloads/)
* django 2.2 or above (https://docs.djangoproject.com/en/2.2/topics/install/)
* django rest framework (run command 'pip3 install djangorestframework')
* psycopg2 (run command 'pip3 install psycopg2')

You can also run following command from sourecode directory to install psycopg2 and django rest framework
```
pip3 install -r requirements.txt
```
### Customization
Customize database connection setting according to your own environment by setting following section in $sourecode/ybstore/settings.py
```
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'ysql_django',
        'USER': 'postgres',
        'PASSWORD': '',
        'HOST': '127.0.0.1',
        'PORT': '5433',
    }
}
```

Generate Django secret key using any django secret key generator and place newly generated key in the configuration
```
SECRET_KEY = 'YOUR-SECRET-KEY'
```

Once these properties are customized, create database in YugabyteDB as specified in configuration
```
./ysqlsh -c "CREATE DATABASE ysql_django"
```

The code is ready to run. Navigate to the source code directory and run following commands:
```
python3 manage.py makemigrations &&
python3 manage.py migrate
```
Note: Database instance setup in settings.py file needs to be up and running before running above commands. These commands would create all database entities in the database.

Now start REST server at port 8080. You can specify a port of your own choice.
```
python3 manage.py runserver 8080
```
