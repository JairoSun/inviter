package cn.doodlister.api.yzm.ym;

import cn.doodlister.api.yzm.CodeService;
import cn.doodlister.api.yzm.sm.UserInfo;
import cn.doodlister.utils.HttpClientUtil;
import cn.doodlister.utils.SMLoginUtils;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 *   通信方式：本平台采用HTTP协议（GET/POST）方式通信
     API接口地址：http://api.51ym.me/UserInterface.aspx
     调用方式： GET/POST
     默认编码：UTF8（GB2312请添加传入参数encode=gb2312）

     特别提示：
     1、首先请调用登录接口或进入用户中心获取Token，才能调用其他接口；
     1、下表中带“*”的参数为可选参数，可根据需要选择是否提交该参数；
     2、接口地址和参数不区分大小写；
     3、接口调用成功则会返回“success”或以“success|”开头的返回值，如果失败则返回相应的【错误代码】；
     4、修改账号密码后原有Token自动失效，请重新调用登陆接口获取新的token；
     5、获取号码在10分钟内没有被用户释放，系统会强制释放；
     6、接入专属对接只需要在易码网站上操作，用户随时可以创建、接入和退出，不需要在接口上传参数
     7、开发者请在“获取电话号码”接口处添加传入参数coderid=你的软件作者ID[请登录系统获取]。
 */
public class YMCodeServiceImpl implements CodeService {
    private static Logger logger = Logger.getLogger(YMCodeServiceImpl.class);
    private static final String token = "003097882ef89ed301d479648f294248bdc28c50";
    @Override
    public UserInfo login(UserInfo userInfo) {
        String url = "http://api.51ym.me/UserInterface.aspx?action=login&username="+userInfo.getUid()+"&password="+userInfo.getPwd();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);
        String[] tokens = response.split("\\|");
        if("success".equals(tokens[0])){
            userInfo.setToken(tokens[1]);
            return userInfo;
        }else{
            logger.error("登陆失败");
            //TODO 失败异常
            return null;
        }

    }

    @Override
    public String getUserInfo(UserInfo user) {
        /**
         * 获取当前用户的账户信息
         http://api.51ym.me/UserInterface.aspx?action=getaccountinfo&token=token
         传入参数：
         *format=1，指定返回JSON格式数据,若该参数为空，则返回格式为“|”分割的字符串。

         成功返回值：
         字符串格式：success|用户名|账户状态|账户等级|账户余额|冻结金额|账户折扣|获取号码最大数量，“|”是分隔符（默认返回数据格式）
         JSON格式：success|JSON数据（参数中指定format=1时返回数据格式）
         */
        String url = "http://api.51ym.me/UserInterface.aspx?action=getaccountinfo&token="+user.getToken();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);
        String[] tokens = response.split("\\|");
        if("success".equals(tokens[0])){

            return response;
        }else{
            logger.error("登陆失败");
            //TODO 失败异常
            return null;
        }


    }

    @Override
    public String getMobileNum(String itemid, UserInfo user) {
        /**
         * 获取电话号码，同一项目同一号码在未释放前可以获取多条短信；
         * 如果要用同一号码同时做多个项目，请先按第一个项目获取一个号码，
         * 再通过获取指定号码的方式取得该号码在其他项目上的使用权全部项目获取成功后，再开始使用。
         * itemid=项目编号
            http://api.51ym.me/UserInterface.aspx?action=getmobile&itemid=项目编号&token=token
         */

        String url = "http://api.51ym.me/UserInterface.aspx?action=getmobile&itemid="+itemid+"&token="+user.getToken();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);
        String[] tokens = response.split("\\|");
        if("success".equals(tokens[0])){

            return tokens[1];
        }else{
            logger.error("登陆失败");
            //TODO 失败异常
            return null;
        }

    }

    @Override
    public Boolean releaseAll(UserInfo user) {
        /**
         * 	释放该用户下的所有号码（包括不同终端获取的全部号码）
         http://api.51ym.me/UserInterface.aspx?action=releaseall&token=token
         传入参数：
         无

         成功返回值： success
         */
        String url = "http://api.51ym.me/UserInterface.aspx?action=releaseall&token="+user.getToken();
        String response = HttpClientUtil.doGet(url);
        logger.debug(response);

        if("success".equals(response)){

            return true;
        }else{
            logger.error("登陆失败");
            //TODO 失败异常
            return true;
        }
    }


    @Override
    public String addIgnore(String mobileNum, String uid, String token, String pid) {
        return null;
    }

    @Override
    public String getVcodeAndReleaseMobile(UserInfo user,String itemId,String phoneNum) {
        /**
         * 获取短信	在你使用获取到的电话号码后，调用该接口获取短信。因短信可能延迟，建议每5秒调用一次，调用60秒以上（可增加获取成功率）。
         http://api.51ym.me/UserInterface.aspx?action=getsms&mobile=电话号码&itemid=项目编号&token=token
         传入参数：
         mobile=电话号码
         itemid=项目编号
         *release=1，获取短信并释放号码，若要继续使用该号码，请勿带入该参数。

         成功返回值： success|短信内容
         */

        String url = "http://api.51ym.me/UserInterface.aspx?action=getsms&mobile="+phoneNum+"&itemid="+itemId+"&token="+user.getToken()+"&release=1";
      //  String response = HttpClientUtil.doGet_UTF8(url);
      //  String url = "http://api.51ym.me/UserInterface.aspx";
        Map<String,String> map = new HashMap<String,String>();
       // map.put("mobile"phoneNum);
        //map.put("itemid",itemId);
        String response = HttpClientUtil.doPost(url,map);
        logger.info(response);
        if("3001\r\n".equals(response)){
            return "3001";
        }
        String[] tokens = response.split("\\|");
        if("success".equals(tokens[0])){
           // releaseAll(user);
            return tokens[1];

        }else{
            logger.error("获取失败");

            //TODO 失败异常
            return "";
        }


    }

    @Override
    public String getVcodeAndHoldMobilenum(String uid, String token, String mobileNum, String nextId, String author_uid) {
        return null;
    }
}
