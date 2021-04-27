package com.hyg.permission;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/23
 * @Desc
 */
class PermissionUtils {


    public static String[] toArray(List<String> permissions){
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }
        String[] pers = new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            pers[i] = permissions.get(i);
        }
        return pers;
    }


    public static boolean is(){
        return false;
    }

}
