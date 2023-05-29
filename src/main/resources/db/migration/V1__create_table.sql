CREATE TABLE public._user (
                              id serial primary key,
                              email varchar(255) unique ,
                              firstname varchar(255),
                              lastname varchar(255),
                              password varchar(255),
                              role varchar(255) check (role in ('CUSTOMER','ADMIN'))
);

create table public.token (
                              expired boolean not null,
                              id serial primary key,
                              revoked boolean not null,
                              user_id integer not null references _user (id),
                              token varchar(255) unique,
                              token_type varchar(255) check (token_type in ('BEARER'))
);
