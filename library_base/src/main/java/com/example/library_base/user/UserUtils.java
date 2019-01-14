package com.example.library_base.user;

import com.example.library_base.bean.User;
import com.example.library_base.config.SharedPreferencesKey;
import com.example.library_base.utils.SPUtils;

import static com.example.library_base.utils.SPUtils.get;


public class UserUtils {

    public static void isLogin(boolean isLogin) {
        SPUtils.put(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_LOGIN, isLogin);
    }
    public static void saveUser(User user) {
        SPUtils.put(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_ID, user.getId());
        SPUtils.put(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_NAME, user.getUsername());
    }
    public static String getUser(String UserInfo){
        String userInfo = "";
        if(UserInfo.equals(SharedPreferencesKey.USER_NAME)){
            userInfo =  (String) SPUtils.get(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_NAME,"未登录");
        }else if(UserInfo.equals(SharedPreferencesKey.USER_NAME)){
            userInfo =  (String) SPUtils.get(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_ID,"0");
        }
        return userInfo;
    }
    public static Boolean isLogin() {
        return (Boolean) get(SharedPreferencesKey.SP_USER, SharedPreferencesKey.USER_LOGIN, false);
    }

}
