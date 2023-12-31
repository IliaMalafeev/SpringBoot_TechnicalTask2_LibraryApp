create table person (
    id integer generated by default as identity primary key,
    full_name varchar(100) not null constraint name_unique unique,
    year_of_birth integer constraint person_y_o_b_check check (year_of_birth > 1900)
);

create table book (
    id integer generated by default as identity primary key,
    holder_id integer references person (id) on delete set null,
    title varchar (200) not null,
    author varchar (200) not null,
    year_of_publication integer not null,
    taken_at timestamp without time zone
)