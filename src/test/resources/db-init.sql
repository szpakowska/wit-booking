create table item
(
    id          int auto_increment,
    name        varchar(50) not null,
    description varchar(50) not null,
    price       decimal     not null,
    constraint item_pk
        primary key (id)
);
create table item_attribute
(
    id             int auto_increment,
    attribute_name varchar(50) not null,
    constraint attribute_pk
        primary key (id)
);
create table item_to_attribute
(
    item_id      int not null,
    attribute_id int not null,
    constraint attribute_item_pk
        primary key (item_id, attribute_id)
);
create table event
(
    id        int auto_increment,
    item_id   int       not null,
    time_from timestamp not null,
    time_to   timestamp not null,
    constraint event_pk
        primary key (id)
);
create table booking
(
    id               int auto_increment,
    event_id         int         not null,
    customer_name    varchar(50) not null,
    customer_contact varchar(50) not null,
    constraint booking_pk
        primary key (id)

);

insert into item (id, name, description, price)
values (1, "Usuwanie przebarwień twarz", "...", 130),
       (2, "Usuwanie przebarwień dłonie", "...", 60),
       (3, "Mezoterapia igłowa twarz", "...", 450),
       (4, "Mezoterapia mikroigłowa twarz", "...", 260),
       (5, "Leczenie trądziku", "...", 140);

insert into item_attribute (id, attribute_name)
    value (1, "twarz"),
    (2, "dłonie"),
    (3, "szyja");

insert into item_to_attribute (item_id, attribute_id)
values (1, 1),
       (2, 2),
       (3, 1),
       (4, 1),
       (5, 1);

insert into event (id, item_id, time_from, time_to)
values (1, 1, '2023-09-13 12:00:00', '2023-09-13 12:50:00'),
       (2, 1, '2023-09-14 12:00:00', '2023-09-14 12:50:00');

insert into booking (id, event_id, customer_name, customer_contact)
values (1, 1, 'ĄąłłóóĘęŚśĆćŃń', 'nie podane');
