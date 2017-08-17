package com.peace.airdropest;

import android.util.Log;

/**
 * Created by ouyan on 2016/9/22.
 */

public class LogTool {
    private static boolean isLogOpen = true;
    public static void log(String tag,String message){
        if(isLogOpen){
            Log.v(tag,message);
        }
    }
    public static void log(Object obeject,String message,boolean isLog){
        if(isLogOpen){
            if(isLog){
                Log.v(obeject.getClass().getSimpleName(),message);
            }

        }
    }
    public static void log(Object obeject,String message){
        if(isLogOpen){
                Log.v(obeject.getClass().getSimpleName(),message);
        }
    }
}
