create table users_table_in_mysql(
	id INT not null primary key, 
	enabled boolean not null, 
    userName varchar(50) not null,
    password varchar(500) not null,
	roles varchar(500) not null    
);