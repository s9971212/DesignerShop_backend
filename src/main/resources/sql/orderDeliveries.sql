USE designershop;

DROP TABLE IF EXISTS order_deliveries;

CREATE TABLE order_deliveries (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    full_address NVARCHAR(1024) NOT NULL,
    address NVARCHAR(256) NOT NULL,
    district NVARCHAR(100) NULL,
    city NVARCHAR(100) NULL,
    state NVARCHAR(100) NULL,
    postal_code NVARCHAR(20) NULL,
    nation NVARCHAR(100) NOT NULL,
    contact_phone NVARCHAR(20) NOT NULL,
    contact_name NVARCHAR(100) NOT NULL,
    is_default TINYINT(1) DEFAULT 0 NOT NULL,
    user_id NVARCHAR(12) NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
