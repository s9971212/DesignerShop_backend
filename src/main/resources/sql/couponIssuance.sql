USE designershop;

DROP TABLE IF EXISTS coupon_issuance;

CREATE TABLE coupon_issuance (
    issuance_id INT AUTO_INCREMENT PRIMARY KEY,
    is_used TINYINT(1) DEFAULT 0 NOT NULL,
    used_date DATETIME NULL,
    user_id NVARCHAR(12) NOT NULL,
    coupon_id INT NOT NULL,
    FOREIGN KEY (coupon_id)
        REFERENCES coupons (coupon_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
