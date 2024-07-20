USE designershop;

DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
    user_id NVARCHAR(10) NOT NULL,
    user_type NVARCHAR(2) NOT NULL,
    seller_type NVARCHAR(2) NULL,
    designer_type NVARCHAR(2) NULL,
    admin_type NVARCHAR(2) NULL,
	account NVARCHAR(30) NOT NULL,
    password NVARCHAR(100) NOT NULL,
	email NVARCHAR(30) NOT NULL,
	phone_no NVARCHAR(20) NOT NULL,
	user_name NVARCHAR(30) NULL,
    gender NVARCHAR(10) NULL,
    birthday DATETIME NULL,
    id_card_no NVARCHAR(10) NULL,
    home_no NVARCHAR(20) NULL,
    user_photo NVARCHAR(10000) NULL,
    register_date DATETIME NOT NULL,
    pwd_changed_date DATETIME NULL,
    pwd_expire_date DATETIME NOT NULL,
    sign_on_status NVARCHAR(1) NULL,
    sign_on_computer NVARCHAR(32) NULL,
    pwd_error_count INT DEFAULT 0 NOT NULL,
    modify_user NVARCHAR(10) NULL,
    modify_date DATETIME NULL,
    is_lock NVARCHAR(1) NULL,
    lock_date DATETIME NULL,
    unlock_date DATETIME NULL,
    hash NVARCHAR(1024) NULL,
    refresh_hash NVARCHAR(1024) NULL,
    google_id NVARCHAR(10) NULL,
    facebook_id NVARCHAR(10) NULL,
    PRIMARY KEY (user_id),
    UNIQUE (account),
    UNIQUE (email),
    UNIQUE (phone_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入假資料到userProfile表中
INSERT INTO user_profile (user_id, user_type, seller_type, designer_type, admin_type, account, password, email, phone_no, user_name, gender, birthday, id_card_no, home_no, user_photo, register_date, pwd_changed_date, pwd_expire_date, sign_on_status, sign_on_computer, pwd_error_count, modify_user, modify_date, is_lock, lock_date, unlock_date, hash, refresh_hash, google_id, facebook_id)
SELECT 
    CONCAT('T', LPAD(FLOOR(RAND()*1000), 2, '0')),
    CASE FLOOR(RAND()*3)
        WHEN 0 THEN 'A'
        WHEN 1 THEN 'B'
        ELSE 'C'
    END,
    NULL,
    NULL,
    NULL,
    CONCAT('user_', LPAD(FLOOR(RAND()*1000), 3, '0')),
    MD5(RAND()),
    CONCAT('user', LPAD(FLOOR(RAND()*1000), 3, '0'), '@example.com'),
    CONCAT('+1', LPAD(FLOOR(RAND()*1000000000), 10, '0')),
    IF(RAND() < 0.5, CONCAT('User', LPAD(FLOOR(RAND()*1000), 3, '0')), NULL),
    IF(RAND() < 0.5, 'Male', 'Female'),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND()*50) YEAR),
    LPAD(FLOOR(RAND()*1000000000), 10, '0'),
    CONCAT(FLOOR(RAND()*1000), ' Fake St'),
    NULL,
    NOW() - INTERVAL FLOOR(RAND()*1095) DAY,
    IF(RAND() < 0.5, NOW() - INTERVAL FLOOR(RAND()*180) DAY, NULL),
    NOW() + INTERVAL FLOOR(RAND()*365) DAY,
    IF(RAND() < 0.8, 'Y', 'N'),
    INET_NTOA(FLOOR(RAND()*4294967295)),
    FLOOR(RAND()*5),
    CONCAT('T', LPAD(FLOOR(RAND()*1000), 3, '0')),
    IF(RAND() < 0.2, NOW() - INTERVAL FLOOR(RAND()*365) DAY, NULL),
    IF(RAND() < 0.2, 'Y', 'N'),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND()*50) YEAR),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND()*50) YEAR),
    LPAD(FLOOR(RAND()*1000000000), 10, '0'),
    LPAD(FLOOR(RAND()*1000000000), 10, '0'),
    CONCAT('T', LPAD(FLOOR(RAND()*1000), 3, '0')),
    CONCAT('T', LPAD(FLOOR(RAND()*1000), 3, '0'))
FROM
    information_schema.tables AS t1,
    information_schema.tables AS t2
LIMIT 3;

