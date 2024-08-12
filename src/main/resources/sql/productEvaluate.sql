USE designershop;

DROP TABLE IF EXISTS product_evaluate;

CREATE TABLE product_evaluate (
    evaluate_id INT AUTO_INCREMENT PRIMARY KEY,
    stars DECIMAL(3 , 2 ) NOT NULL,
    evaluate TEXT NOT NULL,
    product_id INT NOT NULL,
    user_id NVARCHAR(10) NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES product (product_id),
    FOREIGN KEY (user_id)
        REFERENCES user_profile (user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
