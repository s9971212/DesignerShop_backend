USE designershop;

DROP TABLE IF EXISTS cart_item;

CREATE TABLE cart_item (
    items_id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT DEFAULT 1 NOT NULL,
    added_date DATETIME NOT NULL,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    UNIQUE (cart_id , product_id),
    FOREIGN KEY (cart_id)
        REFERENCES cart (cart_id),
    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
