package cn.doodlister.api.yzm.sm;

public class MyControl {
	public static Boolean isNull(String s) {
		if (s == null || "".equals(s) || s.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
