package cn.doodlister.task;


import cn.doodlister.api.app.BaseService;

import cn.doodlister.api.app.exception.CodeErrorException;
import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.UserInfo;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class BaseTask  implements Callable<Boolean> {
    protected  Logger logger = Logger.getLogger(this.getClass());
    //填入对应的项目编号
    protected   String projectId = null;
    //邀请码字段
    protected String inviteCode = null;
    //收码平台
    protected CodeService codeService;
    //收码平台对应的UserInfo
    protected UserInfo userInfo;
    //各个service
    protected BaseService baseService;
    //验证码接收循环次数 默认10次。
    protected int resiveNum = 20;
    /**
     * 子类应重写此方法，根据短信得到对应的邀请码
     * @param msg
     * @return
     */
    protected abstract String getCodeByMsg(String msg);
    public BaseTask(String projectId,String inviteCode, CodeService codeService, UserInfo userInfo, BaseService baseService) {
        this.projectId = projectId;
        this.inviteCode = inviteCode;
        this.codeService = codeService;
        this.userInfo = userInfo;
        this.baseService = baseService;
    }
    @Override
    public Boolean call() throws Exception{
        String phone="";
        try {
            //登陆收码平台
            codeService.login(userInfo);
            //得到当前收码平台用户信息
            String info = codeService.getUserInfo(userInfo);
            logger.info(info);
            //1.发码平台--得到手机号
            phone = codeService.getMobileNum(projectId,userInfo);
            //2.app--发送验证码

            Map<String, String> dataMap = baseService.sendCode(phone);
            //3.平台--得到验证码
            //循环10次,接受验证码。。 否则报超时
            String code = "";
            int resiveCount;
            for(resiveCount = 0;resiveCount < resiveNum && "".equals(code); ++resiveCount){
                Thread.sleep(5000); //等待5秒
                logger.info("手机号:"+phone+" 第"+ (resiveCount+1) + "尝试,接收验证码.");
                code = codeService.getVcodeAndReleaseMobile(userInfo,projectId,phone);
            }
            //判断收到验证码还是超时了
            if(resiveCount >= resiveNum){
                logger.error("接收短信超时");
                //加黑该号码
                codeService.addIgnore(phone,projectId,userInfo);
                return false;
            }
            code = getCodeByMsg(code);
            if("".equals(code)){
                logger.error("未得到验证码"+code);
                return false;
            }
            //4.app--登陆
            dataMap = baseService.login(code, dataMap);
            //5.开始刷。。
            Boolean isSuccess = baseService.bindInviteCode(inviteCode, dataMap);
            if(isSuccess){
                logger.info(" ^ _ ^!! 绑定成功");
                return true;
            }

        } catch (CodeErrorException codeException) {
            codeException.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
            //加黑该号码
            codeService.addIgnore(phone,projectId,userInfo);
            logger.info("手机号:"+phone+"拉黑成功");
        }
        return false;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public CodeService getCodeService() {
        return codeService;
    }

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public int getResiveNum() {
        return resiveNum;
    }

    public void setResiveNum(int resiveNum) {
        this.resiveNum = resiveNum;
    }
}
