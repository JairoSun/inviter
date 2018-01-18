package cn.doodlister.api.app.cddh.service;

import cn.doodlister.api.app.cddh.entity.CDDHResult;
import cn.doodlister.api.app.cddh.entity.CDDHUser;
import cn.doodlister.api.app.exception.BindInviteException;
import cn.doodlister.api.app.exception.CodeErrorException;
import cn.doodlister.api.app.exception.LoginFailException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CDDHUserServiceTest {

    @Test
    void getCode() {
        try {
            CDDHResult code = CDDHUserService.getCode("18830292635");
            System.out.println(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void login(){
        try {
            CDDHUser user = CDDHUserService.login("15694310744", "2817");
            System.out.println(user);
        } catch (CodeErrorException codeException) {
            codeException.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void bindInviteCode(){
        try {
            CDDHUserService.bindInviteCode("2440870","1.10016494.1037476.HUm.96cd67309c410d2f8a2e23daefd8b645");
        } catch (BindInviteException e) {
            e.printStackTrace();
        }catch (LoginFailException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}