package cn.doodlister.api.app;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class  BaseService  {
    protected   Logger logger = Logger.getLogger(this.getClass());
    /**
     * 向指定手机号发送邀请码
     * @param userPhone
     * @return
     * @throws Exception
     */
    public abstract Map<String,String> sendCode(String userPhone) throws Exception;
    public abstract Map<String,String> login(String code, Map<String,String> dataMap) throws Exception ;
    public abstract Boolean bindInviteCode(String inviteCode,Map<String,String> dataMap) throws Exception;
}