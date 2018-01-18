package cn.doodlister.test;

import org.junit.Test;

public class DemoTest {
    @Test
    public void test() throws Exception {
        Boolean loginBoolean = DXPTTestDemo.login("doodlister", "szy19960530");
        if (loginBoolean) {
            DXPTTestDemo.getUserInfos();
            DXPTTestDemo.getMobileNum("29054");
        }
        else {
            System.out.println("登陆失败");
        }
    }
}
