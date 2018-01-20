package cn.doodlister.api.yzm.sm;

import cn.doodlister.api.yzm.UserInfo;



import org.junit.jupiter.api.Test;


class SMCodeServiceImplTest {
    SMCodeServiceImpl service = new SMCodeServiceImpl();
    UserInfo userInfo = new UserInfo("","");
    @Test
    public void login() {
        service.login(userInfo);
        System.out.println(userInfo);
    }

    @Test
    void getUserInfo() {
        login();
        System.out.println(service.getUserInfo(userInfo));

    }

    @Test
    void getMobileNum() {
        login();
        System.out.println(service.getMobileNum("37275",userInfo));
    }

    @Test
    void releaseAll() {
    }

    @Test
    void addIgnore() {
    }

    @Test
    void getVcodeAndReleaseMobile() {
    }

    @Test
    void getVcodeAndHoldMobilenum() {
    }
    @Test
    public void getRecvingInfo() {
        login();

        System.out.println(service.getRecvingInfo(userInfo,"37275"));
    }
}