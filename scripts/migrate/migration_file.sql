create table users
(
    id          varchar2(36)         not null
        constraint users_pk primary key,
    email       varchar2(100) unique not null
        constraint users_email_valid_check CHECK ( REGEXP_LIKE(email,
        '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')),
    password    varchar2(500)        not null,
    fullName    varchar2(100),
    address     varchar2(500),
    avatar      varchar2(500),
    gender      varchar2(20),
    bod         varchar2(20),
    phoneNumber varchar2(20),
    status      varchar2(20) default 'active',
    createdAt   timestamp    default current_timestamp,
    updatedAt   timestamp    default current_timestamp,
    createdBy   varchar2(36),
    updatedBy   varchar2(36),
    role        varchar(30)  default 'user'
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
    alias       varchar2(100) not null
        constraint categories_pk primary key,
    name        varchar2(200),
    description varchar2(500),
    status      varchar2(20) default 'active',
    createdAt   timestamp    default current_timestamp,
    updatedAt   timestamp    default current_timestamp,
    createdBy   varchar2(36),
    updatedBy   varchar2(36)
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
    id          varchar2(36) not null
        constraint shops_pk primary key,
    name        varchar2(200),
    address     varchar2(500),
    phoneNumber varchar2(20),
    logo        varchar2(500),
    banner      varchar2(500),
    status      varchar2(20) default 'active',
    createdAt   timestamp    default current_timestamp,
    updatedAt   timestamp    default current_timestamp,
    createdBy   varchar2(36)
        constraint shops_users_createdBy_fk references USERS (ID),
    updatedBy   varchar2(36)
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
    id            varchar2(36) not null
        constraint products_pk primary key,
    name          varchar2(200),
    description   varchar2(500),
    price         float,
    quantityStore int,
    status        varchar2(20) default 'active',
    createdAt     timestamp    default current_timestamp,
    updatedAt     timestamp    default current_timestamp,
    createdBy     varchar2(36)
        constraint products_users_createdBy_fk references USERS (ID),
    updatedBy     varchar2(36)
        constraint products_users_updatedBy_fk references USERS (ID),
    shopId        varchar2(36)
        constraint products_shops_shopId_fk references SHOPS (ID)
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
    id        varchar2(36) not null
        constraint product_images_pk primary key,
    url       varchar2(500),
    status    varchar2(20) default 'active',
    createdAt timestamp    default current_timestamp,
    updatedAt timestamp    default current_timestamp,
    createdBy varchar2(36)
        constraint product_images_users_createdBy_fk references USERS (ID),
    updatedBy varchar2(36)
        constraint product_images_users_updatedBy_fk references USERS (ID),
    productId varchar2(36)
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
    id         varchar2(36) not null
        constraint orders_pk primary key,
    totalMoney double precision,
    quantity   int,
    discount   float,
    status     varchar2(20) default 'active',
    createdAt  timestamp    default current_timestamp,
    updatedAt  timestamp    default current_timestamp,
    createdBy  varchar2(36)
        constraint orders_users_createdBy_fk references USERS (ID),
    updatedBy  varchar2(36)
        constraint orders_users_updatedBy_fk references USERS (ID),
    shopId     varchar2(36)
        constraint orders_shops_shopId_fk references USERS (ID)
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
    id        varchar2(36) not null
        constraint order_details_pk primary key,
    orderId   varchar2(36)
        constraint order_details_orders_orderId_fk references ORDERS (ID),
    productId varchar2(36)
        constraint order_details_products_productId_fk references USERS (ID),
    status    varchar2(20) default 'active',
    createdAt timestamp    default current_timestamp,
    updatedAt timestamp    default current_timestamp,
    createdBy varchar2(36)
        constraint order_details_users_createdBy_fk references USERS (ID),
    updatedBy varchar2(36)
        constraint order_details_users_updatedBy_fk references USERS (ID)
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

