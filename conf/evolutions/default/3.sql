# --- !Ups
#set ignorecase true;

create table textSection (
  id int not null,
  body TEXT,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES section(id)
);

create table listSection (
  id int not null,
  listPos int not null,
  header TEXT,
  body TEXT,
  PRIMARY KEY(id, listPos),
  FOREIGN KEY (id) REFERENCES section(id)
);

-- create table section (
--   id  bigint not null,
--   name varchar(255) not null,
--   body text,
--   tooltip text,
--   constraint pk_section primary key (id)
-- );

# --- !Downs
drop table if exists textSection;
drop table if exists listSection;
