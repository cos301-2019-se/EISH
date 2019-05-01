
create table devices(
	device_id serial Primary key,
	device_name text not null,
	publish_topic text not null,
	subscribe_topic text not null,
	min_watt int not null,
	max_watt int not null,
	device_type text not null,
	device_state boolean
);
insert into devices(device_name,publish_topic,subscribe_topic,min_watt,max_watt,device_type,device_state)
values('lenovo','stat/sonoff-laptop/POWER','cmnd/sonoff-laptop/Power',30,50,'laptop',false);
select * from devices;