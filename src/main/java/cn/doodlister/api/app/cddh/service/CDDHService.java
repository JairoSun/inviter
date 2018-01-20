package cn.doodlister.api.app.cddh.service;

import cn.doodlister.api.app.BaseService;
import cn.doodlister.api.app.cddh.entity.CDDHResult;

import cn.doodlister.api.app.cddh.entity.CDDHUser;
import cn.doodlister.api.app.exception.BindInviteException;
import cn.doodlister.api.app.exception.CodeErrorException;
import cn.doodlister.api.app.exception.LoginFailException;
import cn.doodlister.api.app.exception.SendCodeException;
import cn.doodlister.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CDDHService extends BaseService{
    /**
     * 根据手机号获取验证码
     * 请求格式 {"phone":"17336321970"}
     * @param userPhone
     * @return
     * @throws Exception
     */
    public  CDDHResult getCode(String userPhone) throws Exception {
        String url = "http://api.api.chongdingdahui.com/user/requestSmsCode";
        Map jsonMap = new HashMap<String,String>();
        jsonMap.put("phone",userPhone);
        String jsonString = JSON.toJSONString(jsonMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);
        CDDHResult resultEntity = JSON.parseObject(responseJson, CDDHResult.class);
        logger.info("短信发送成功");
        return resultEntity;
    }

    /**
     * 登陆方法
     * 请求格式 {"phone":"17336321970","code","1234"}
     * @param userPhone
     * @param code
     * @return
     * @throws Exception
     */
    public  CDDHUser login(String userPhone,String code) throws Exception {
        String url = "http://api.api.chongdingdahui.com/user/login";

        Map jsonMap = new HashMap<String,String>();
        jsonMap.put("code",code);
        jsonMap.put("phone",userPhone);
        String jsonString = JSON.toJSONString(jsonMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);

        CDDHResult result = JSON.parseObject(responseJson, CDDHResult.class);

        if(result.getCode() == result.SUCCESS){
            //登陆成功
            JSONObject data = (JSONObject) result.getData();
            if(data.containsKey("user")){
                JSONObject userObject = (JSONObject) data.get("user");
                CDDHUser user = userObject.toJavaObject(CDDHUser.class);
                logger.info(userPhone+" 登陆成功");
                return user;
            }else{
                logger.error(userPhone+"登陆失败" +data.toString());
                throw new Exception(data.toString());
            }

        }else if(result.getCode() == result.CODE_ERROR){
            //验证码错误
            logger.error(userPhone+" 登陆失败"+"验证码错误" );
            throw new CodeErrorException(result.getMsg());
        }else{
            //未知错误
            logger.error(userPhone+"登陆失败"+result.toString());
            throw new Exception(result.toString());
        }
        //{"code":3,"msg":"验证码错误","data":null}
        //登陆成功json
        //{"code":0,"msg":"请求成功","data":{"user":{"created":null,"updated":null,"username":"手机用户21970vXk","userId":17549260,"phone":"17336321970","avatarUrl":"https://img4.jiecaojingxuan.com/st/chongding/defaultAvatar.png","balance":0,"income":0,"shareCode":"17649270","inviteCode":null,"lifePoints":0,"wxUnionId":null,"wxAppOpenId":"","sessionToken":"1.17549260.1022164.bZI.7bcbe46bdc4806cd3cde92e819f386f5","lastLoginTs":"2018-01-12T19:56:04.88","pushAlias":"Tdrwqd125sCvBjdK","status":0},"register":true}}
    }

    /**
     * 绑定邀请码
     * 请求格式
     * {
     *     "inviteCode":"2440870"
     * }
     * 返回格式
     * {"code":0,"msg":"请求成功"}
     * {code":28,"msg":"已经设置过邀请码啦","data":null}
     */
    public Boolean bindInviteCode(String inviteCode,String sessionToken) throws Exception {
        String url = "http://api.api.chongdingdahui.com/user/bindInviteCode";
        Map jsonMap = new HashMap();
        jsonMap.put("inviteCode",inviteCode);
        String jsonString = JSON.toJSONString(jsonMap);
        //设置登陆响应头
        Map<String,String> postHeader = new HashMap<String, String>();
        postHeader.put("X-Live-Session-Token",sessionToken);
        //进行请求
        String responseJson = HttpClientUtil.doPost(url, jsonString, postHeader);
        CDDHResult result = JSON.parseObject(responseJson, CDDHResult.class);

        if(result.getCode() == result.SUCCESS){
            //绑定成功
            logger.info("邀请码"+inviteCode+"绑定成功" );
            return true;
        }else if(result.getCode() == result.HAVE_BEEN_INVITED){
            //已经绑定过
            logger.error("邀请码"+inviteCode+"绑定失败 此账户已经绑定过邀请码" );
            throw new BindInviteException(result.getMsg());
        }else if(result.getCode() == result.LOGIN_FAIL){
            //登陆失效
            logger.error("邀请码"+inviteCode+"绑定失败 登陆失效" );
            throw new LoginFailException(result.getMsg());
        }else{
            //未知错误
            logger.error("邀请码"+inviteCode+"绑定失败 "+result.toString() );
            throw new Exception(result.toString());
        }

    }

    /**
     * 根据手机号获取验证码
     * 请求格式 {"phone":"17336321970"}
     * @param userPhone
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> sendCode(String userPhone) throws Exception {
        String url = "http://api.api.chongdingdahui.com/user/requestSmsCode";
        Map dataMap = new HashMap<String,String>();
        dataMap.put("phone",userPhone);
        String jsonString = JSON.toJSONString(dataMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        if("0".equals(jsonObject.get("code").toString())){
            logger.info("手机号:"+userPhone+"发送短信成功.等待短信平台接受短信");
          //  dataMap.put("request_id",jsonObject.getString("request_id"));
            return dataMap;
        }else{
            logger.error("手机号:"+dataMap.get("phone")+"短信发送失败.."+jsonObject);
            throw new SendCodeException("手机号:"+dataMap.get("phone")+"短信发送失败.."+jsonObject);
        }

    }

    @Override
    public Map<String, String> login(String code,Map<String, String> dataMap) throws Exception {

        //{"code":3,"msg":"验证码错误","data":null}
        //登陆成功json
        //{"code":0,"msg":"请求成功","data":{"user":{"created":null,"updated":null,"username":"手机用户21970vXk","userId":17549260,"phone":"17336321970","avatarUrl":"https://img4.jiecaojingxuan.com/st/chongding/defaultAvatar.png","balance":0,"income":0,"shareCode":"17649270","inviteCode":null,"lifePoints":0,"wxUnionId":null,"wxAppOpenId":"","sessionToken":"1.17549260.1022164.bZI.7bcbe46bdc4806cd3cde92e819f386f5","lastLoginTs":"2018-01-12T19:56:04.88","pushAlias":"Tdrwqd125sCvBjdK","status":0},"register":true}}

        String url = "http://api.api.chongdingdahui.com/user/login";
        dataMap.put("code",code);
        String jsonString = JSON.toJSONString(dataMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);

        JSONObject jsonObject = JSON.parseObject(responseJson);
        if("0".equals(jsonObject.get("code").toString())){
            //登陆成功
            JSONObject data = (JSONObject)jsonObject.get("data");
            if(data.containsKey("user")){
                JSONObject userObject = (JSONObject) data.get("user");
                dataMap.put("sessionToken", (String) userObject.get("sessionToken"));
                logger.info("手机号:"+dataMap.get("phone")+"登陆成功");
                return dataMap;
            }else{
                logger.error("手机号:"+dataMap.get("phone")+"登陆失败"+jsonObject);
                throw new LoginFailException(jsonObject.toString());
            }
        }else{
            logger.error("手机号:"+dataMap.get("phone")+"登陆失败"+jsonObject);
            throw new LoginFailException(jsonObject.toString());
        }
    }

    @Override
    public Boolean bindInviteCode(String inviteCode, Map<String, String> dataMap) throws Exception {
        String url = "http://api.api.chongdingdahui.com/user/bindInviteCode";

        dataMap.put("inviteCode",inviteCode);
        String jsonString = JSON.toJSONString(dataMap);
        //设置登陆响应头
        Map<String,String> postHeader = new HashMap<String, String>();
        postHeader.put("X-Live-Session-Token",dataMap.get("sessionToken"));
        //进行请求
        String responseJson = HttpClientUtil.doPost(url, jsonString, postHeader);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        if("0".equals(jsonObject.get("code").toString())){
            logger.info("手机号:"+dataMap.get("phone")+" 绑定 "+inviteCode+" 邀请码成功" );

            return true;
        }else{
            logger.error("手机号:"+dataMap.get("phone")+" 绑定 "+inviteCode+" 邀请码失败" );
            throw new BindInviteException(jsonObject.toJSONString());
        }
    }
}
