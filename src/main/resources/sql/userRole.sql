USE designershop;

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
    role_id NVARCHAR(2) PRIMARY KEY,
    role_name NVARCHAR(10) NOT NULL,
    role_category NVARCHAR(10) NOT NULL,
    UNIQUE (role_name)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO user_role (role_id, role_name, role_category) VALUES
('B1', '新手買家', 'buyer'),
('B2', '初階買家', 'buyer'),
('B9', '神級買家', 'buyer'),
('S1', '新手賣家', 'seller'),
('S2', '初階賣家', 'seller'),
('S9', '神級賣家', 'seller'),
('D1', '新手設計師', 'designer'),
('D2', '初階設計師', 'designer'),
('D9', '神級設計師', 'designer'),
('A1', '新手管理員', 'admin'),
('A2', '初階管理員', 'admin'),
('A9', '神級管理員', 'admin');
