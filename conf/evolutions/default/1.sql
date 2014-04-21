# --- !Ups
#set ignorecase true;

create table test (
  id int not null,
  body text
);

create table section (
  id int not null,
  name varchar(255) not null,
  body text,
  tooltip text,
  PRIMARY KEY (id)
)

-- create table section (
--   id  bigint not null,
--   name varchar(255) not null,
--   body text,
--   tooltip text,
--   constraint pk_section primary key (id)
-- );

# --- !Downs
drop table if exists section;