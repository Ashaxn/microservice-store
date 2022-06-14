DROP DATABASE IF EXISTS reserverDB;

CREATE DATABASE reserverDB;
USE reserverDB;

SET @@SESSION.auto_increment_increment=1;

CREATE TABLE reserver(
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
    userName VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	regiteredAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	updatedAt DATETIME DEFAULT NULL,
	token VARCHAR(100) DEFAULT NULL,
	
	CONSTRAINT pk_reserver PRIMARY KEY(id)
);


-- dummy data
INSERT INTO reserver(userName, password, email)
VALUES
("Amila Bandara", "sljdkhsisdsd98^&%tg", "amila@gmail.com" ),
("Milana Perera", "skjd7dsd4s6fds^7j", "milana@gmail.com" ),
("Akila Sanjaya", "ssdsdsdjskdsdsd", "akila@gmail.com" ),
("Madawa Dilshan", "sksdsdopio03s^]e7j", "madawa@gmail.com" ),
("Kalana Sandeepa", "ds;dlsodjsdlsd^soidhsod", "kalana@gmail.com" );



