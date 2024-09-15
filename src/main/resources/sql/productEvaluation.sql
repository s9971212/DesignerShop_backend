USE designershop;

DROP TABLE IF EXISTS product_evaluation;

CREATE TABLE product_evaluation (
    evaluation_id INT AUTO_INCREMENT PRIMARY KEY,
    stars DECIMAL(3 , 2 ) NOT NULL,
    evaluation TEXT NULL,
    created_date DATETIME NOT NULL,
    updated_user NVARCHAR(12) NULL,
    updated_date DATETIME NULL,
    user_id NVARCHAR(12) NOT NULL,
    product_id INT NOT NULL,
    UNIQUE (user_id , product_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
