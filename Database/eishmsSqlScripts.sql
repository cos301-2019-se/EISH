 /*
USERS AND ENTITIES RELATED TO USERS
*/
 CREATE TYPE userType AS ENUM ('ROLE_ADMIN', 'ROLE_RESIDENT', 'ROLE_GUEST');
 
 create table homeuser(
 userid serial  primary key,
 username text not null unique,
 useremail text not null,
 userpassword text not null,
 userlocationtopic text not null,
 usertype userType not null,
 userexpirydate TIMESTAMP not null
 );

 insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('admin','admin@eishms.io','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_ADMIN','2050-01-31 00:00:00');
 insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Eben','eben@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_GUEST','2019-07-31 00:00:00');
insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Charl','charl@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_RESIDENT','2019-07-31 00:00:00');

select * from homeuser;

CREATE TABLE homeuserpresence(
	homeuserpresencetimestamp timestamp not null,
	homeuserpresence boolean not null,
	userid serial references homeuserpresence(userid) not null,
	primary key(userid,homeuserpresencetimestamp));


/*
DEVICES AND ENTITIES RELATED TO DEVICES
*/
CREATE TYPE devicePriorityType AS ENUM ('PRIORITY_MUSTHAVE', 'PRIORITY_ALWAYSON', 'PRIORITY_NEUTRAL', 'PRIORITY_NICETOHAVE');


CREATE TABLE device(
deviceid serial primary key,
devicename text not null unique,
devicetopic text not null unique,
devicepriority devicePriorityType not null,
devicestates text[] not null);

CREATE TABLE deviceconsumption(
deviceid serial references device(deviceid) not null,
deviceconsumptiontimestamp timestamp not null,
deviceconsumptionstate text not null,
deviceconsumption float not null,
 primary key(deviceid,deviceconsumptiontimestamp)	
);

drop table deviceconsumption;
drop table device;

CREATE TABLE homeconsumption(
homeconsumptiontimestamp timestamp not null primary key,
homeconsumption float not null
);
insert into devicetype(devicetypename,devicetypestates) values('TV',ARRAY['ON','OFF','STANDBY']);

/*
GENERATORS AND ENTITIES RELATED TO GENERATORS
*/
CREATE TABLE generator(
	generatorid serial primary key,
	generatorname text not null unique,
	generatorurl text not null unique,
);

CREATE TABLE generatorgeneration();	
