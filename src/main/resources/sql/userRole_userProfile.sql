USE designershop;

DROP TABLE IF EXISTS user_role_profile;

CREATE TABLE user_role_profile (
    role_id NVARCHAR(2) NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    PRIMARY KEY (role_id , user_id),
    FOREIGN KEY (role_id)
        REFERENCES user_role (role_id),
    FOREIGN KEY (user_id)
        REFERENCES user_profile (user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO user_role_profile (role_id, user_id) VALUES
('B1','DU0240700001'),
('B1','DU0240700002'),
('B1','DU0240700003');
