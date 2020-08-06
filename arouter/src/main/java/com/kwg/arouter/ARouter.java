package com.kwg.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

public class ARouter {
    private static ARouter aRouter = new ARouter();


    //装在了所有activity的类对象
    private Map<String,Class<? extends Activity>> maps = new HashMap<>();

    private Context context;

    private ARouter() {
    }

    public static ARouter getInstance(){
        return aRouter;
    }


    public  void init(Context context){
        this.context = context;

        List<String> classNames = getClassName("com.kwg.util");
        for (String className: classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                //判断是不是IRouter的子类
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }


        }

    }



    /**
     * 通过包名获取包下面所有的类名
     * @return
     */

    private List<String> getClassName(String packagname) {
        //创建一个class对象的集合
        List<String> classList = new ArrayList<>();
        String path = null ;
        //通过包管理器 获取应用信息类获取apk的完整路径
        try {
            path  = context.getPackageManager().getApplicationInfo(context.getPackageName(),0).sourceDir;
            //根据apk的完整路径获取编译后的dex文件目录
            DexFile dexFile = new DexFile(path);
            Enumeration enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()){
                String name  = (String) enumeration.nextElement();
                if(name.contains(packagname)){
                    classList.add(name);
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          return  classList;

    }



    public  void addActivity(String key,Class<? extends Activity> clazz){
        if(key!= null && clazz!=null && !maps.containsKey(key)){
            maps.put(key,clazz);
        }
    }



    /**
     * 跳转窗体的方法
     */


    public  void jumpActivity(String key , Bundle bundle){
        Class<? extends Activity> aClass = maps.get(key);
        if(aClass != null){
            Intent intent  = new Intent().setClass(context,aClass);
            if(bundle!=null){
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }



}
