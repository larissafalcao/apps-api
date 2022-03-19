create table if not exists app(
    id serial primary key,
    name varchar(255) not null,
    owner varchar(255) not null,
    type varchar(255) not null,
    price double precision not null
);
