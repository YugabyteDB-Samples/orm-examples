create table users (
  user_id bigint not null generated always as identity,
  first_name text not null,
  last_name text not null,
  email text not null,

  constraint pk_users primary key (user_id)
);

create table products (
  product_id bigint not null generated always as identity,
  product_name text not null,
  description text,
  price numeric(10, 2) not null,

  constraint pk_products primary key (product_id)
);

create table orders (
  order_id uuid not null, -- Might generate these with a generated clause in the future! https://github.com/yugabyte/yugabyte-db/issues/3472
  user_id bigint not null,
  order_total numeric(10, 2) not null,
  order_time timestamp with time zone not null,

  constraint pk_orders primary key (order_id),
  constraint fk_orders_users foreign key (user_id) references users
);

create table order_lines (
  order_id uuid not null,
  product_id bigint not null,
  units integer not null,

  constraint pk_order_lines primary key (order_id, product_id),
  constraint fk_order_lines_orders foreign key (order_id) references orders,
  constraint fk_order_lines_products foreign key (product_id) references products
);

create function trg_orders ()
returns trigger
as $$
begin
  new.order_time = current_timestamp;
  return new;
end;
$$ language plpgsql;

create trigger trg_orders before insert on orders for each row execute procedure trg_orders();