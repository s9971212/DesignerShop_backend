USE designershop;

DROP TABLE IF EXISTS coupon_usages;

CREATE TABLE coupon_usages (
    usage_id INT AUTO_INCREMENT PRIMARY KEY,
    used_date DATETIME NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    order_id NVARCHAR(14) NOT NULL,
    coupon_id INT NOT NULL,
    FOREIGN KEY (coupon_id)
        REFERENCES coupons (coupon_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
