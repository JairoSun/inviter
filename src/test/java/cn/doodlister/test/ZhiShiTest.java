package cn.doodlister.test;

import cn.doodlister.api.app.cddh.entity.CDDHResult;
import cn.doodlister.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.HashMap;
import java.util.Map;

public class ZhiShiTest {
    @Test
    public void zsGetCode() throws Exception {
        String url = "http://service.h7tuho5mf.cn/api/v1/verification_code";
        Map jsonMap = new HashMap<String,String>();
        //{"phone":"8613230282281","region":"cn"}请求数据
        jsonMap.put("phone","8617336321970");
        jsonMap.put("region","cn");
        String jsonString = JSON.toJSONString(jsonMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);

        System.out.println( JSON.parseObject(responseJson));
        //{"error_msg":"操作成功","channel":"question","request_id":"1515809554285954","dm_error":0}
    }

    @Test
    public void zsLogin() throws Exception {
        String url = "http://service.h7tuho5mf.cn/api/v1/login";
        Map jsonMap = new HashMap<String,String>();
        //请求数据 {"phone":"8613230282281","platform":"phone","request_id":"1515806403128152","code":"257584"}
        jsonMap.put("phone","8617336321970");
        jsonMap.put("platform","phone");
        jsonMap.put("request_id","1515809554285954");
        jsonMap.put("code","963835");
        String jsonString = JSON.toJSONString(jsonMap);
        String responseJson = HttpClientUtil.doPost(url, jsonString);
        //{"uid":7871510,"error_msg":"操作成功","session":"308KULlnIWXEgsxYmqmWi2wRw6SIlkRwk5FA2ldGQnY94BMyAi3i3","first_login":true,"first_register":true,"dm_error":0}
        System.out.println( JSON.parseObject(responseJson));
    }
    @Test
    public void zsInvite() throws Exception {
       // String url = "http://service.h7tuho5mf.cn/api/invite_code/bind?cc=TG43907&lc=3736fe96a324a083&mtxid=000000000000&devi=474d65aa&sid=30eEE6WcY3XORGn4i1fSCLRVlHAo6eoNQUSnq1WUtGLaM5WeAi3i3&osversion=android_24&cv=CR1.2.10_Android&imei=&proto=8&conn=3G&ua=XiaomiMI5&logid=&icc=&uid=7852456&aid=1fc32fc2cdf7caa9&smid=&imsi=&mtid=d9c471b76710fca876003681b785db87&code=W6VS4";
        String url = "http://service.h7tuho5mf.cn/api/invite_code/bind?uid=7852425&code=W6VS4";



        String responseJson = HttpClientUtil.doGet(url);
        //{"uid":7871510,"error_msg":"操作成功","session":"308KULlnIWXEgsxYmqmWi2wRw6SIlkRwk5FA2ldGQnY94BMyAi3i3","first_login":true,"first_register":true,"dm_error":0}
        System.out.println( JSON.parseObject(responseJson));
        //{"error_msg":"请求参数错误","dm_error":499}
        //{"error_msg":"操作成功","text":"不能输入自己的邀请码哦~","bind_success":0,"dm_error":0}
    }
    //W6VS4
    @Test
    public void tesee() throws InterruptedException {
        //        10
        for (int i = 2000760; i < 2000760 +15 ; ++i) {
            new Thread(new ZSBdRunn(i + "", "AEBU4")).start();
            Thread.sleep(500);
        }
        Thread.sleep(5000);
    }




}
