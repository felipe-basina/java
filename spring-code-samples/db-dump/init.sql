CREATE DATABASE IF NOT EXISTS generic_tables;

USE generic_tables;

CREATE TABLE IF NOT EXISTS lock_table (
    id MEDIUMINT NOT NULL,
    dat_lock timestamp NOT NULL,
    primary key (id)
);

INSERT INTO lock_table (id, dat_lock) values
(1, now());