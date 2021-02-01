create table if not exists products
(
    product_id bigserial not null
        constraint products_pkey
            primary key,
    name varchar(255),
    quantity integer,
    measure varchar(255)
);

create table if not exists orders
(
    id bigserial not null
        constraint order_pkey
            primary key,
    name varchar(255),
    quantity integer,
    measure varchar(255)
);