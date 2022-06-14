DROP DATABASE IF EXISTS productDB;

CREATE DATABASE productDB;
USE productDB;

SET @@SESSION.auto_increment_increment=1;


CREATE TABLE category(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(256) NOT NULL,

	CONSTRAINT pk_category PRIMARY KEY(id)
);

CREATE TABLE product(
	id INT NOT NULL AUTO_INCREMENT,
	product_name VARCHAR(100) NOT NULL,
	product_price DOUBLE DEFAULT NULL,
	owner_id INT NOT NULL,
	buyer_id INT DEFAULT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT NULL,
	is_completed boolean DEFAULT TRUE,
	category_id INT,
	
	
	CONSTRAINT pk_product PRIMARY KEY(id),
	CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reservers(
	id INT NOT NULL,
	reserver_id INT NOT NULL,
	
	CONSTRAINT pk_reservers PRIMARY KEY(id, reserver_id),
	CONSTRAINT FOREIGN KEY(id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO category(name,description)
VALUES
("category 1", "description 1"),
("category 2", "description 2"),
("category 3", "description 3"),
("category 4", "description 4"),
("category 5", "description 5");


INSERT INTO product(product_name, product_price, owner_id , is_completed ,category_id)
VALUES
("Product 1", 12000, 1, TRUE, 2),
("Product 2", 2400, 1, FALSE, 1),
("Product 3", 10000, 1, TRUE, 1),
("Product 4", 7000, 1, FALSE, 3),
("Product 5", 5000, 1, TRUE, 5);

INSERT INTO reservers(id, reserver_id)
VALUES 
(1,1),
(1,2),
(1,3),
(2,1),
(4,2);









