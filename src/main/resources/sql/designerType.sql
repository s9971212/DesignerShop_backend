USE designershop;

DROP TABLE IF EXISTS designer_type;

CREATE TABLE designer_type (
    designer_type_id NVARCHAR(2) PRIMARY KEY,
    designer_type_name NVARCHAR(10) NOT NULL,
    UNIQUE (designer_type_name)
);

INSERT INTO designer_type (designer_type_id, designer_type_name) VALUES
('D1', '新手設計師'),
('D2', '初階設計師'),
('D9', '神級設計師');
