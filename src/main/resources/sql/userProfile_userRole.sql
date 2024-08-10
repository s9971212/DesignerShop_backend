USE designershop;

DROP TABLE IF EXISTS user_profile_role;

CREATE TABLE user_profile_role (
    user_id NVARCHAR(10) NOT NULL,
    role_id NVARCHAR(2) NOT NULL,
    PRIMARY KEY (user_id , role_id),
    FOREIGN KEY (user_id)
        REFERENCES user_profile (user_id),
    FOREIGN KEY (role_id)
        REFERENCES user_role (role_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO user_profile_role (user_id, role_id) VALUES
('0240700001', 'B1'),
('0240700002', 'B1'),
('0240700003', 'B1');
