DROP DATABASE IF EXISTS SuperHeroesTest;
CREATE DATABASE SuperHeroesTest;

use SuperHeroesTest;

CREATE TABLE Hero (
hero_id INT PRIMARY KEY AUTO_INCREMENT,
heroName varchar(15) ,
heroDescription varchar(15),
superpower varchar(15) );


CREATE TABLE Location(
location_id INT PRIMARY KEY AUTO_INCREMENT,
location_name varchar(15),
location_description varchar(15),
address varchar(15),
location varchar(20) );

CREATE TABLE Organisation(
org_id INT PRIMARY KEY AUTO_INCREMENT,
org_name varchar(15),
org_description varchar(15),
address varchar(15) );

CREATE TABLE Sighting(
sighting_id INT PRIMARY KEY AUTO_INCREMENT,
hero_id int,
location_id int,
dateTime datetime,
FOREIGN KEY (hero_id) REFERENCES Hero(hero_id),
FOREIGN KEY (location_id) REFERENCES Location(location_id) );

CREATE TABLE HeroAtOrg(
hero_id int,
org_id int,
FOREIGN KEY (hero_id) REFERENCES Hero(hero_id),
FOREIGN KEY (org_id) REFERENCES Organisation(org_id),
PRIMARY KEY (hero_id, org_id));

select * from hero;