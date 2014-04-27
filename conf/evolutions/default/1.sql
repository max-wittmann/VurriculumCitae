# --- !Ups
#set ignorecase true;

create table section (
  id int not null,
  pos int not null,
  sectionType varchar(255) not null,
  name varchar(255) not null,
  PRIMARY KEY (id)
);

-- create table section (
--   id  bigint not null,
--   name varchar(255) not null,
--   body text,
--   tooltip text,
--   constraint pk_section primary key (id)
-- );

# --- !Downs
drop table if exists section;