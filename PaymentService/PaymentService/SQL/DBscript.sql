DROP DATABASE IF EXISTS paymentDB;

CREATE DATABASE paymentDB;
USE paymentDB;

SET @@SESSION.auto_increment_increment=1;

CREATE TABLE payement(
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
    recipient_id int NOT NULL,
    total DOUBLE NOT NULL,
	payment_method VARCHAR(50) NOT NULL,
	paidAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	seller_id INT DEFAULT NULL,
	reserver_id INT DEFAULT NULL,
	buyer_id INT DEFAULT NULL,
	
	CONSTRAINT pk_payement PRIMARY KEY(id),
	CONSTRAINT payment_methodes CHECK(payment_method IN ("bank", "visa", "american express", "master"))
);

CREATE TABLE recipient(
	id INT NOT NULL UNIQUE AUTO_INCREMENT,
	account_number VARCHAR(100) NOT NULL,
	bank VARCHAR(100) DEFAULT NULL,
	branch VARCHAR(100) DEFAULT NULL,
	issuedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	
	CONSTRAINT pk_recipient PRIMARY KEY(id)
);

ALTER TABLE payement 
ADD CONSTRAINT 
FOREIGN KEY(recipient_id) REFERENCES recipient(id)
ON DELETE CASCADE 
ON UPDATE cascade;

-- dummy data
INSERT INTO recipient(account_number, bank, branch)
VALUES 
(1,"BOC", "Dawulagala"),
(2,"Sampath Bank", "Kandy"),
(3,"BOC", "Peradeniya"),
(4,"HNB", "Colombo"),
(5,"Ceylan", "Dawulagala");

INSERT INTO payement(recipient_id, total,payment_method, seller_id, reserver_id, buyer_id)
VALUES 
( 1, 12000.00, "bank", 2,NULL,1),
( 2, 8000.00, "bank", 3,NULL,3),
( 3, 9100.00, "bank",3,3,NULL),
( 4, 8000.00, "visa",1, NULL,3),
( 5, 12000.00, "bank",3,NULL,7);




