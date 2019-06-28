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

 insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('admin','admin@eishms.io','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_ADMIN','2050-01-31 00:00:00');
 insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Eben','eben@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_GUEST','2019-07-31 00:00:00');
insert into homeuser(username,useremail,userpassword,userlocationtopic,usertype,userexpirydate) values('Charl','charl@labs.epiuse.com','$2a$10$Es.jaJFtj/OIXfzq9dlnjOYKRItsGWhDLyiv8jxA7.76rVSy21sMi','owntracks/admin/iPhone/house','ROLE_RESIDENT','2019-07-31 00:00:00');

select * from homeuser;


