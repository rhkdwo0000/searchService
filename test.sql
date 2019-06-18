select count(*) from news;
select * from news;
select * from processCheck;

delete from processCheck;
delete from news;

desc processCheck;
SELECT * FROM NEWS WHERE id = '5879';

create table news(
id int(11) AUTO_INCREMENT primary key,
sub_title varchar(500),
sub_content varchar(1000),
sub_image varchar(1000),
newspaper varchar(100),
link varchar(500),
date varchar(150),
firstCheck int(11)
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

alter table news add insert_time timestamp; 
alter table news add processCheck_id int; 

alter table [테이블명] add [컬럼명] [타입] [옵션]; 

ex) alter table [테이블명] add [컬럼명] varchar(100) not null default '0'; 

alter table processCheck add count int;

timestamp DEFAULT CURRENT_TIMESTAMP
ALTER TABLE processCheck MODIFY start_time date;
ALTER TABLE news MODIFY insert_time timestamp DEFAULT NOW();
ALTER TABLE processCheck MODIFY start_time timestamp DEFAULT NOW();
ALTER TABLE processCheck MODIFY end_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

select * from news order by id desc limit 1;
select * from news order by id asc limit 1; 

desc news;
 
UPDATE news SET firstCheck = '0';
alter table news add firstCheck int; 

중복데이터 삭제 
 delete from news_article where news_article_id not in( select * from (select MIN(news_article_id) from news_article group by image_url) as temp);

 
 
news table
create table news_account( 
news_accountid int(11) AUTO_INCREMENT primary key,
title varchar(500),
mainContent text(), 
subContent varchar(1000),
image_url varchar(1000), 이미지 url
runningTextLink varchar(500),
duplicationCheck int(11), 가장 최신뉴스 
newsInsertTime timestamp DEFAULT NOW(),
processCheckId int(11)
);



processCheck table   
create table crawling_history( 
crawling_history_id int(11),
startTime timestamp DEFAULT NOW(),
endTime timestamp DEFAULT NOW(),
news_insert_Count int(11) 
);
 
 SELECT * FROM news_article GROUP BY image_url HAVING COUNT(image_url) > 1

 
 
 
 

