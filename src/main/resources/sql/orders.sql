USE designershop;

DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    order_id NVARCHAR(14) PRIMARY KEY,
    total_price DECIMAL(10 , 2 ) NOT NULL,
    created_date DATETIME NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    UNIQUE (user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
