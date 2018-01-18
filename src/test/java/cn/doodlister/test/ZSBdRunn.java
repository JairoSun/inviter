package cn.doodlister.test;

import cn.doodlister.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;

public class ZSBdRunn implements Runnable {
    //String url = "http://service.h7tuho5mf.cn/api/invite_code/bind?uid=7852425&code=W6VS4";
    private String url ;

    //AADFP
    public ZSBdRunn(String uid,String code) {

        this.url = "http://service.h7tuho5mf.cn/api/invite_code/bind?uid="+uid+"&code="+code;
    }

    @Override
    public void run() {
        System.out.println( "请求:"+url);
        String responseJson = HttpClientUtil.doGet(url);
        System.out.println( JSON.parseObject(responseJson));
    }
}
