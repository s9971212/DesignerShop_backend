USE designershop;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name NVARCHAR(100) NOT NULL,
    product_description TEXT NULL,
    price DECIMAL(10 , 2 ) NOT NULL,
    original_price DECIMAL(10 , 2 ) NOT NULL,
    stock_quantity INT DEFAULT 0 NOT NULL,
    sold_quantity INT DEFAULT 0 NOT NULL,
    likes INT DEFAULT 0 NOT NULL,
    created_date DATETIME NOT NULL,
    updated_user NVARCHAR(12) NULL,
    updated_date DATETIME NULL,
    is_deleted TINYINT(1) DEFAULT 0 NOT NULL,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES product_category (category_id),
    FOREIGN KEY (brand_id)
        REFERENCES product_brand (brand_id),
    FOREIGN KEY (user_id)
        REFERENCES user_profile (user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
