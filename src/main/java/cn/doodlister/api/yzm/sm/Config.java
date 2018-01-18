package cn.doodlister.api.yzm.sm;

public class Config {
	private static String baseUrl = "http://api.eobzz.com/httpApi.do?action=";
	public static String loginUrl = baseUrl + "loginIn&";
	public static String getUserInfos = baseUrl + "getUserInfos&";
	public static String getMobilenum = baseUrl + "getMobilenum&";
	public static String addIgnoreList = baseUrl + "addIgnoreList&";
	public static String getVcodeAndReleaseMobile = baseUrl + "getVcodeAndReleaseMobile&";
	public static String getVcodeAndHoldMobilenum = baseUrl + "getVcodeAndHoldMobilenum&";
}
