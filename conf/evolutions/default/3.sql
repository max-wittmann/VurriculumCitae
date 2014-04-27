# --- !Ups
INSERT INTO section (id, pos, sectionType, name) VALUES (0, 0, 'TextSection', 'About Me');
INSERT INTO textSection(id, body) VALUES (0, "<p>I'm <b>Max!</b></p><p>Don't eat my brains...</p>");

INSERT INTO section (id, pos, sectionType, name) VALUES (1, 1, 'TextSection', 'Personal Info');
INSERT INTO textSection(id, body) VALUES (1, "Software developer, Man, Pickle");

INSERT INTO section (id, pos, sectionType, name) VALUES (2, 2, 'ListSection', 'Education');
INSERT INTO listSection(id, listPos, header, body) VALUES (2, 0, "2008-2012, PhD", "Did my doctorate!");
INSERT INTO listSection(id, listPos, header, body) VALUES (2, 1, "2008-2012, Honours (Information Technology)", "Yep");
INSERT INTO listSection(id, listPos, header, body) VALUES (2, 2, "2008-2012, Bachelor of Computer Science", "What can I say?");

INSERT INTO section (id, pos, sectionType, name) VALUES (3, 3, 'ListSection', 'Employment History');
INSERT INTO listSection(id, listPos, header, body) VALUES(3, 0, "2008-2012, ThoughtWorks", "At TW");
INSERT INTO listSection(id, listPos, header, body) VALUES(3, 1, "2008-2012, Lecturer", "Teaching");

INSERT INTO section (id, pos, sectionType, name) VALUES (4, 4, 'ListSection', 'Skills');
INSERT INTO listSection(id, listPos, header, body) VALUES (4, 0, "Header a", "Blah");
INSERT INTO listSection(id, listPos, header, body) VALUES (4, 1, "Header a", "Blah");
INSERT INTO listSection(id, listPos, header, body) VALUES (4, 2, "Header a", "Blah");

# --- !Downs
