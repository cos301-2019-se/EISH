drop table devices;

create table Devices(
 device_id serial primary key,
	device_name text not null,
	device_topic text not null,
	device_min_watt int not null,
	device_max_watt int not null,
	device_type text not null,
	device_state boolean not null,
	device_autostart boolean not null,
	device_priority int not null CHECK(0<=device_priority<=2)
);
insert into Devices(device_name,device_topic,device_min_watt,device_max_watt,device_type,device_state,device_autostart,device_priority) values('lg fridge','cmnd/sonoff-fridge/power',100,200,'Fridge',false,false,2);
select * from Devices;



select * from deviceconsumption;
drop table deviceconsumption;
create table deviceconsumption(
consumption_id serial not null,
	device_id serial references devices(device_id) not null, 
	consumption float not null,
	timeofconsumption bigint not null
);
insert into deviceconsumption(device_id,consumption,timeofconsumption) values(1,123.8,15321484654);
select * from deviceconsumption;


drop table generators;
select * from generators;
create table generators(
 generator_id serial primary key not null,
	generator_name text not null,
	generator_topic text not null,
	generator_type text not null,
	generator_min_capacity int not null,
	generator_max_capacity int not null
);
drop table generatorgeneration;

create table generatorgeneration(
	generation_id serial primary key not null,
	capacity float not null,
	timeofgeneration bigint not null,
	generator_id serial references generators(generator_id)
	

);
select * from generatorgeneration;