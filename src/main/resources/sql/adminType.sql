USE designershop;

DROP TABLE IF EXISTS admin_type;

CREATE TABLE admin_type (
    admin_type_id NVARCHAR(2) PRIMARY KEY,
    admin_type_name NVARCHAR(10) NOT NULL,
    UNIQUE (admin_type_name)
);

INSERT INTO admin_type (admin_type_id, admin_type_name) VALUES
('A1', '新手管理員'),
('A2', '初階管理員'),
('A9', '神級管理員');
