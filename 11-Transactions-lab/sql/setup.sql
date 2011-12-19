DROP DATABASE IF EXISTS intertech;
CREATE DATABASE IF NOT EXISTS intertech;
Use intertech;
DROP TABLE IF EXISTS contact;
CREATE TABLE contact (
    id integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    date_of_birth date,
    married bit,
    children INT(20)
) Engine=InnoDB;
INSERT INTO contact (first_name, last_name, date_of_birth, married, children) VALUES ('Seamus', 'White', '2000-02-13', false, 0);
DROP TABLE IF EXISTS contact_audit;
CREATE TABLE contact_audit (
    id integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    contact_id integer,
    operation varchar(50),
    effective timestamp
) Engine=InnoDB;
commit;
Drop User 'intertech'@'localhost';
Drop User 'intertech'@'%';
Create User 'intertech'@'localhost' Identified BY 'intertech';
Create User 'intertech'@'%' Identified BY 'intertech';
Grant All on intertech.* to 'intertech'@'localhost';
Grant All on intertech.* to 'intertech'@'%';
commit;
