package cn.doodlister;

import cn.doodlister.api.app.cddh.entity.CDDHResult;
import cn.doodlister.api.app.cddh.entity.CDDHUser;
import cn.doodlister.api.app.cddh.service.CDDHUserService;
import cn.doodlister.api.app.exception.CodeErrorException;
import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.sm.*;

import cn.doodlister.api.yzm.ym.YMCodeServiceImpl;
import cn.doodlister.utils.SMLoginUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;



public class CDDHRun implements  Runnable {

    private static Logger logger = Logger.getLogger(CDDHRun.class);
    //项目ID：36848
    private static final String pid = "36848";
    private static final String itemId = "13235";
    private static  String inviteCode ="14247378";





    public CDDHRun() {
    }

    /**
     * 1.发码平台--得到手机号
     * 2.app--发送验证码
     * 3.平台--得到验证码
     * 4.app--登陆
     * 5.开始刷。。
     */


      @Test
      public  void test(){
          for(int i=0;i<2;++i)
              run();
      }


    @Test
    public void run(){
        String username ="Doodlister";
        String password ="szy19960530";


        System.out.println(inviteCode);
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(username);
        userInfo.setPwd(password);
        try {
            //UserInfo userInfo = SMLoginUtils.login(username, password);
            CodeService service = new YMCodeServiceImpl();
            service.login(userInfo);
          //  String info = SMLoginUtils.getUserInfo(userInfo.getUid(), userInfo.getToken());
            String info = service.getUserInfo(userInfo);
            logger.info(info);
            //1.发码平台--得到手机号
            //String phone =  SMLoginUtils.getMobileNum(pid,userInfo.getUid(),userInfo.getToken());
            String phone = service.getMobileNum(itemId,userInfo);
            System.out.println("手机号:"+phone);
            //2.app--发送验证码
            CDDHResult codeResult = CDDHUserService.getCode(phone);
            if(codeResult.getCode()!=CDDHResult.SUCCESS){
                logger.error("短信发送失败,原因:"+codeResult);
                return ;
            }
            //3.平台--得到验证码
            //String code = SMLoginUtils.MSG_NO_RECEIVE;
            String code = "3001";
            while (code.equals("3001")){
                //等待30秒  得到验证码
                Thread.sleep(5000);
             //  code = SMLoginUtils.getVcodeAndReleaseMobile(userInfo,phone,"Doodlister");
                code = service.getVcodeAndReleaseMobile(userInfo,itemId,phone);

            }
            System.out.println(code);
            code = getCodeByMsg(code);
            //4.app--登陆
            CDDHUser user = CDDHUserService.login(phone, code);
            //5.开始刷。。
            CDDHUserService.bindInviteCode(inviteCode,user.getSessionToken());
            logger.info(" ^ _ ^!! 绑定成功");
        } catch (CodeErrorException codeException) {
            codeException.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    private String getCodeByMsg(String msg){
        String code;

        code = msg.substring(msg.indexOf("：")+1,msg.indexOf("，该"));
        return code;
    }
    @Test
    public void sendCode() throws Exception {
        String phone ="15183856437";
        CDDHResult codeResult = CDDHUserService.getCode(phone);
        if(codeResult.getCode()!=CDDHResult.SUCCESS){
            logger.error("短信发送失败,原因:"+codeResult);
            return ;
        }
    }
    @Test
    public  void  testLogin(){
        //7个了
        String code ="【冲顶大会】您的验证码为：2136，该验证码5分钟内有效，请勿泄露。";
        String phone = "13105704007";
        String inviteCode="10262154";
        code = getCodeByMsg(code);
        System.out.println(code);
        //4.app--登陆
        CDDHUser user = null;
        try {
            user = CDDHUserService.login(phone, code);
            //5.开始刷。。
            CDDHUserService.bindInviteCode(inviteCode,user.getSessionToken());
            logger.info(" ^ _ ^!! 绑定成功");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void ttt(){
     //   "success|【冲顶大会】您的验证码为：0114，该验证码5分钟内有效，请勿泄露。"
        String t="success|【冲顶大会】您的验证码为：5408，该验证码5分钟内有效，请勿泄露。";
        for (String s : t.split("\\|")) {
            System.out.println(s);
        }

    }

}
