USE designershop;

DROP TABLE IF EXISTS product_likes;

CREATE TABLE product_likes (
    user_id NVARCHAR(10) NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (user_id , product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;