DROP DATABASE IF EXISTS sellerDB;

CREATE DATABASE sellerDB;
USE sellerDB;

SET @@SESSION.auto_increment_increment=1;

CREATE TABLE category(
	id INT NOT NULL AUTO_INCREMENT,
	categoryName VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(100) NOT NULL,
	
	CONSTRAINT pk_category PRIMARY KEY(id)
);

CREATE TABLE seller(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	updatedAt DATETIME DEFAULT NULL,
	sellerCategory INT NOT NULL,
	token VARCHAR(100) DEFAULT NULL,
	
	CONSTRAINT pk_seller PRIMARY key(id),
	CONSTRAINT fk_sellerCategory FOREIGN KEY(sellerCategory) REFERENCES category(id) 
	ON UPDATE CASCADE 
	ON DELETE CASCADE
);

CREATE TABLE review(
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
	details VARCHAR(100)NOT NULL,
	sellerId INT NOT NULL,
	submiterId INT NOT NULL,
	sumbitedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	updatedAt DATETIME DEFAULT NULL,
	
	CONSTRAINT pk_review PRIMARY key(id),
	CONSTRAINT fk_review FOREIGN KEY(sellerId) REFERENCES seller(id)
	ON UPDATE CASCADE 
	ON DELETE CASCADE
);

-- dummy data
INSERT INTO category(id, categoryName, description)
VALUES
(1, "category 1", "description 1"),
(2, "category 2", "description 2"),
(3, "category 3", "description 3"),
(4, "category 4", "description 4"),
(5, "category 5", "description 5");

INSERT INTO seller(id, name, email, password, sellerCategory)
VALUES 
(1,"name 1", "email1@gmail.com", "password1", 1),
(2,"name 2", "email2@gmail.com", "password2", 2),
(3,"name 3", "email3@gmail.com", "password3", 3),
(4,"name 4", "email4@gmail.com", "password4", 3),
(5,"name 5", "email5@gmail.com", "password5", 5);

INSERT INTO review(id, title, details, sellerId, submiterId)
VALUES
(1, "title 1", "details 1", 1, 2),
(2, "title 2", "details 2", 1, 2),
(3, "title 3", "details 3", 2, 5),
(4, "title 4", "details 4", 4, 4),
(5, "title 5", "details 5", 5, 1);








