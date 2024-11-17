USE designershop;

DROP TABLE IF EXISTS coupons_product_category;

CREATE TABLE coupons_product_category (
    coupon_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (coupon_id , category_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
