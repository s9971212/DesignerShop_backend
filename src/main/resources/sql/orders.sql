USE designershop;

DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    order_id NVARCHAR(14) PRIMARY KEY,
    total_price DECIMAL(10 , 2 ) NOT NULL,
    created_date DATETIME NOT NULL,
    full_address NVARCHAR(1024) NOT NULL,
    contact_phone NVARCHAR(20) NOT NULL,
    contact_name NVARCHAR(100) NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    delivery_id INT NOT NULL,
    FOREIGN KEY (delivery_id)
        REFERENCES order_deliveries (delivery_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
