package cn.doodlister.api.yzm;


/**
 * 验证码抽象接口
 */
public interface CodeService {
    UserInfo login(UserInfo userInfo)  ;
    /**
     * 获取用户个人信息
     * @param user
     * @return
     */
    String getUserInfo(UserInfo user);

    /**
     * 获取手机号码
     * @param itemid
     * @param user
     * @return
     */
    String getMobileNum(String itemid, UserInfo user) ;

    /**
     * 释放所有号码
     * @param user
     * @return
     */
    Boolean releaseAll(UserInfo user) ;

    /**
     * 加黑无用号码
     * @param mobileNum
     * @param itemId
     * @param info
     * @return
     */
    Boolean addIgnore(String mobileNum, String itemId, UserInfo info) ;


    /**
     *  获取验证码并不再使用本号
     * @param user
     * @param itemId
     * @param phoneNum
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
