DROP DATABASE IF EXISTS buyerDB;

CREATE DATABASE buyerDB;
USE buyerDB;

SET @@SESSION.auto_increment_increment=1;

CREATE TABLE buyer(
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
    userName VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	regiteredAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	updatedAt DATETIME DEFAULT NULL,
	token VARCHAR(100) DEFAULT NULL,
	
	CONSTRAINT pk_buyer PRIMARY KEY(id)
);

CREATE TABLE favourites(
	userId INT NOT NULL AUTO_INCREMENT,
	productId INT NOT NULL,
	addedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	
	CONSTRAINT pk_favourites PRIMARY KEY(userId, productId),
	CONSTRAINT fk_buyer FOREIGN KEY(userId) REFERENCES buyer(id)
);

-- dummy data
INSERT INTO buyer(id, userName, password, email)
VALUES
(1,"Damith Akalanka", "sljdkhsisdsd98^&%tg", "damith@gmail.com" ),
(2,"Marlon Silva", "skjd7dsd4s6fds^7j", "marlon@gmail.com" ),
(3,"Sammani Bhagya", "ssdsdsdjskdsdsd", "sammani@gmail.com" ),
(4,"Akila Perera", "sksdsdopio03s^]e7j", "akila@gmail.com" ),
(5,"Amila Malinga", "ds;dlsodjsdlsd^soidhsod", "amila@gmail.com" );


INSERT INTO favourites(userId, productId)
VALUES
(1,1),
(1,2),
(2,1),
(2,4);

