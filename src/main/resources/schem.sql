create table if not exists user(
    username varchar(60) unique,
    password text null,
    primary key(username)
);

create table if not exists otp(
    username varchar(60) not null,
    code varchar(60) null,
    primary key(username)
);