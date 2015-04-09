CREATE TABLE Category
(
id int not null,
parent_id int null,
name varchar(100) not null,
   constraint PK_CATEGORY primary key (id)
);

create table GenID
(
   generic_id           int                            not null,
   tab                  varchar(15)                    not null,
   constraint PK_GENID primary key (tab)
);

create table Manufacturer
(
   id               int                            not null,
   name             varchar(100)                   not null,
   country		varchar(50) 			null,
   constraint PK_MANUFACTURER primary key (id)
);

create table Purchase
(
purchase_id int not null, 
purchase_prodID int not null, 
fio varchar(100) not null,
mail varchar(50) not null,
phone varchar(30) not null,
address varchar(100) not null,
   constraint PK_ORDER primary key (purchase_id)
);

create table Product
(
prod_id              int                            not null,
   prod_cat_id               int                            not null,
   prod_man_id               int                            not null,
   prod_name            varchar(200)                   not null,
   prod_price           float                   not null,
   prod_desc            varchar(300)                   null,
   prod_avail           bit                           not null,
   prod_image           varchar(50)                   null,
   constraint PK_PRODUCTS primary key (prod_id)
);

