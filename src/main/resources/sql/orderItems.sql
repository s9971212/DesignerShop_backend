USE designershop;

DROP TABLE IF EXISTS order_items;

CREATE TABLE order_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    price_at_purchase DECIMAL(10 , 2 ) NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    product_id INT NOT NULL,
    order_id NVARCHAR(14) NOT NULL,
    UNIQUE (product_id , order_id),
    FOREIGN KEY (order_id)
        REFERENCES orders (order_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
