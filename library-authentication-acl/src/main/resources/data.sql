insert into authors (name,surname) values ('Lev', 'Tolstoy');
insert into authors (name,surname) values ('Jane', 'Austen');
insert into authors (name,surname) values ('Agatha', 'Christie');
insert into authors (name,surname) values ('Stephen ', 'King');

insert into genres (name) values ('Detective');
insert into genres (name) values ('Novel');
insert into genres (name) values ('Horror');

insert into books (title, author_id, genre_id) values ('War and Peace', 1, 2);
insert into books (title, author_id, genre_id) values ('Pride and prejudice', 2, 2);
insert into books (title, author_id, genre_id) values ('Murder on the Orient Express', 3, 1);
insert into books (title, author_id, genre_id) values ('It', 4, 3);

insert into comments (text, book_id) values ('Boring', 3);
insert into comments (text, book_id) values ('So exiting', 3);
insert into comments (text, book_id) values ('Best book ever', 3);
insert into comments (text, book_id) values ('Who is the murder? Butler again?', 3);

insert into library_users (name, password, roles) values ('reader', '$2a$12$uux2IgDAql4IllVNMvUSj.bHWzGnebPsa6TwsgtVPjJseBc4pl.Py', 'READER');
insert into library_users (name, password, roles) values ('admin', '$2a$12$vJXdQPVLtDrtwhlG/cs5pu3fF.f5/Ton2Nob4PfHtxLp6QshCMwqG', 'ADMIN');
insert into library_users (name, password, roles) values ('editor', '$2a$12$u7.3iQ4fZ1XOck1fxTDUteDrdjegD2N33ms7W56Dq7K9ZzqGxbniO', 'ADMIN');

---
--- ACL DATA
---
INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin'),
(2, 1, 'reader'),
(3, 1, 'editor'),
(4, 1, 'ROLE_ADMIN'),
(5, 1, 'ROLE_READER');
INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.libraryauthenticationacl.models.Book');
---
---OBJECT_ID_CLASS: define the domain object class, links to ACL_CLASS table
---OBJECT_ID_IDENTITY: domain objects can be stored in many tables depending on the class. Hence, this field store the target object primary key
---PARENT_OBJECT: specify parent of this Object Identity within this table
---OWNER_SID: ID of the object owner, links to ACL_SID table
---ENTRIES_INHERITING: whether ACL Entries of this object inherits from the parent object (ACL Entries are defined in ACL_ENTRY table)
---
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0),
(4, 1, 4, NULL, 3, 0);

---
---ACL_OBJECT_IDENTITY: specify the object identity, links to ACL_OBJECT_IDENTITY table
---ACE_ORDER: the order of current entry in the ACL entries list of corresponding Object Identity
---SID: the target SID which the permission is granted to or denied from, links to ACL_SID table
---MASK: READ = 1  WRITE 2 CREATE 4 DELETE 8
---GRANTING: value 1 means granting, value 0 means denying
---AUDIT_SUCCESS and AUDIT_FAILURE: for auditing purpose
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
--admin
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),

(4, 2, 1, 1, 1, 1, 1, 1),
(5, 2, 2, 1, 2, 1, 1, 1),

(7, 3, 1, 1, 1, 1, 1, 1),
(8, 3, 2, 1, 2, 1, 1, 1),

(10, 4, 1, 1, 1, 1, 1, 1),
(11, 4, 2, 1, 2, 1, 1, 1),

--reader
(13, 1, 3, 2, 1, 1, 1, 1),
(14, 2, 3, 2, 1, 1, 1, 1),
(15, 3, 3, 2, 1, 1, 1, 1),
(16, 4, 3, 2, 1, 1, 1, 1),

--editor
(17, 1, 4, 3, 1, 1, 1, 1),
(18, 1, 5, 3, 2, 1, 1, 1),
(19, 1, 6, 3, 8, 1, 1, 1),

(20, 2, 4, 3, 1, 1, 1, 1),
(21, 2, 5, 3, 2, 1, 1, 1),
(22, 2, 6, 3, 8, 1, 1, 1),

(23, 3, 4, 3, 1, 1, 1, 1),
(24, 3, 5, 3, 2, 1, 1, 1),
(25, 3, 6, 3, 8, 1, 1, 1),

(26, 4, 4, 3, 1, 1, 1, 1),
(27, 4, 5, 3, 2, 1, 1, 1),
(28, 4, 6, 3, 8, 1, 1, 1);