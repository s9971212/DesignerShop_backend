USE designershop;

DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
    user_id NVARCHAR(10) PRIMARY KEY,
    account NVARCHAR(30) NOT NULL,
    password NVARCHAR(100) NOT NULL,
    email NVARCHAR(30) NOT NULL,
    phone_no NVARCHAR(20) NOT NULL,
    user_name NVARCHAR(30) NULL,
    gender NVARCHAR(10) NULL,
    birthday DATETIME NULL,
    id_card_no NVARCHAR(10) NULL,
    home_no NVARCHAR(20) NULL,
    user_image TEXT NULL,
    register_date DATETIME NOT NULL,
    pwd_changed_date DATETIME NULL,
    pwd_expire_date DATETIME NOT NULL,
    sign_on_status NVARCHAR(1) NULL,
    sign_on_computer NVARCHAR(32) NULL,
    pwd_error_count INT DEFAULT 0 NOT NULL,
    updated_user NVARCHAR(10) NULL,
    updated_date DATETIME NULL,
    is_lock NVARCHAR(1) NULL,
    lock_date DATETIME NULL,
    unlock_date DATETIME NULL,
    sign_on_token NVARCHAR(1024) NULL,
    pwd_forgot_token NVARCHAR(1024) NULL,
    pwd_forgot_token_expire_date DATETIME NULL,
    google_id NVARCHAR(10) NULL,
    facebook_id NVARCHAR(10) NULL,
    UNIQUE (account),
    UNIQUE (email),
    UNIQUE (phone_no)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO user_profile (user_id, account, password, email, phone_no, register_date, pwd_changed_date, pwd_expire_date,  pwd_error_count)  VALUES
('0240700001', 'test1', '$2a$10$ZY8YXrUxnaucChrLaqt4SubBP29qfubq6/w3OdSoWXO56sUEfu/VC', 'test1@example.com', '0912312312', NOW(), NOW(), NOW(), 0),
('0240700002', 'test2', '$2a$10$ZY8YXrUxnaucChrLaqt4SubBP29qfubq6/w3OdSoWXO56sUEfu/VC', 'test2@example.com', '0945645645', NOW(), NOW(), NOW(), 0),
('0240700003', 'test3', '$2a$10$ZY8YXrUxnaucChrLaqt4SubBP29qfubq6/w3OdSoWXO56sUEfu/VC', 'test3@example.com', '0978978978', NOW(), NOW(), NOW(), 0);
