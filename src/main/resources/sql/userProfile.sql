USE designershop;

DROP TABLE IF EXISTS userprofile;

CREATE TABLE userprofile (
    userid NVARCHAR(10) NOT NULL,
    usertype NVARCHAR(2) NOT NULL,
	account NVARCHAR(30) NOT NULL,
    password NVARCHAR(100) NOT NULL,
	email NVARCHAR(30) NOT NULL,
	phoneno NVARCHAR(20) NOT NULL,
	username NVARCHAR(30) NULL,
    gender NVARCHAR(10) NULL,
    birthday DATETIME NULL,
    idcardno NVARCHAR(10) NULL,
    homeno NVARCHAR(20) NULL,
    userphoto NVARCHAR(10000) NULL,
    registerdate DATETIME NOT NULL,
    pwdchangeddate DATETIME NULL,
    pwdexpiredate DATETIME NOT NULL,
    signonstatus NVARCHAR(1) NULL,
    signoncomputer NVARCHAR(32) NULL,
    pwderrorcount INT DEFAULT 0 NOT NULL,
    modifyuser NVARCHAR(10) NULL,
    modifydate DATETIME NULL,
    islock NVARCHAR(1) NULL,
    lockdate DATETIME NULL,
    unlockdate DATETIME NULL,
    hash NVARCHAR(1024) NULL,
    refreshhash NVARCHAR(1024) NULL,
    googleid NVARCHAR(10) NULL,
    facebookid NVARCHAR(10) NULL,
    PRIMARY KEY (userId),
    UNIQUE (account),
    UNIQUE (email),
    UNIQUE (phoneNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入假資料到userProfile表中
INSERT INTO userprofile (userid, usertype, account, password, email, phoneno, username, gender, birthday, idcardno, homeno, userphoto, registerdate, pwdchangeddate, pwdexpiredate, signonstatus, signoncomputer, pwderrorcount, modifyuser, modifydate, islock, lockdate, unlockdate, hash, refreshhash, googleid, facebookid)
SELECT 
    CONCAT('T', LPAD(FLOOR(RAND()*1000), 2, '0')),
    CASE FLOOR(RAND()*3)
        WHEN 0 THEN 'A'
        WHEN 1 THEN 'B'
        ELSE 'C'
    END,
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

