drop database if exists blogDBTest;
create database blogDBTest;

use blogDBTest;

create table author(
author_id int primary key auto_increment,
author_name varchar(60) not null,
author_email varchar(40) not null,
author_password varchar(30) not null,
author_admin boolean default false
);

create table blog(
blog_id int primary key auto_increment,
blog_name varchar(50) not null,
author_id int not null,
blog_content mediumtext not null,
blog_creation_time datetime default current_timestamp,
blog_public boolean default true,
blog_approved boolean default false,
foreign key (author_id) references author(author_id)
);

create table images(
image_id int primary key auto_increment,
image_location varchar(30)
);

create table blog_images(
image_id int not null,
blog_id int not null,
primary key(image_id, blog_id),
foreign key (image_id) references images(image_id),
foreign key (blog_id) references blog(blog_id)
);

create table timer(
timer_id int primary key auto_increment,
blog_id int not null,
blog_start datetime not null default current_timestamp,
blog_expire datetime not null,
foreign key (blog_id) references blog(blog_id));

create table tag(
tag_id int primary key auto_increment,
tag_name varchar(30));

create table blog_tag(
blog_id int not null,
tag_id int not null,
primary key(blog_id, tag_id),
foreign key (blog_id) references blog(blog_id),
foreign key (tag_id) references tag(tag_id));

create table blog_page(
page_id int primary key auto_increment,
page_name varchar(30) NOT NULL,
page_location varchar(40) NOT NULL
);

create table page_images(
image_id int not null,
page_id int not null,
primary key(image_id, page_id),
foreign key (image_id) references images(image_id),
foreign key (page_id) references blog_page(page_id)
);

INSERT INTO author(author_name, author_password, author_email) values("Gage Gabaldon", "1234", "gage@gmail.com");
INSERT INTO author(author_name, author_password, author_email) values("Cole", "1234", "cole@gmail.com");
INSERT INTO blog(blog_name, author_id, blog_content) values("First Blog", 1, "hello, welcome to my first exciting blog that is sure to wow and blow up");
INSERT INTO blog(blog_name, author_id, blog_content) values("Here we go", 1, "lllleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeelllllllllllllllllllleeeeeeeeeeeeeee");
INSERT INTO blog(blog_name, author_id, blog_content) values("Another new Post", 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");