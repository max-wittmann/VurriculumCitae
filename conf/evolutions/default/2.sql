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

create table compoundSection (
  parentId int not null,
  sectionId int not null,
  sectionPos int not null,
  PRIMARY KEY(parentId, sectionId),
  FOREIGN KEY (parentId) REFERENCES section(id),
  FOREIGN KEY (sectionId) REFERENCES section(id)
);

# --- !Downs
drop table if exists textSection;
drop table if exists listSection;
drop table if exists compoundSection;