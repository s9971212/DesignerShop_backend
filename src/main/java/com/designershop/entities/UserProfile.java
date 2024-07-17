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
@Table(name = "userprofile")
public class UserProfile {

	@Id
	@Column(name = "userid", nullable = false, length = 10)
	private String userId;

	@Column(name = "usertype", nullable = false, length = 2)
	private String userType;

	@Column(name = "account", nullable = false, length = 30)
	private String account;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "email", nullable = false, length = 30)
	private String email;

	@Column(name = "phoneno", nullable = false, length = 20)
	private String phoneNo;

	@Column(name = "username", length = 30)
	private String userName;

	@Column(name = "gender", length = 10)
	private String gender;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "birthday")
	private Timestamp birthday;

	@Column(name = "idcardno", length = 10)
	private String idCardNo;

	@Column(name = "homeno", length = 20)
	private String homeNo;

	@Column(name = "userphoto", length = 10000)
	private String userPhoto;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "registerdate", nullable = false)
	private Timestamp registerDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "pwdchangeddate")
	private Timestamp pwdChangedDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "pwdexpiredate", nullable = false)
	private Timestamp pwdExpireDate;

	@Column(name = "signonstatus", length = 1)
	private String signOnStatus;

	@Column(name = "signoncomputer", length = 32)
	private String signOnComputer;

	@Column(name = "pwderrorcount", nullable = false)
	private int pwdErrorCount = 0;

	@Column(name = "modifyuser", length = 10)
	private String modifyUser;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modifydate")
	private Timestamp modifyDate;

	@Column(name = "islock", length = 1)
	private String isLock;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "lockdate")
	private Timestamp lockDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "unlockdate")
	private Timestamp unlockDate;

	@Column(name = "hash", length = 1024)
	private String hash;

	@Column(name = "refreshhash", length = 1024)
	private String refreshHash;

	@Column(name = "googleid", length = 10)
	private String googleId;

	@Column(name = "facebookid", length = 10)
	private String facebookId;

	public UserProfile(String userId, String userType) {
		this.userId = userId;
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
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
		return Objects.equals(userId, other.userId);
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
		if (StringUtils.isBlank(this.userPhoto)) {
			this.userPhoto = null;
		}
	}
}
