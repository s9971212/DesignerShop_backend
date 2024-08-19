USE designershop;

DROP TABLE IF EXISTS product_brand;

CREATE TABLE product_brand (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    brand NVARCHAR(50) NOT NULL,
    UNIQUE (brand)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO product_brand (brand) VALUES
('SAMSUNG 三星'),
('ASUS 華碩');
