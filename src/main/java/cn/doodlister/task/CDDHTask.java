package cn.doodlister.task;

import cn.doodlister.api.app.BaseService;
import cn.doodlister.api.app.cddh.service.CDDHService;
import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.api.yzm.ym.YMCodeServiceImpl;

public class CDDHTask extends BaseTask {
    public static String YM_ITEM_ID = "13235";
    public static String SM_ITEM_ID = "36848";
    @Override
    protected String getCodeByMsg(String msg) {
        String code="";
        if(msg != "")
            code = msg.substring(msg.indexOf("：")+1,msg.indexOf("，该"));
        return code;
    }

    public CDDHTask(String projectId, String inviteCode, CodeService codeService, UserInfo userInfo, BaseService baseService) {
        super(projectId, inviteCode, codeService, userInfo, baseService);
    }

    public CDDHTask(String projectId,String inviteCode,UserInfo userInfo) {
        super(projectId, inviteCode, new YMCodeServiceImpl(), userInfo, new CDDHService());
    }
}
