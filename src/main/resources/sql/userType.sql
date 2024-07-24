USE designershop;

DROP TABLE IF EXISTS user_type;

CREATE TABLE user_type (
    user_type_id NVARCHAR(2) PRIMARY KEY,
    user_type_name NVARCHAR(10) NOT NULL,
    UNIQUE (user_type_name)
);

INSERT INTO user_type (user_type_id, user_type_name) VALUES
('B1', '新手買家'),
('B2', '初階買家'),
('B9', '神級買家');
