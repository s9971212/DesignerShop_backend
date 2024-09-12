USE designershop;

DROP TABLE IF EXISTS product_images;

CREATE TABLE product_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    image TEXT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES products (product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
