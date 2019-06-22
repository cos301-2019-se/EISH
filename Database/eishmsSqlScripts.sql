 CREATE TYPE userType AS ENUM ('ROLE_ADMIN', 'ROLE_RESIDENT', 'ROLE_GUEST');

 create table homeuser(
 userid serial  primary key,
 username text not null,
 useremail text not null,
 userpassword text not null,
 userlocationtopic text not null,
 usertype userType not null,
 userexpirydate TIMESTAMP not null
 );

 insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('admin','admin@eishms.io','admin','owntracks/admin/iPhone/house','ROLE_ADMIN','2050-01-31 00:00:00');
select * from homeuser;


