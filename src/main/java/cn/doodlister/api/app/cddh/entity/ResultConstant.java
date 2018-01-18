package cn.doodlister.api.app.cddh.entity;

public enum  ResultConstant {
    SUCCESS(0,"请求成功"),
    CODE_ERROR(3,"验证码错误");

    private int value;
    private String description;

    ResultConstant(int value,String description){
        this.value=value;
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public static ResultConstant valueOf(int value){
        switch (value){
            case 0:
                return SUCCESS;
            case 3:
                return CODE_ERROR;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
       /* for(ResultConstant con :ResultConstant.values()){
            System.out.println(con+":"+con.getDescription());

        }*/
        System.out.println(ResultConstant.valueOf(0));
    }
}
