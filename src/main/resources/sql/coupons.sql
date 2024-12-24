USE designershop;

DROP TABLE IF EXISTS coupons;

CREATE TABLE coupons (
    coupon_id INT AUTO_INCREMENT PRIMARY KEY,
    code NVARCHAR(50) NOT NULL,
    description TEXT NULL,
    image TEXT NULL,
    discount_type ENUM('PERCENTAGE', 'FIXED') NOT NULL,
    discount_value DECIMAL(10 , 2 ) NOT NULL,
    minimum_order_price DECIMAL(10 , 2 ) NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    issuance_limit INT NULL,
    usage_limit INT NULL,
    created_date DATETIME NOT NULL,
    updated_user NVARCHAR(12) NULL,
    updated_date DATETIME NULL,
    is_active TINYINT(1) DEFAULT 0 NOT NULL,
    UNIQUE (code)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
