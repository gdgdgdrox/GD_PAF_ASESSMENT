CREATE DATABASE eshop;

USE eshop;

CREATE TABLE customers(
    name varchar(32) NOT NULL PRIMARY KEY,
    address varchar(128) NOT NULL,
    email varchar(128) NOT NULL
);

INSERT INTO customers(name, address, email) VALUES
    ("fred", "201 Cobblestone Lane", "fredflintstone@bedrock.com"),
    ("sherlock", "221B Baker Street London", "sherlock@consultingdetective.org"),
    ("spongebob", "124 Conch Street Bikini Bottom", "spongebob@yahoo.com"),
    ("jessica", "698 Candlewood Land Cabot Cove", "fletcher@gmail.com"),
    ("dursley", "4 Privet Drive Little Whinging Surrey", "dursley@gmail.com");

CREATE TABLE online_order(
    order_id char(8) NOT NULL PRIMARY KEY,
    name varchar(32) NOT NULL,
    order_date date NOT NULL,
    FOREIGN KEY (name) REFERENCES customers(name)
);

CREATE TABLE line_items(
	id int auto_increment primary key,
    item varchar(128) NOT NULL,
    quantity int NOT NULL,
    order_id char(8) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES online_order(order_id)
);

CREATE TABLE order_status(
	id int primary key auto_increment,
	order_id char(8) NOT NULL,
    delivery_id varchar(128),
    status ENUM("pending", "dispatched"),
    status_update timestamp,
	FOREIGN KEY (order_id) REFERENCES online_order(order_id)
);
