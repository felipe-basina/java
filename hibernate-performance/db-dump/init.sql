CREATE DATABASE IF NOT EXISTS library;

USE library;

CREATE TABLE IF NOT EXISTS author (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  first_name varchar(100),
  last_name varchar(100),
  version int,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS book (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  title varchar(100),
  version int,
  author_id MEDIUMINT,
  FOREIGN KEY (author_id) REFERENCES author(id),
  primary key (id)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

insert into author (id, first_name, last_name, version) values
(1, 'John', 'Doe', 1),
(2, 'Mary', 'Loo', 1);

insert into book (id, title, version, author_id) values
(1, 'My first book', 1, 1),
(2, 'The fairy tales vol.1', 1, 2);