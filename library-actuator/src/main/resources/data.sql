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

