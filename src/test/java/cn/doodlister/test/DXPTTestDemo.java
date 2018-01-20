package cn.doodlister.test;


import cn.doodlister.api.yzm.sm.MyControl;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.api.yzm.sm.UserService;

public class DXPTTestDemo {
	private static UserInfo userInfo = new UserInfo();

	/**
	 * 主入口，在这里调用登陆，获取手机号，验证码等方法
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Boolean loginBoolean = login("doodlister", "szy19960530");
		if (loginBoolean) {
			getUserInfos();
		}
		else {
			System.out.println("登陆失败");
		}
	}

	/**
	 * 登 陆
	 * 
	 * @param 用户名
	 * @param 密码
	 * @return true or false
	 */
	public static Boolean login(String userName, String password) throws Exception {
		userInfo.setUid(userName);
		userInfo.setPwd(password);
		String result = UserService.login(userInfo);
		if (MyControl.isNull(result)) {
			System.out.println("login fail  result = " +result);
			return false;
		}
		if (result.toLowerCase().startsWith(userInfo.getUid().toLowerCase())) {
			String[] strings = result.split("\\|");
			userInfo.setToken(strings[1]);
			return true;
		}
		else{
			System.out.println("login fail  result = " +result);
			return false;
		}
	}

	/**
	 * 获取用户个人信息
	 * 
	 * @return 成功返回：用户名;积分;余额;可同时获取号码数 失败请参考文档
	 */
	public static String getUserInfos() {
		if (userInfo.isLogin()) {
			String result = UserService.getUserInfo(userInfo.getUid(), userInfo.getToken());
			System.out.println(result);
			return result;
		}
		return "";
	}

	/**
	 * 获取手机号码
	 * 
	 * @param 项目ID
	 * @return 成功返回：手机号码|token 失败请参考文档
	 */
	public static String getMobileNum(String pid) {
		if (userInfo.isLogin()) {
			String result = UserService.getMobileNum(pid, userInfo.getUid(), userInfo.getToken());
			System.out.println(result);
			return result;
		}
		return "";
	}

	/**
	 * 
	 * @param 用getMobilenum方法获取到的手机号
	 * @param 项目ID
	 * @return 成功返回：加黑成功的号码数量 失败请参考文档
	 */
	public static String addIgnore(String mobileNum, String pid) {
		if (userInfo.isLogin()) {
			String result = UserService.addIgnore(mobileNum, userInfo.getUid(), userInfo.getToken(), pid);
			System.out.println(result);
			return result;
		}
		return "";
	}

	/**
	 * 获取验证码并不再使用本号
	 * 
	 * @param 用getMobilenum方法获取到的手机号
	 * @param 软件开发者用户名(可选, 可得10%的消费分成)
	 * @return 成功返回：手机号码|验证码短信 失败请参考文档
	 */
	public static String getVcodeAndReleaseMobile(String mobileNum, String author_uid) {
		if (userInfo.isLogin()) {
			String result = UserService.getVcodeAndReleaseMobile(userInfo.getUid(), userInfo.getToken(), mobileNum, author_uid);
			System.out.println(result);
			return result;
		}
		return "";
	}

	/**
	 * 
	 * @param 用getMobilenum方法获取到的手机号
	 * @param 软件开发者用户名(可选, 可得10%的消费分成)
	 * @param 下个要接收的项目ID
	 * @return 成功返回：发送号码|验证码|token 失败请参考文档
	 */
	public static String getVcodeAndHoldMobilenum(String mobileNum, String author_uid, String nextId) {
		if (userInfo.isLogin()) {
			String result = UserService.getVcodeAndHoldMobilenum(userInfo.getUid(), userInfo.getToken(), mobileNum, nextId, author_uid);
			System.out.println(result);
			return result;
		}
		return "";
	}

}
