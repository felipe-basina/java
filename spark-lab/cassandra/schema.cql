CREATE KEYSPACE IF NOT EXISTS MUSIC WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use MUSIC;

Create table IF NOT EXISTS MusicPlaylist
    (
        SongId int,
        SongName text,
        Year int,
        Singer text,
        Primary key(SongId, SongName)
    );

insert into music.musicplaylist (SongId, SongName, Year, Singer) values (1001, 'Rebirth', 2001, 'Angra');
insert into music.musicplaylist (SongId, SongName, Year, Singer) values (2001, 'Ever Dream', 2002, 'Nightwish');
insert into music.musicplaylist (SongId, SongName, Year, Singer) values (2001, 'Bless the Child', 2002, 'Nightwish');

---------------------------
-- CREATES A KEYSPACE
---------------------------
CREATE KEYSPACE IF NOT EXISTS user_management
  WITH REPLICATION = {
    'class' : 'SimpleStrategy',
    'replication_factor' : 1
  };

---------------------------
-- SELECT THE KEYSPACE
---------------------------
USE user_management;

---------------------------
-- CREATES A NEW TABLE
---------------------------
CREATE TABLE IF NOT EXISTS credentials_by_email (
  email     text,
  password  text,
  user_id   UUID,
  PRIMARY KEY(email)
);

---------------------------
-- CREATES A NEW TABLE
---------------------------
CREATE TABLE IF NOT EXISTS profiles_by_id (
  user_id    UUID,
  first_name text,
  last_name  text,
  PRIMARY KEY(user_id)
);

---------------------------
-- INSERTS SOME DATA
---------------------------
INSERT INTO credentials_by_email
  (email, password, user_id)
  VALUES(
    'aa@gmail.com',
    '@l3xL0v3$C@ss@ndr@',
    11111111-1111-1111-1111-111111111111
  );
INSERT INTO profiles_by_id
    (user_id, last_name, first_name)
    VALUES(
      11111111-1111-1111-1111-111111111111,
      'Artichoke',
      'Alex'
    );
INSERT INTO credentials_by_email
  (email, password, user_id)
  VALUES(
    'bb@gmail.com',
    'C@ss@ndr@R0ck$',
    22222222-2222-2222-2222-222222222222
  );
INSERT INTO profiles_by_id
  (user_id, last_name, first_name)
  VALUES(
    22222222-2222-2222-2222-222222222222,
    'Broccoli',
    'Bailey'
  );
INSERT INTO credentials_by_email
  (email, password, user_id)
  VALUES(
    'cc@gmail.com',
    'C@ss@ndr@43v3r',
    33333333-3333-3333-3333-333333333333
  );
INSERT INTO profiles_by_id
  (user_id, last_name, first_name)
  VALUES(
    33333333-3333-3333-3333-333333333333,
    'Cabbage',
    'Chris'
  );

------------------------------------------------------
-- CREATES A NEW TABLE
-- THIS IS AN ALTERNATIVE TO JOIN TABLES
------------------------------------------------------
CREATE TABLE IF NOT EXISTS users_by_email (
  email     text,
  password  text,
  first_name text,
  last_name  text,
  PRIMARY KEY(email)
);

INSERT INTO users_by_email
  (email, password, last_name, first_name)
  VALUES(
    'aa@gmail.com',
    '@l3xL0v3$C@ss@ndr@',
    'Artichoke',
    'Alex'
  );
INSERT INTO users_by_email
  (email, password, last_name, first_name)
  VALUES(
    'bb@gmail.com',
    'C@ss@ndr@R0ck$',
    'Broccoli',
    'Bailey'
  );
INSERT INTO users_by_email
  (email, password, last_name, first_name)
  VALUES(
    'cc@gmail.com',
    'C@ss@ndr@43v3r',
    'Cabbage',
    'Chris'
  );

------------------------------------------------------
-- CREATES A NEW TABLE
-- THIS IS AN ALTERNATIVE TO JOIN TABLES
------------------------------------------------------
CREATE TABLE IF NOT EXISTS users_by_name (
  last_name   text,
  first_name  text,
  email       text,
  PRIMARY KEY((last_name, first_name))
);

INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Artichoke',
    'Alex',
    'aa@gmail.com'
  );
INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Broccoli',
    'Bailey',
    'bb@gmail.com'
  );
INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Cabbage',
    'Chris',
    'cc@gmail.com'
  );

------------------------------------------------------
-- DELETES A TABLE
------------------------------------------------------
DROP TABLE users_by_name;

------------------------------------------------------
-- CREATES A NEW TABLE
-- THIS SETS A COLUMN 'EMAIL' AS PARTITION KEY
-- TO SET UNIQUENESS VALUE. WITHOUT THIS THE RECORD WILL
-- BE OVERRIDDEN
------------------------------------------------------
CREATE TABLE IF NOT EXISTS users_by_name (
  last_name   text,
  first_name  text,
  email       text,
  PRIMARY KEY((last_name, first_name), email)
);

INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Artichoke',
    'Alex',
    'aa@gmail.com'
  );
INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Broccoli',
    'Bailey',
    'bb@gmail.com'
  );
INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Cabbage',
    'Chris',
    'cc@gmail.com'
  );
INSERT INTO users_by_name
  (last_name, first_name, email)
  VALUES(
    'Artichoke',
    'Alex',
    'aart@gmail.com'
  );

CREATE KEYSPACE IF NOT EXISTS random_stuah WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE random_stuah;

DROP TABLE random_stuff;

CREATE TABLE IF NOT EXISTS random_stuff (
 processed_date date,
 num_partition int,
 uid       text,
 PRIMARY KEY((processed_date, num_partition), uid)
);

INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 0, '43b5b7e1-5ae1-4094-b83e-2631a2226f24');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 0, 'aa73e47f-211f-49d5-bb28-ee97742b7b2d');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 0, 'f02c8325-1d1f-44d2-a0d7-96715e57f06b');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 1, 'a07517cd-571b-44b6-929a-7601606b243e');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 2, 'ce160f87-f167-4ad0-ba45-8d11846a7522');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 2, 'ba2345c5-d0b9-4440-a43f-1af380b427bd');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 3, '7a40f887-bc27-417a-bbd5-c993265bec78');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 3, '892675f8-9d6c-45f4-b9c0-9e2b99f98157');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 4, 'eb910759-92f8-46c9-ab5e-b85dfd7c418b');
INSERT INTO random_stuff (processed_date, num_partition, uid) values ('2021-01-01', 5, '0aa05b4e-6474-4fa7-ac98-df6580331cd4');