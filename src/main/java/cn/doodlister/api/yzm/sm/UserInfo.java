package cn.doodlister.api.yzm.sm;

public class UserInfo {
	private String uid;
	private String pwd;
	private String token;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserInfo() {
		uid = null;
		pwd = null;
		token = null;
	}

	public void clear() {
		uid = null;
		pwd = null;
		token = null;
	}

	public Boolean isLogin() {
		if (MyControl.isNull(uid) || MyControl.isNull(pwd) || MyControl.isNull(token)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [uid=");
		builder.append(uid);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}
}
