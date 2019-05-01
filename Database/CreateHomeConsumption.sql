create table HomeConsumption(
	home_consumption_id serial primary key,
	start_time bigint not null,
	end_time bigint not null,
	total_consumption float not null
); 
insert into HomeConsumption(start_time,end_time,total_consumption)values(15321484654,18548484654,1680);
select * from HomeConsumption;