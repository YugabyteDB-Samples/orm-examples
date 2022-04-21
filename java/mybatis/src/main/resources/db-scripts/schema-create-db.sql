
    create table orderline (
       order_id int8 not null,
        product_id int8 not null,
        units int4,
        primary key (order_id, product_id)
    );

    create table orders (
       order_id bigserial not null,
        order_total numeric(10,2),
        user_id int8,
        primary key (order_id)
    );

    create table products (
       product_id  bigserial not null,
        description varchar(255),
        price numeric(10,2),
        product_name varchar(50),
        primary key (product_id)
    );

    create table users (
       user_id  bigserial not null,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        primary key (user_id)
    );

    alter table orderline 
       add constraint FK44tgjt0pognvcg1r7isplhg57 
       foreign key (product_id) 
       references products;

    alter table orderline 
       add constraint FK7jhn7tvm2wi722qnm2ilw06hh 
       foreign key (order_id) 
       references orders 
       on delete cascade;

    alter table orders 
       add constraint FK32ql8ubntj5uh44ph9659tiih 
       foreign key (user_id) 
       references users;
