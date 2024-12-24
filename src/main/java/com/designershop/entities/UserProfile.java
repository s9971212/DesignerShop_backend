package com.designershop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

    /**
     * 使用者ID
     */
    @Id
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 使用者帳號
     */
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    /**
     * 使用者密碼
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
     * 使用者電子郵件地址
     */
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    /**
     * 使用者手機號碼
     */
    @Column(name = "phone_no", nullable = false, length = 20)
    private String phoneNo;

    /**
     * 使用者名稱
     */
    @Column(name = "name", length = 30)
    private String name;

    /**
     * 使用者性別
     */
    @Column(name = "gender", length = 10)
    private String gender;

    /**
     * 使用者生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "birthday")
    private LocalDateTime birthday;

    /**
     * 使用者身份證號碼
     */
    @Column(name = "id_card_no", length = 10)
    private String idCardNo;

    /**
     * 使用者家庭電話號碼
     */
    @Column(name = "home_no", length = 20)
    private String homeNo;

    /**
     * 使用者圖片
     */
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    /**
     * 使用者註冊時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;

    /**
     * 上次修改使用者密碼的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "pwd_changed_date")
    private LocalDateTime pwdChangedDate;

    /**
     * 使用者密碼過期的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "pwd_expire_date", nullable = false)
    private LocalDateTime pwdExpireDate;

    /**
     * 使用者登錄狀態
     */
    @Column(name = "sign_on_status", length = 1)
    private String signOnStatus;

    /**
     * 使用者當前登錄的電腦IP
     */
    @Column(name = "sign_on_computer", length = 32)
    private String signOnComputer;

    /**
     * 密碼錯誤次數
     */
    @Column(name = "pwd_error_count", nullable = false)
    private int pwdErrorCount = 0;

    /**
     * 上次更新該使用者資料的使用者ID
     */
    @Column(name = "updated_user", length = 12)
    private String updatedUser;

    /**
     * 上次更新該使用者資料的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * 使用者是否被鎖定
     */
    @Column(name = "is_lock", nullable = false)
    private boolean isLock = false;

    /**
     * 使用者帳戶被鎖定的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "lock_date")
    private LocalDateTime lockDate;

    /**
     * 使用者帳戶解鎖的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "unlock_date")
    private LocalDateTime unlockDate;

    /**
     * 使用者登錄過程中的驗證令牌
     */
    @Column(name = "sign_on_token", length = 1024)
    private String signOnToken;

    /**
     * 使用者的密碼重設令牌
     */
    @Column(name = "pwd_forgot_token", length = 1024)
    private String pwdForgotToken;

    /**
     * 密碼重設令牌的過期時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "pwd_forgot_token_expire_date")
    private LocalDateTime pwdForgotTokenExpireDate;

    /**
     * 使用者是否已被刪除
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * 使用者的Google ID
     */
    @Column(name = "google_id", length = 10)
    private String googleId;

    /**
     * 使用者的Facebook ID
     */
    @Column(name = "facebook_id", length = 10)
    private String facebookId;

    /**
     * 使用者資料與權限的多對多關聯。每個使用者資料可以對應多個權限，每個權限也可以有多個使用者資料
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles;

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
        return Objects.equals(userId, other.userId) && Objects.equals(account, other.account) && Objects.equals(email, other.email)
                && Objects.equals(phoneNo, other.phoneNo);
    }

    @PrePersist
    @PreUpdate
    public void handleEmptyStrings() {
        if (StringUtils.isBlank(this.name)) {
            this.name = null;
        }
        if (StringUtils.isBlank(this.gender)) {
            this.gender = null;
        }
        if (StringUtils.isBlank(this.idCardNo)) {
            this.idCardNo = null;
        }
        if (StringUtils.isBlank(this.homeNo)) {
            this.homeNo = null;
        }
        if (StringUtils.isBlank(this.image)) {
            this.image = null;
        }
    }
}
