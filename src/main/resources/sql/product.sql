USE designershop;

DROP TABLE IF EXISTS product;

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name NVARCHAR(100) NOT NULL,
    product_description TEXT NULL,
    price DECIMAL(10 , 2 ) NOT NULL,
    stock_quantity INT DEFAULT 0 NOT NULL,
    sold_quantity INT DEFAULT 0 NOT NULL,
    likes INT DEFAULT 0 NOT NULL,
    created_date DATETIME NOT NULL,
    updated_date DATETIME NOT NULL,
    user_id NVARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user_profile (user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
