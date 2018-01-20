package cn.doodlister.api.yzm.sm;

import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMCodeServiceImpl implements CodeService {
    private static Logger logger = Logger.getLogger(SMCodeServiceImpl.class);
    String host = "http://api.eobzz.com/httpApi.do?action=";
    @Override
    public UserInfo login(UserInfo userInfo) {
        String url = host+"loginIn&uid="+userInfo.getUid()+"&pwd="+userInfo.getPwd();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);
        String[] tokens = response.split("\\|");
        if(userInfo.getUid().equals(tokens[0])){
            userInfo.setToken(tokens[1]);
            return userInfo;
        }else{
            logger.error("登陆失败"+response);
            //TODO 失败异常
            return null;
        }
    }

    @Override
    public String getUserInfo(UserInfo user) {
        String url = host+"getUserInfos&uid="+user.getUid()+"&token="+user.getToken();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);
        //用户名;积分;余额;可同时获取号码数
        String[] tokens = response.split(";");
        if(tokens.length==4){
            String info = "用户名:"+tokens[0]+" 账户余额:"+tokens[2]+" 可获取号码最大数量:" +tokens[3];
            return info;
        }else{
            logger.error("获取用户信息失败");
            //TODO 失败异常
            return null;
        }

    }

    @Override
    public String getMobileNum(String itemid, UserInfo user) {
        String url = host + "getMobilenum&pid=" + itemid + "&uid=" + user.getUid() + "&token=" + user.getToken() + "&mobile=&size=1";
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);

        String[] tokens = response.split("\\|");
        if (tokens.length == 2) {
            logger.info("获取到手机号:" + tokens[0]);
            return tokens[0];
        } else {
            //未取到手机号
            logger.error("未获取到手机号码");
            logger.error(response);
            return  null;
        }
    }



    @Override
    public Boolean releaseAll(UserInfo user) {
        //[{"Pid":37275,"Recnum":"13221736246"}, {"Pid":37275,"Recnum":"13095804219"}, {"Pid":37275,"Recnum":"15773405161"}]
        return null;
    }

    @Override
    public Boolean addIgnore(String mobileNum, String itemId, UserInfo info) {
        //&uid=用户名&token=登录时返回的令牌&mobiles=号码1,号码2,号码3&pid=项目ID
        String url = host + "addIgnoreList&uid=" + info.getUid() + "&token=" + info.getToken() + "&mobiles="+mobileNum+"&pid="+itemId;
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);

        if (response.contains("_")) {
            logger.error("加黑失败 返回信息:"+response);
            return false;
        } else {
            logger.error("加黑成功:"+response+"个");
             return true;
        }

    }

    @Override
    public String getVcodeAndReleaseMobile(UserInfo user, String itemId, String phoneNum) {
        String url = host + "getVcodeAndReleaseMobile&uid="+user.getUid()+"&token="+user.getToken()+"&mobile="+phoneNum+"&author_uid=doodlister";
        Map<String,String> map = new HashMap<String,String>();
        String response = HttpClientUtil.doPost(url,map);
        logger.info("收码平台返回状态:"+response);
        String[] token = response.split("\\|");
        if(token.length == 2){
            logger.info("得到验证短信:"+token[1]);
            return token[1];
        }else{
            if("not_receive".equals(token)){
                logger.info("当前未收到验证码，程序等待验证码");
            }
            logger.error(response);
        }
        return "";
    }

    @Override
    public String getVcodeAndHoldMobilenum(String uid, String token, String mobileNum, String nextId, String author_uid) {
        return null;
    }


    public List<String> getRecvingInfo(UserInfo userInfo, String projectId) {

        List<String> list = new ArrayList<String>();
        String url = host + "getRecvingInfo&uid="+userInfo.getUid()+"&pid="+projectId+"&token="+userInfo.getToken();
        String response = HttpClientUtil.doGet(url);
        response= response.replace("[","").replace("]","");
        if("".equals(response)){
           return  list;
        }
        String tokens[] = response.split("},");

        for(int i=0;i<tokens.length;++i){


            tokens[i]= tokens[i].substring(0,tokens[i].lastIndexOf(","))+"}";
            tokens[i]= tokens[i].substring(0,tokens[i].lastIndexOf(","))+"}";
            JSONObject object = (JSONObject) JSON.parse(tokens[i]);
            list.add(((String) object.get("Recnum")).trim());
        }
        return list;
    }
}
