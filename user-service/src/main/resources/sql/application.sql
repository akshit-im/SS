DROP DATABASE MAVERICKS;
CREATE DATABASE MAVERICKS;
USE MAVERICKS;
drop user 'Mavericks'@'localhost';
CREATE USER 'Mavericks'@'localhost' IDENTIFIED BY 'MATka@1234';
GRANT ALL PRIVILEGES ON MAVERICKS.* To 'Mavericks'@'localhost';
 
create table if not exists persistent_logins ( 
  username varchar(100) not null, 
  series varchar(64) primary key, 
  token varchar(64) not null, 
  last_used timestamp not null 
);

/*INSERT INTO APP_ROLES(ID,NAME,STATUS) VALUES(SUBSTRING(MD5(UUID()) FROM 1 FOR 24),'Adminstrator', 'Active');*/

/* Singh!@#app */
INSERT INTO USER(ID,CODE,EMAIL,ENTRY_DATE,LOGIN_ID,MOBILE,NAME,PASSWORD,STATUS,TYPE) VALUES(UUID(),'EXP360UI','exp360ui@gmail.com',NOW(),MD5(UUID()),1000000001,'EXP360 UI','$2a$10$gqJF/OS5.boZUc9hujAtpOgOiu7eAPgK4PiPoBhL5yuiSeUc6GRTi','Active',9b97cb83-5a2a-11ee-b57e-6c2408a73db9);

