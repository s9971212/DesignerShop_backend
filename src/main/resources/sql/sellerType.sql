USE designershop;

DROP TABLE IF EXISTS seller_type;

CREATE TABLE seller_type (
    seller_type_id NVARCHAR(2) PRIMARY KEY,
    seller_type_name NVARCHAR(10) NOT NULL,
    UNIQUE (seller_type_name)
);

INSERT INTO seller_type (seller_type_id, seller_type_name) VALUES
('S1', '新手賣家'),
('S2', '初階賣家'),
('S9', '神級賣家');
