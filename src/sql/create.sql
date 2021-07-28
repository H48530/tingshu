create table user (
	uid int primary key auto_increment,
    username varchar(40) unique not null,
    nickname varchar(40) not null,
    password char(128) not null
);

create table album (
	aid int primary key auto_increment,
    name varchar(40) not null,
    uid int not null,
    cover varchar(200) not null,
    count int not null default 0
);

create table story (
	sid int primary key auto_increment,
    name varchar(40) not null,
    aid int not null,
    count int not null default 0,
    audio longblob not null
);