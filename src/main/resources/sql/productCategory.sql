USE designershop;

DROP TABLE IF EXISTS product_category;

CREATE TABLE product_category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category NVARCHAR(50) NOT NULL,
    UNIQUE (category)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO product_category (category) VALUES
('女生衣著'),
('男生衣著'),
('運動/健身'),
('男女鞋'),
('女生配件/黃金'),
('美妝保養'),
('娛樂、收藏'),
('寵物'),
('手機平板與周邊'),
('3C與筆電'),
('家電影音'),
('服務、票券'),
('書籍及雜誌期刊'),
('居家生活'),
('美食、伴手禮'),
('汽機車零件百貨'),
('電玩遊戲'),
('保健、護理'),
('嬰幼童與母親'),
('女生包包/精品'),
('男生包包與配件'),
('戶外/旅行'),
('文創商品'),
('其他類別');
