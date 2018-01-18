package cn.doodlister.api.yzm;


import cn.doodlister.api.yzm.sm.UserInfo;


/**
 * 验证码抽象接口
 */
public interface CodeService {
    UserInfo login(UserInfo userInfo)  ;

    /**
     * 获取用户个人信息
     * @param userName
     * @param token
     * @return
     */
    String getUserInfo(UserInfo user);
    /**
     * 获取手机号码
     * @param pid
     * @param uid
     * @param token
     * @return
     */
    String getMobileNum(String itemid, UserInfo user) ;

    Boolean releaseAll(UserInfo user) ;
    /**
     * 加黑无用号码
     * @param mobileNum
     * @param uid
     * @param token
     * @param pid
     * @return
     */
    String addIgnore(String mobileNum, String uid, String token, String pid) ;

    /**
     * 获取验证码并不再使用本号
     * @param uid
     * @param token
     * @param mobileNum
     * @param author_uid
     * @return
     */
    String getVcodeAndReleaseMobile(UserInfo user,String itemId,String phoneNum) ;



    /**
     * 获取验证码并继续使用本号
     * @param uid
     * @param token
     * @param mobileNum
     * @param nextId
     * @param author_uid
     * @return
     */
    String getVcodeAndHoldMobilenum(String uid, String token, String mobileNum, String nextId, String author_uid);
}
