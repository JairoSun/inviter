package cn.doodlister.api.yzm.sm;


import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.utils.HttpClientUtil;

public class UserService{

	public static String login(UserInfo userInfo) throws Exception {
		if (!MyControl.isNull(userInfo.getUid()) && !MyControl.isNull(userInfo.getPwd())) {
			try {
				String param = String.format("uid=%s&pwd=%s", userInfo.getUid().trim(), userInfo.getPwd());
				//System.out.println(param);
				return HttpHelper.getHtml(Config.loginUrl, param);
			} catch (Exception ex) {

				throw ex;
			}
		} else {
			return "";
		}
	}

	/**
	 * 获取用户个人信息
	 * @param userName
	 * @param token
	 * @return
	 */
	public static String getUserInfo(String userName, String token) {
		try {
			String para = String.format("uid=%s&token=%s", userName, token);
			String url = Config.getUserInfos + para;
			System.out.println(url);
			return HttpHelper.getHtml(url);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 获取手机号码
	 * @param pid
	 * @param uid
	 * @param token
	 * @return
	 */
	public static String getMobileNum(String pid, String uid, String token) {
		try {
			String para = String.format("pid=%s&uid=%s&token=%s&mobile=&size=%s", pid, uid, token, 1);
			String url = Config.getMobilenum + para;
			return HttpHelper.getHtml(url);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 加黑无用号码
	 * @param mobileNum
	 * @param uid
	 * @param token
	 * @param pid
	 * @return
	 */
	public static String addIgnore(String mobileNum, String uid, String token, String pid) {
		try {
			String para = String.format("uid=%s&token=%s&mobiles=%s&pid=%s", uid, token, mobileNum, pid);
			String url = Config.addIgnoreList + para;
			return HttpHelper.getHtml(url);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 获取验证码并不再使用本号
	 * @param uid
	 * @param token
	 * @param mobileNum
	 * @param author_uid
	 * @return
	 */
	public static String getVcodeAndReleaseMobile(String uid, String token, String mobileNum, String author_uid) {
		try {
			String para = String.format("uid=%s&token=%s&mobile=%s&author_uid=%s", uid, token, mobileNum, author_uid);
			String url = Config.getVcodeAndReleaseMobile + para;
			//System.out.println(url);

			return 	HttpClientUtil.doGet(url,60000);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 获取验证码并继续使用本号
	 * @param uid
	 * @param token
	 * @param mobileNum
	 * @param nextId
	 * @param author_uid
	 * @return
	 */
	public static String getVcodeAndHoldMobilenum(String uid, String token, String mobileNum, String nextId, String author_uid) {
		try {
			String para = String.format("uid=%s&token=%s&mobile=%s&next_pid=%s&author_uid=%s", uid, token, mobileNum, nextId, author_uid);
			String url = Config.getVcodeAndHoldMobilenum + para;
			return HttpHelper.getHtml(url);
		} catch (Exception ex) {
			return "";
		}
	}
}
