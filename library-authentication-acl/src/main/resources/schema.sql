drop table if exists comments CASCADE;
drop table if exists books CASCADE;
drop table if exists authors CASCADE;
drop table if exists genres CASCADE;
drop table if exists library_users CASCADE;

create table library_users (
       id bigint generated by default as identity,
        name varchar(255),
        password varchar(255),
        roles varchar(255),
        primary key (id)
    );

create table authors (
       id bigint generated by default as identity,
        name varchar(255),
        surname varchar(255),
        primary key (id)
    );
create table books (
       id bigint generated by default as identity,
        title varchar(255),
        author_id bigint,
        genre_id bigint,
        primary key (id)
    );
create table comments (
       id bigint generated by default as identity,
        text varchar(255),
        book_id bigint references books(id) on delete cascade,
        primary key (id)

    );
create table genres (
       id bigint generated by default as identity,
        name varchar(255),
        primary key (id)
    );
alter table books
       add constraint fk_author
       foreign key (author_id)
       references authors;
alter table books
       add constraint fk_genre
       foreign key (genre_id)
       references genres;
--
-- ACL Schema
--

CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);