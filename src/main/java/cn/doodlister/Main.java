package cn.doodlister;

import cn.doodlister.api.app.zs.ZSService;
import cn.doodlister.api.yzm.UserInfo;
import cn.doodlister.api.yzm.ym.YMCodeServiceImpl;
import cn.doodlister.task.BaseTask;
import cn.doodlister.task.CDDHTask;
import cn.doodlister.task.ZSTask;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    /**
     *
     * @param inviteCode 邀请码
     * @param num   数量
     * @param taskClazz CDDHTask 或 ZSTask
     * @param itemId 短信平台ItemID 已经再 对应的TASK里内置 易码和速码的itemID。
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public void runTask(String inviteCode,int num,Class taskClazz,String itemId) throws InterruptedException, ExecutionException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //这里填入收码平台的账号密码
        UserInfo userInfo = new UserInfo("","");
        List<FutureTask<Boolean>> futureTaskList = new ArrayList();
        Constructor constructor = taskClazz.getConstructor(String.class, String.class, UserInfo.class);

        for(int i=0;i<num;++i){
            futureTaskList.add(new FutureTask<Boolean>(
                    (BaseTask)constructor.newInstance(itemId,inviteCode,userInfo)
            ));
            new Thread(futureTaskList.get(i)).start();
            Thread.sleep(5000);
        }
        int successCount = 0;
        for(int i=0;i<num;++i){
            if( futureTaskList.get(i).get()){
                ++successCount;
            }

        }
        System.out.println("任务执行完成，成功"+successCount+"个");
        System.out.println("按下任意键退出程序");
        new Scanner(System.in);
    }



    @Test
    public  void cddhRun() throws InterruptedException, ExecutionException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("冲顶大会--开始");
        //这里填入邀请码
        String inviteCode ="";
        int num= 2;
        runTask(inviteCode,num,CDDHTask.class,CDDHTask.SM_ITEM_ID);

    }
    @Test
    public  void zscrRun() throws InterruptedException, ExecutionException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("芝士超人--开始");
        String inviteCode ="";
        //13
        int num= 2;

        runTask(inviteCode,num,ZSTask.class,ZSTask.YM_ITEM_ID);

    }
    @Test
    public void relaseAll(){
        YMCodeServiceImpl codeService =new YMCodeServiceImpl();
        UserInfo userInfo = new UserInfo("doodlister","szy19960530");
        codeService.login(userInfo);
        Boolean aBoolean = codeService.releaseAll(userInfo);
        System.out.println(aBoolean);
    }






}
