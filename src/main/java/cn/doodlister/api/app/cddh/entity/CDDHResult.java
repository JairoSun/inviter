package cn.doodlister.api.app.cddh.entity;

import cn.doodlister.api.app.BaseResult;

public class CDDHResult  extends BaseResult{
    /**
     * 现在已知的返回格式有
     * 1.msg {"code":0,"msg":"请求成功","data":null}
     * 2.{"code":3,"msg":"验证码错误","data":null}
     * 3.{code":28,"msg":"已经设置过邀请码啦","data":null}
     * 4.{code=7, msg='未登录或登录失效', data=null}
     * **/
    public static final int SUCCESS = 0; //请求成功
    public static final int CODE_ERROR = 3; //验证码错误
    public static final int LOGIN_FAIL = 7; //登陆失效
    public static final int HAVE_BEEN_INVITED = 28; //验证码错误
    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CDDHResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
