package cn.doodlister.api.app.cddh.entity;

public class CDDHUser {
    /**
     *
     "created": null,
     "updated": null,
     "username": "手机用户21970vXk",
     "userId": 17549260,
     "phone": "17336321970",
     "avatarUrl": "https://img4.jiecaojingxuan.com/st/chongding/defaultAvatar.png",
     "balance": 0,
     "income": 0,
     "shareCode": "17649270",
     "inviteCode": null,
     "lifePoints": 0,
     "wxUnionId": null,
     "wxAppOpenId": "",
     "sessionToken": "1.17549260.1022164.bZI.7bcbe46bdc4806cd3cde92e819f386f5",
     "lastLoginTs": "2018-01-12T19:56:04.88",
     "pushAlias": "Tdrwqd125sCvBjdK",
     "status": 0
     */
    private String created;
    private String updated;
    private String username;
    private Long userId;
    private String  phone;
    private String avatarUrl;
    private int balance;
    private int income;
    private String shareCode;
    private String inviteCode;
    private String sessionToken;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "CDDHUser{" +
                "created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", balance=" + balance +
                ", income=" + income +
                ", shareCode='" + shareCode + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
