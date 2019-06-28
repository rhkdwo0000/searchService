
alter table [테이블명] add [컬럼명] [타입] [옵션]; 

ex) alter table [테이블명] add [컬럼명] varchar(100) not null default '0'; 

timestamp DEFAULT CURRENT_TIMESTAMP
ALTER TABLE processCheck MODIFY start_time date;
ALTER TABLE news MODIFY insert_time timestamp DEFAULT NOW();
ALTER TABLE processCheck MODIFY start_time timestamp DEFAULT NOW();
ALTER TABLE processCheck MODIFY end_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

중복데이터 삭제 
 delete from news_article where news_article_id not in( select * from (select MIN(news_article_id) from news_article group by image_url) as temp);

중복데이터 검색
 SELECT * FROM news_article GROUP BY image_url HAVING COUNT(image_url) > 1
 
 
 
CREATE TABLE `crawling_job` (
  `crawling_job_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `news_insert_count` int(11) DEFAULT NULL,
  `crawling_site` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`crawling_job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;



CREATE TABLE `news_article` (
  `news_article_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `content` mediumtext COLLATE utf8_bin,
  `image_url` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `crawling_url` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `latest_news_article_flag` int(11) DEFAULT NULL,
  `crawling_job_id` int(11) DEFAULT NULL,
  `newspaper` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `insert_time` date DEFAULT NULL,
  PRIMARY KEY (`news_article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22231 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


 
 
 
 

