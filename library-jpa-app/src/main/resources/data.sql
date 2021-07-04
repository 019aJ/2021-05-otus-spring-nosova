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