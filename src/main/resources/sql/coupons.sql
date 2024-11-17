USE designershop;

DROP TABLE IF EXISTS coupons;

CREATE TABLE coupons (
    coupon_id INT AUTO_INCREMENT PRIMARY KEY,
    code NVARCHAR(50) NOT NULL,
    discount_type ENUM('percentage', 'fixed') NOT NULL,
    discount_value DECIMAL(10 , 2 ) NOT NULL,
    minimum_order_price DECIMAL(10 , 2 ) NULL,
    usage_limit INT NULL,
    description TEXT NULL,
    image TEXT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created_date DATETIME NOT NULL,
    updated_user NVARCHAR(12) NULL,
    updated_date DATETIME NULL,
    is_active TINYINT(1) DEFAULT 0 NOT NULL,
    UNIQUE (code)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
