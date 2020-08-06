package com.kwg.zujianhua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kwg.annotation.BindPath;
import com.kwg.arouter.ARouter;
import com.kwg.member.MemberActivity;

/**
 * 组件化开发要注意的几个点
 *
 * 1.要注意报名和资源文件夹的命名
 * 2。Gradle中的版本的统一管理
 * 3。组件Application和Library之间的切换
 * 4。AndroidMainfest.xml文件的区分
 *
 */

@BindPath("main/main")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("lkdsfjaskjdf","dklajsfk");
                ARouter.getInstance().jumpActivity("login/login",null);
            }
        });

    }

}