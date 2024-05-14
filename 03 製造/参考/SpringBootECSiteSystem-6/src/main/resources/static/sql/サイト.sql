create database empsystem;

USE empsystem;

CREATE TABLE `tbl_admin` (
  `admin_id` int(3) NOT NULL COMMENT '会員ID',
  `admin_name` varchar(100) NOT NULL COMMENT '会員名',
  `admin_email_address` varchar(255) NOT NULL COMMENT 'メールアドレス',
  `admin_password` varchar(100) NOT NULL COMMENT'パスワード',
  PRIMARY KEY (`admin_id`)
);

CREATE TABLE `tbl_category` (
  `category_id` int(3) NOT NULL COMMENT 'カテゴリID',
  `category_name` varchar(100) NOT NULL COMMENT 'カテゴリ名',
  `category_description` varchar(255) NOT NULL COMMENT 'カテゴリ詳細',
  `publication_status` char(1) NOT NULL COMMENT '表示状態',
  PRIMARY KEY (`category_id`)
) ;

CREATE TABLE `tbl_manufacture` (
  `manufacture_id` int(3) NOT NULL COMMENT 'メーカーID',
  `manufacture_name`varchar(255)NOT NULL COMMENT 'メーカー名',
  `about_manufacture` varchar(255) NOT NULL COMMENT 'メーカー詳細',
  `publication_status` char(1) NOT NULL COMMENT '表示状態',
  PRIMARY KEY (`manufacture_id`)
  );


CREATE TABLE `tbl_product` (
  `product_id` int(7) NOT NULL COMMENT '商品ID',
  `product_name` varchar(100) NOT NULL COMMENT '商品名',
  `product_code` varchar(100) NOT NULL COMMENT '商品コード',
  `category_id` int(3) NOT NULL COMMENT 'カテゴリID',
  `manufacture_id` int(3) NOT NULL COMMENT 'メーカーID',
  `product_price` Float(10) NOT NULL COMMENT '商品単価',
  `product_quantity` int(10) NOT NULL COMMENT '在庫数目',
  `product_description` varchar(255) NOT NULL COMMENT '商品詳細',
  `product_image` blob NOT NULL COMMENT '商品イメージ',
  `publication_status` char(1) NOT NULL COMMENT '表示状態',
  PRIMARY KEY (`product_id`),
  foreign key(`manufacture_id`) references tbl_manufacture(manufacture_id),
  foreign key(`category_id`) references tbl_category(category_id)
  );

REPLACE INTO tbl_manufacture VALUES ('001', 'ティッシュ','スコッティ','1');
REPLACE INTO tbl_manufacture VALUES ('002', '明治','世界NO.1食品メーカー','1');
REPLACE INTO tbl_manufacture VALUES ('003', '読売','新聞、本発行','1');
REPLACE INTO tbl_category values('001','日用品','家庭日用品','1');
REPLACE INTO tbl_category values('002','食品','家庭食品','2');
REPLACE INTO tbl_category values('003','本','家庭本','3');

REPLACE INTO tbl_product values('1000012','ティッシュ','20220701','001','001',9.9,0000000099,'スコッティ','','1');
REPLACE INTO tbl_product values('1000013','マスク','20220702','001','001',19.9,0000000099,'医療用','','1');
REPLACE INTO tbl_product values('1000014','アルコール','20220703','001','001',29.9,0000000099,'消毒用','','1');
REPLACE INTO tbl_product values('1000015','カップラーメン','20220704','002','002',39.9,0000000099,'美味しいラーメン','','1');
REPLACE INTO tbl_product values('1000016','牛乳','20220705','002','002',12.9,0000000099,'明治美味しい牛乳','','1');
REPLACE INTO tbl_product values('1000017','パン','20220706','002','002',6,0000000099,'フワフワパン','','1');
REPLACE INTO tbl_product values('1000018','本1','20220707','003','003',69,0000000099,'教育','','1');
REPLACE INTO tbl_product values('1000019','本2','20220708','003','003',79,0000000099,'自然','','1');
REPLACE INTO tbl_product values('1000020','本2','20220708','003','003',89,0000000099,'文学','','1');

use empsystem;
grant file on *.* to 'root'@'localhost';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000012';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000013';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000014';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000015';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000016';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000017';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000018';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000019';
update tbl_product set product_image =load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png') where product_id = '1000020';


alter table tbl_product add(product_imagefile varchar(255)); 
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000012';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000013';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000014';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000015';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000016';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000017';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000018';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000019';
update tbl_product set product_imagefile ='C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1000012.png' where product_id = '1000020';