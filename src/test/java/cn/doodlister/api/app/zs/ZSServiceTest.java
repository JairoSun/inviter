package cn.doodlister.api.app.zs;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ZSServiceTest {
    ZSService service = new ZSService();
    @Test
    void sendCode() throws Exception {
        Map<String, String> dataMap = service.sendCode("15568826504");
        System.out.println(dataMap);
        //{phone=8613230282281, region=cn, request_id=1516345485950895}
    }

    @Test
    void login() throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("request_id","1516355511767639");
        dataMap.put("phone","8615568826504");
        service.login("370460", dataMap);
        System.out.println(dataMap);
        //{uid=7852456, code=739087, phone=8613230282281, session=30j4i0BbGoq1zJUCrvDncoIgbemIbkPf2BuEwE79qxb8XYYHgi3i3, request_id=1516345485950895, platform=phone}
    }

    @Test
    void bindInviteCode() throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("request_id","1516355511767639");
        dataMap.put("phone","8615568826504");
        dataMap.put("uid","8072217");
        dataMap.put("code","370460");
        dataMap.put("session","30btHEWBdTUnbkER3Fw2vItSO94IDi29EHVU7OUSwvMEAYuXwi3i3");
        service.bindInviteCode("W6VS4",dataMap);
        System.out.println(dataMap);
    }

}