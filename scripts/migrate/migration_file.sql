create table users
(
    id          nvarchar2(36)         not null
        constraint users_pk primary key,
    email       nvarchar2(100) unique not null
        constraint users_email_valid_check CHECK ( REGEXP_LIKE(email,
        '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')),
    password    nvarchar2(500)        not null,
    fullName    nvarchar2(100),
    address     nvarchar2(500),
    avatar      nvarchar2(500),
    gender      nvarchar2(20),
    bod         nvarchar2(20),
    phoneNumber nvarchar2(20),
    status      nvarchar2(20) default 'active',
    createdAt   timestamp     default current_timestamp,
    updatedAt   timestamp     default current_timestamp,
    createdBy   nvarchar2(36),
    updatedBy   nvarchar2(36),
    role        nvarchar2(30) default 'user'
);

CREATE OR REPLACE TRIGGER users_trigger
    BEFORE
UPDATE
    on users
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table categories
(
    alias       nvarchar2(100) not null
        constraint categories_pk primary key,
    name        nvarchar2(200) unique,
    description nvarchar2(500),
    status      nvarchar2(20) default 'active',
    createdAt   timestamp     default current_timestamp,
    updatedAt   timestamp     default current_timestamp,
    createdBy   nvarchar2(36),
    updatedBy   nvarchar2(36)
);

CREATE OR REPLACE TRIGGER categories_trigger
    BEFORE
UPDATE
    on categories
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table shops
(
    id          nvarchar2(36) not null
        constraint shops_pk primary key,
    name        nvarchar2(200),
    address     nvarchar2(500),
    phoneNumber nvarchar2(20),
    logo        nvarchar2(500),
    banner      nvarchar2(500),
    status      nvarchar2(20) default 'active',
    createdAt   timestamp     default current_timestamp,
    updatedAt   timestamp     default current_timestamp,
    createdBy   nvarchar2(36)
        constraint shops_users_createdBy_fk references USERS (ID),
    updatedBy   nvarchar2(36)
        constraint shops_users_updatedBy_fk references USERS (ID)
);

CREATE OR REPLACE TRIGGER shops_trigger
    BEFORE
UPDATE
    on shops
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table products
(
    id            nvarchar2(36) not null
        constraint products_pk primary key,
    name          nvarchar2(200),
    description   nvarchar2(500),
    price         float,
    quantityStore int,
    status        nvarchar2(20) default 'active',
    createdAt     timestamp     default current_timestamp,
    updatedAt     timestamp     default current_timestamp,
    createdBy     nvarchar2(36)
        constraint products_users_createdBy_fk references USERS (ID),
    updatedBy     nvarchar2(36)
        constraint products_users_updatedBy_fk references USERS (ID),
    shopId        nvarchar2(36)
        constraint products_shops_shopId_fk references SHOPS (ID),
    categoryAlias nvarchar2(100)
        constraint products_categories_categoryAlias_fk references CATEGORIES (ALIAS)
);

CREATE OR REPLACE TRIGGER products_trigger
    BEFORE
UPDATE
    on products
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table product_images
(
    id        nvarchar2(36) not null
        constraint product_images_pk primary key,
    url       nvarchar2(500),
    status    nvarchar2(20) default 'active',
    createdAt timestamp     default current_timestamp,
    updatedAt timestamp     default current_timestamp,
    createdBy nvarchar2(36)
        constraint product_images_users_createdBy_fk references USERS (ID),
    updatedBy nvarchar2(36)
        constraint product_images_users_updatedBy_fk references USERS (ID),
    productId nvarchar2(36)
        constraint product_images_products_productId_fk references PRODUCTS (ID)
);

CREATE OR REPLACE TRIGGER product_images_trigger
    BEFORE
UPDATE
    on product_images
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table orders
(
    id              nvarchar2(36)  not null
        constraint orders_pk primary key,
    totalMoney      double precision,
    quantity        int,
    discount        float,
    deliveryAddress nvarchar2(500) not null,
    deliveryStatus  nvarchar2(50) default 'pending',
    isPaid          number         not null,
    paymentAt       timestamp,
    paymentType     nvarchar2(50),
    shipCost        double precision,
    status          nvarchar2(20) default 'active',
    createdAt       timestamp     default current_timestamp,
    updatedAt       timestamp     default current_timestamp,
    createdBy       nvarchar2(36)
        constraint orders_users_createdBy_fk references USERS (ID),
    updatedBy       nvarchar2(36)
        constraint orders_users_updatedBy_fk references USERS (ID)
);

CREATE OR REPLACE TRIGGER orders_trigger
    BEFORE
UPDATE
    on orders
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

create table order_details
(
    id        nvarchar2(36) not null
        constraint order_details_pk primary key,
    orderId   nvarchar2(36)
        constraint order_details_orders_orderId_fk references ORDERS (ID),
    productId nvarchar2(36)
        constraint order_details_products_productId_fk references PRODUCTS (ID),
    quantity  int not null,
    status    nvarchar2(20) default 'active',
    createdAt timestamp     default current_timestamp,
    updatedAt timestamp     default current_timestamp,
    createdBy nvarchar2(36)
        constraint order_details_users_createdBy_fk references USERS (ID),
    updatedBy nvarchar2(36)
        constraint order_details_users_updatedBy_fk references USERS (ID),
    shopId    nvarchar2(36)
        constraint order_details_shops_shopId_fk references SHOPS (ID)
);

CREATE OR REPLACE TRIGGER order_details_trigger
    BEFORE
UPDATE
    on order_details
    REFERENCING NEW AS "NEW"
    FOR EACH ROW
begin
    :NEW.updatedAt := current_timestamp;
end;

