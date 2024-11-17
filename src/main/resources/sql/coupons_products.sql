USE designershop;

DROP TABLE IF EXISTS coupons_products;

CREATE TABLE coupons_products (
    coupon_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (coupon_id , product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
