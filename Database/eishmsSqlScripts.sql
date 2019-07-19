 /*
USERS AND ENTITIES RELATED TO USERS
*/
 CREATE TYPE userType AS ENUM ('ROLE_ADMIN', 'ROLE_RESIDENT', 'ROLE_GUEST');
 
 create table homeuser(
 userid serial  primary key,
 username text not null unique,
 useremail text not null,
 userpassword text not null,
 userlocationtopic text not null unique,
 usertype userType not null,
 userexpirydate TIMESTAMP not null
 );

insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('admin','admin@eishms.io','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','house','ROLE_ADMIN','2050-01-31 00:00:00');
insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Eben','eben@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','house','ROLE_GUEST','2019-07-31 00:00:00');
insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Charl','charl@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','house','ROLE_RESIDENT','2019-07-31 00:00:00');
				
select * from homeuser;

CREATE TABLE homeuserpresence(
	homeuserpresencetimestamp timestamp not null,
	homeuserpresence boolean not null,
	userid serial references homeuser(userid) not null,
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
deviceconsumption float,
 primary key(deviceid,deviceconsumptiontimestamp)	
);

drop table deviceconsumption;
drop table device;

CREATE TABLE homeconsumption(
homeconsumptiontimestamp timestamp not null primary key,
homeconsumption float not null
);
insert into devicetype(devicetypename,devicetypestates) values('TV',ARRAY['ON','OFF','STANDBY']);


/*selects from now till last minute*/
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 minutes';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '10 minutes';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 hour';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 day';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 week';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 month';
select * from deviceconsumption where deviceconsumptiontimestamp >= NOW() - INTERVAL '1 year';

select * from deviceconsumption where deviceid =1  and deviceconsumptiontimestamp >= NOW() - INTERVAL '1 minutes' 
order by deviceconsumptiontimestamp desc limit 1;


select * from deviceconsumption
where deviceconsumptiontimestamp between now()-interval '3 weeks' and now();

select deviceid, AVG(deviceconsumption) 
from deviceconsumption
where deviceconsumptiontimestamp
between now()- interval '10 minutes' and now()
group by deviceid;

/*
GENERATORS AND ENTITIES RELATED TO GENERATORS
*/
CREATE TYPE generatorPriorityType AS ENUM ('PRIORITY_USEWHENEMPTY','PRIORITY_USEWHENCRITICAL', 'PRIORITY_ALWAYSUSE', 'PRIORITY_NEUTRAL');

CREATE TABLE generator(
	generatorid serial primary key,
	generatorname text not null unique,
	generatorurl text not null unique,
	generatorpriority generatorPriorityType not null,
	generatorstates text[] not null
);

CREATE TABLE generatorgeneration(
generatorid serial references generator(generatorid) not null,
generatorgenerationtimestamp timestamp not null,
generatorgenerationstate text not null,
generatorgenerationcapacity float,
 primary key(generatorid,generatorgenerationtimestamp));

insert into generator("generatorname","generatorurl","generatorpriority","generatorstates")
values ('PV System','http://eishms.ddns.net:3001/v2/installations/0/SolarCharger','PRIORITY_NEUTRAL',ARRAY['OFFLINE','ONLINE']);	

/*
BATTERY AND ENTITIES RELATED 
*/

CREATE TYPE powerStateType AS ENUM ('POWERSTATE_FULL', 'POWERSTATE_NORMAL', 'POWERSTATE_LOW', 'POWERSTATE_CRITICALLYLOW','POWERSTATE_EMPTY','POWERSTATE_OFFLINE');
CREATE TYPE chargingState AS ENUM ('CHARGINGSTATE_CHARGING','CHARGINGSTATE_DISCHARGING','CHARGINGSTATE_IDLE','CHARGINGSTATE_OFFLINE');


CREATE TABLE batterycapacity(
batterycapacityid serial primary key,
batterycapacitystorage int not null,
batterycapacitycurrentpower int not null,
batterycapacitypowerstate powerStateType not null,
batterycapacitychargingstate chargingState not null,
batterycapacitytimestamp timestamp not null,
batterycapacitypowerpercentage int not null
);




/*

WEATHER AND ENTITIES RELATED TO WEATHER
*/
CREATE TABLE weather(
	weaterid serial primary key,
	weatherlocation text not null,
    weatherdescription text not null,
    weathericon text not null,
    weathertemperature float not null,
    weatherhumidity int not null,
    weatherpressure float not null, 
    weatherwindspeed float not null,
    weatherlastobtime timestamp not null
);
