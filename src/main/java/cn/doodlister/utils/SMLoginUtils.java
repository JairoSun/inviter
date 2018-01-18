package cn.doodlister.utils;

import cn.doodlister.CDDHRun;
import cn.doodlister.api.yzm.sm.*;
import org.apache.log4j.Logger;

public class SMLoginUtils {
    private static Logger logger = Logger.getLogger(SMLoginUtils.class);
    public static final String  MSG_NO_RECEIVE ="not_receive";//未收到验证码
    /**
     * 短信平台登陆方法
     * @param username
     * @param password
     */
    public static UserInfo login(String username, String password) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(username);
        userInfo.setPwd(password);
        String result = UserService.login(userInfo);
        if (MyControl.isNull(result)) {
            logger.error("收码平台登陆失败   " +result);
        }
        if (result.toLowerCase().startsWith(userInfo.getUid().toLowerCase())) {
            String[] strings = result.split("\\|");
            userInfo.setToken(strings[1]);
            logger.info("用户"+username+"登陆成功");
        }
        else{
            logger.error("收码平台登陆失败 请检查用户名密码是否正确 result = " +result);
        }
        return userInfo;
    }
    /**
     * 获取用户信息
     * @param userName
     * @param token
     * @return
     */
    public static String getUserInfo(String userName, String token) {
        try {
            String para = String.format("uid=%s&token=%s", userName, token);
            String url = Config.getUserInfos + para;
            logger.debug("收码平台:"+ url);
            String response = HttpHelper.getHtml(url);
            String[] strings = response.split(";");
            if(strings.length==4){
                return "用户名:"+strings[0]+" 积分:"+strings[1]+" 可用余额:"+strings[2]+" 可同时获取号码数:"+strings[3];
            }else{
                return response;
            }
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
            String response = HttpHelper.getHtml(url);
            String[] strings = response.split("\\|");
            if(strings.length == 2){
                logger.info("获取到手机号:"+strings[0]);
                return strings[0];
            }else{
                //未取到手机号
                logger.error("未取到手机号:"+ response);
                throw new Exception();
            }
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 获取验证码并不再使用本号
     * @param userInfo
     * @param mobileNum 用getMobilenum方法获取到的手机号
     * @param author_uid 软件开发者用户名(可选, 可得10%的消费分成)
     * @return 成功返回：手机号码|验证码短信 失败请参考文档
     * @throws Exception
     */
    public static String getVcodeAndReleaseMobile(UserInfo userInfo,String mobileNum, String author_uid) throws Exception {
        if (userInfo.isLogin()) {
            String result = UserService.getVcodeAndReleaseMobile(userInfo.getUid(), userInfo.getToken(), mobileNum, author_uid);

            String[] strings = result.split("\\|");
            if(strings.length == 2){
                logger.info("得到验证短信:"+strings[1]);
                return strings[1];
            }else{
                if("not_receive".equals(result)){
                    logger.info("当前未收到验证码，程序等待验证码");
                    return "not_receive";
                }
                logger.error(result);
                throw new Exception();
            }

        }
        return "";
    }


}
