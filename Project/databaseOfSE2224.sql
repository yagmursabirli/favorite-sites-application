create schema se2224_Project;
use se2224_Project;

create table userinfo(user_id int AUTO_INCREMENT primary key, username varchar(50) not null,
						password varchar(50) not null);
 alter table userinfo add unique (username);

                        
insert into userinfo (username, password) values('yagmur', 'yagmur123');
insert into userinfo (username, password) values('damla', 'damla123');
insert into userinfo (username, password) values('kaan', 'kaan123');
insert into userinfo (username, password) values('aydan', 'aydan123');

create table visits (
    visit_id INT AUTO_INCREMENT primary key,
    username varchar(50) NOT NULL,
    country varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    year int NOT NULL,
    season varchar(20) NOT NULL,
    Feature varchar(100) NOT NULL,
    comment TEXT,
    rating INT NOT NULL,
     foreign key (username) references userinfo(username)
);
 alter table visits add unique (visit_id);
create table shared_visits(id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    friend_username VARCHAR(50) NOT NULL,
    visit_id INT NOT NULL,
    FOREIGN KEY (visit_id) REFERENCES visits(visit_id));