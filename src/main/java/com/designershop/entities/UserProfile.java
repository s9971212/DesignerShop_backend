package com.designershop.entities;

import java.sql.Timestamp;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

	@Id
	@Column(name = "user_id", nullable = false, length = 10)
	private String userId;

	@Column(name = "user_type", nullable = false, length = 2)
	private String userType;

	@Column(name = "seller_type", length = 2)
	private String sellerType;

	@Column(name = "designer_type", length = 2)
	private String designerType;

	@Column(name = "admin_type", length = 2)
	private String adminType;

	@Column(name = "account", nullable = false, length = 30)
	private String account;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "email", nullable = false, length = 30)
	private String email;

	@Column(name = "phone_no", nullable = false, length = 20)
	private String phoneNo;

	@Column(name = "user_name", length = 30)
	private String userName;

	@Column(name = "gender", length = 10)
	private String gender;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "birthday")
	private Timestamp birthday;

	@Column(name = "id_card_no", length = 10)
	private String idCardNo;

	@Column(name = "home_no", length = 20)
	private String homeNo;

	@Column(name = "user_image", length = 10000)
	private String userImage;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "register_date", nullable = false)
	private Timestamp registerDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "pwd_changed_date")
	private Timestamp pwdChangedDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "pwd_expire_date", nullable = false)
	private Timestamp pwdExpireDate;

	@Column(name = "sign_on_status", length = 1)
	private String signOnStatus;

	@Column(name = "sign_on_computer", length = 32)
	private String signOnComputer;

	@Column(name = "pwd_error_count", nullable = false)
	private int pwdErrorCount = 0;

	@Column(name = "modify_user", length = 10)
	private String modifyUser;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modify_date")
	private Timestamp modifyDate;

	@Column(name = "is_lock", length = 1)
	private String isLock;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "lock_date")
	private Timestamp lockDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "unlock_date")
	private Timestamp unlockDate;

	@Column(name = "sign_on_token", length = 1024)
	private String signOnToken;

	@Column(name = "pwd_forgot_token", length = 1024)
	private String pwdForgotToken;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "pwd_forgot_token_expire_date")
	private Timestamp pwdForgotTokenExpireDate;

	@Column(name = "google_id", length = 10)
	private String googleId;

	@Column(name = "facebook_id", length = 10)
	private String facebookId;

	@Override
	public int hashCode() {
		return Objects.hash(userId, account, email, phoneNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(userId, other.userId) && Objects.equals(account, other.account)
				&& Objects.equals(email, other.email) && Objects.equals(phoneNo, other.phoneNo);
	}

	@PrePersist
	@PreUpdate
	public void handleEmptyStrings() {
		if (StringUtils.isBlank(this.userName)) {
			this.userName = null;
		}
		if (StringUtils.isBlank(this.gender)) {
			this.gender = null;
		}
		if (Objects.isNull(this.birthday)) {
			this.birthday = null;
		}
		if (StringUtils.isBlank(this.idCardNo)) {
			this.idCardNo = null;
		}
		if (StringUtils.isBlank(this.homeNo)) {
			this.homeNo = null;
		}
		if (StringUtils.isBlank(this.userImage)) {
			this.userImage = null;
		}
	}
}
