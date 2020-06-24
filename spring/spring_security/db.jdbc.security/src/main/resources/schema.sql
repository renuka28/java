create table custom_users_table(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table custom_authorities_table (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references custom_users_table(username)
);
create unique index ix_auth_username on custom_authorities_table (username,authority);