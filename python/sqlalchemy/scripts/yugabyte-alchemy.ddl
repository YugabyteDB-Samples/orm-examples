
create database alchemy_orm_sample;

\c alchemy_orm_sample

create table users (
	user_id 		serial primary key,
	first_name		varchar(40) not null,
	last_name		varchar(40) not null,
	email			varchar(40)
);

create table products (
	product_id		serial primary key,
	product_name	varchar(50),
	description		varchar(500),
	price  			numeric(8,2)
);

create table orders (
	order_id		serial primary key,
	user_id			numeric(8, 2),
	order_total     numeric(8, 2)
);

create table order_lines (
	line_id			serial primary key,
	order_id		bigint,
	product_id      integer,
	units           bigint
);

\c alchemy_orm_sample