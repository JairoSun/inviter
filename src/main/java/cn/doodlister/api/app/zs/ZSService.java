package cn.doodlister.api.app.zs;

import cn.doodlister.api.app.BaseService;
import cn.doodlister.api.app.exception.BindInviteException;
import cn.doodlister.api.app.exception.LoginFailException;
import cn.doodlister.api.app.exception.SendCodeException;
import cn.doodlister.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ZSService extends BaseService {
    @Override
    public Map<String,String> sendCode(String userPhone) throws Exception {
        Map<String,String> dataMap = new HashMap<String, String>();
        String url = "http://service.h7tuho5mf.cn/api/v1/verification_code";
        dataMap.put("phone","86"+userPhone);
        dataMap.put("region","cn");
        String jsonString = JSON.toJSONString(dataMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        logger.debug(jsonObject);
        if("操作成功".equals(jsonObject.getString("error_msg"))){
            logger.info("手机号:"+userPhone+"发送短信成功.等待短信平台接受短信");
            dataMap.put("request_id",jsonObject.getString("request_id"));
            return dataMap;
        }else{
            logger.error("手机号:"+dataMap.get("phone")+"短信发送失败.."+jsonObject);
            throw new SendCodeException("手机号:"+dataMap.get("phone")+"短信发送失败.."+jsonObject);
        }
        //请求成功JSON
        //{"error_msg":"操作成功","channel":"question","request_id":"1515809554285954","dm_error":0}
        //JSON.parseObject(responseJson)
    }
    @Override
    public Map<String,String> login(String code,Map<String,String> dataMap) throws Exception {
        //请求数据 {"phone":"8613230282281","platform":"phone","request_id":"1515806403128152","code":"257584"}
        //返回数据 {"uid":7871510,"error_msg":"操作成功","session":"308KULlnIWXEgsxYmqmWi2wRw6SIlkRwk5FA2ldGQnY94BMyAi3i3","first_login":true,"first_register":true,"dm_error":0}
        String url = "http://service.h7tuho5mf.cn/api/v1/login";
        dataMap.put("platform","phone");
        dataMap.put("code",code);
        String jsonString = JSON.toJSONString(dataMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);

        JSONObject jsonObject = JSON.parseObject(responseJson);
        logger.debug(jsonObject);
        if("操作成功".equals(jsonObject.getString("error_msg"))){
            logger.info("手机号:"+dataMap.get("phone")+"登陆成功");
            dataMap.put("uid", jsonObject.get("uid").toString());
            dataMap.put("session", (String) jsonObject.get("session"));
            return dataMap;
        }else{
            logger.error("手机号:"+dataMap.get("phone")+"登陆失败.."+jsonObject);
            throw new LoginFailException("手机号:"+dataMap.get("phone")+"登陆失败.."+jsonObject);
        }
    }

    @Override
    public Boolean bindInviteCode(String inviteCode,Map<String,String> dataMap) throws Exception {
        // String url = "http://service.h7tuho5mf.cn/api/invite_code/bind?sid=30eEE6WcY3XORGn4i1fSCLRVlHAo6eoNQUSnq1WUtGLaM5WeAi3i3&&uid=7852456&code=W6VS4";
        String url ="http://service.h7tuho5mf.cn/api/invite_code/bind?sid="+dataMap.get("session")+"&uid="+dataMap.get("uid")+"&code="+inviteCode;
        String responseJson = HttpClientUtil.doGet(url);
        //{"uid":7871510,"error_msg":"操作成功","session":"308KULlnIWXEgsxYmqmWi2wRw6SIlkRwk5FA2ldGQnY94BMyAi3i3","first_login":true,"first_register":true,"dm_error":0}
        //{"error_msg":"请求参数错误","dm_error":499}
        //{"error_msg":"操作成功","text":"不能输入自己的邀请码哦~","bind_success":0,"dm_error":0}

        JSONObject jsonObject = JSON.parseObject(responseJson);
        logger.debug(jsonObject);
        logger.info(url);
        logger.info(jsonObject);
        if("操作成功".equals(jsonObject.getString("error_msg"))){
            if(jsonObject.getString("text").contains("您已输入过邀请码哦")){
                throw new BindInviteException("手机号:"+dataMap.get("phone")+"绑定失败.."+jsonObject);
            }
            logger.info("手机号:"+dataMap.get("phone")+"绑定成功");
        }else{
            logger.error("手机号:"+dataMap.get("phone")+"绑定失败.."+jsonObject);
            logger.error(url);
            logger.error(dataMap);
            throw new BindInviteException("手机号:"+dataMap.get("phone")+"绑定失败.."+jsonObject);
        }
        return true;
    }
}
