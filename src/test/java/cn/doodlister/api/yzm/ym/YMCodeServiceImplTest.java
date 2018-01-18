package cn.doodlister.api.yzm.ym;

import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.sm.UserInfo;
import cn.doodlister.utils.SMLoginUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

public class YMCodeServiceImplTest {

    @Test
    public void login(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("doodlister");
        userInfo.setPwd("szy19960530");
        CodeService service = new YMCodeServiceImpl();
        System.out.println(service.login(userInfo));

    }

    @Test
    public void getUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("doodlister");
        userInfo.setPwd("szy19960530");
        CodeService service = new YMCodeServiceImpl();
        service.login(userInfo);
        System.out.println( service.getUserInfo(userInfo));
    }

    @Test
    public void getPhoneNum(){
        // 13235 冲顶大会
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("doodlister");
        userInfo.setPwd("szy19960530");
        CodeService service = new YMCodeServiceImpl();
        service.login(userInfo);

        String mobileNum = service.getMobileNum("13235", userInfo);
        System.out.println(mobileNum);
    }

    @Test
    public void releseAll(){
        // 13235 冲顶大会
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("doodlister");
        userInfo.setPwd("szy19960530");
        CodeService service = new YMCodeServiceImpl();
        service.login(userInfo);


        System.out.println(service.releaseAll(userInfo));
    }

    @Test
    public void getVcodeAndReleaseMobile(){
        // 13235 冲顶大会
        String itemNum = "13235";
        String phoneNum = "15183856437";

        UserInfo userInfo = new UserInfo();
        userInfo.setUid("doodlister");
        userInfo.setPwd("szy19960530");
        CodeService service = new YMCodeServiceImpl();
        service.login(userInfo);

        System.out.println(service.getVcodeAndReleaseMobile(userInfo,itemNum,phoneNum));

    }

    // success|ãå²é¡¶å¤§ä¼ãæ¨çéªè¯ç ä¸ºï¼7205ï¼è¯¥éªè¯ç 5åéåææï¼è¯·å¿æ³é²ã


}
