--USERS
create sequence user_seq start with 1 increment 1;

create table users
(
    id       int8 not null,
    email    varchar(255),
    name     varchar(255),
    password varchar(255),
    role     varchar(255),
    primary key (id)
);


--BASKET
create sequence basket_seq start with 1 increment 1;

create table baskets
(
    id      int8 not null,
    user_id int8,
    primary key (id)
);

alter table if exists baskets
    add constraint baskets_fk_user
        foreign key (user_id) references users;


--CATEGORY
create sequence category_seq start with 1 increment 1;

create table categories
(
    id    int8 not null,
    title varchar(255),
    primary key (id)
);


--PRODUCTS
create sequence product_seq start with 1 increment 1;

create table products
(
    id          int8 not null,
    price       float8,
    title       varchar(255),
    image       varchar(255),
    category_id int8 not null,
    primary key (id)
);

alter table if exists products
    add constraint products_fk_category
        foreign key (category_id) references categories;

--PRODUCTS IN BASKET
create table basket_products
(
    basket_id  int8 not null,
    product_id int8 not null
);

alter table if exists basket_products
    add constraint basket_products_fk_product
        foreign key (product_id) references products;

alter table if exists basket_products
    add constraint basket_products_fk_basket
        foreign key (basket_id) references baskets;