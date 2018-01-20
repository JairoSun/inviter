package cn.doodlister.xiguajiami;

import cn.doodlister.utils.HttpClientUtil;
import cn.doodlister.utils.WatermelonUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.util.TextUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class xigua {
    @Test
    public void tttt(){
        System.out.println(WatermelonUtils.encryptWithXor(""));
        //34323636333637343c3235
        //34323636333637343c3235
    }
    @Test
    public void send_code(){
        String url = "http://security.snssdk.com/user/mobile/send_code/v2/?mix_mode=1&type=3731&unbind_exist=35&mobile=3436373635373d37373d34&iid=22773033397&device_id=38635657490&ac=4g&channel=tianzhuo_xg_sg&aid=32&app_name=video_article&version_code=627&version_name=6.2.7&device_platform=android&ab_version=249703%2C221019%2C236847%2C246276%2C249881%2C229577%2C254738%2C249818%2C249821%2C254970%2C249631%2C250927%2C252881%2C239018%2C247773%2C235684%2C249825%2C249820%2C252009%2C252593%2C249829%2C248258%2C251024%2C150151&ssmix=a&device_type=MI+5&device_brand=Xiaomi&language=zh&os_api=24&os_version=7.0&uuid=99000712322804&openudid=1fc32fc2cdf7caa9&manifest_version_code=227&resolution=1080*1920&dpi=430&update_version_code=6272";
        Map jsonMap = new HashMap<String,String>();
        //请求数据 {"phone":"8613230282281","platform":"phone","request_id":"1515806403128152","code":"257584"}



        String responseJson = HttpClientUtil.doPost(url,jsonMap);
        //{"uid":7871510,"error_msg":"操作成功","session":"308KULlnIWXEgsxYmqmWi2wRw6SIlkRwk5FA2ldGQnY94BMyAi3i3","first_login":true,"first_register":true,"dm_error":0}
        System.out.println( responseJson);

    }

}

