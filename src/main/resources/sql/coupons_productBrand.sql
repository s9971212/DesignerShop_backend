USE designershop;

DROP TABLE IF EXISTS coupons_product_brand;

CREATE TABLE coupons_product_brand (
    coupon_id INT NOT NULL,
    brand_id INT NOT NULL,
    PRIMARY KEY (coupon_id , brand_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
