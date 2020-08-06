package com.kwg.login;

import com.kwg.arouter.ARouter;
import com.kwg.arouter.IRouter;

public class ActivityUtil implements IRouter {
    @Override
    public void putActivity() {
        ARouter.getInstance().addActivity("login/login",com.kwg.login.LoginActivity.class);
    }
}
