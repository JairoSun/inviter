package cn.doodlister.task;

import cn.doodlister.api.app.BaseService;
import cn.doodlister.api.app.cddh.service.CDDHService;
import cn.doodlister.api.app.zs.ZSService;
import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.api.yzm.sm.SMCodeServiceImpl;
import cn.doodlister.api.yzm.ym.YMCodeServiceImpl;
import org.junit.jupiter.api.Test;

public class ZSTask extends BaseTask  {
    public static String YM_ITEM_ID = "13415";
    public static String SM_ITEM_ID = "37275";
    public ZSTask(String projectId, String inviteCode, CodeService codeService, UserInfo userInfo, BaseService baseService) {
        super(projectId, inviteCode, codeService, userInfo, baseService);
    }

    @Override
    protected String getCodeByMsg(String msg) {
        String code = "";
        if(null!=msg && msg.contains("芝士超人")){
            code = msg.split("：")[1].split("。")[0];
            code = code.trim();
        }
        return code;
    }

    public ZSTask(String projectId,String inviteCode,UserInfo userInfo) {
        super(projectId, inviteCode, new YMCodeServiceImpl(), userInfo, new ZSService());
    }

}
