USE designershop;

DROP TABLE IF EXISTS coupons_user_profile;

CREATE TABLE coupons_user_profile (
    coupon_id INT NOT NULL,
    user_id NVARCHAR(12) NOT NULL,
    PRIMARY KEY (coupon_id , user_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;
