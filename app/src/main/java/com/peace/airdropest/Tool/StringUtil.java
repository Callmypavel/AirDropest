package com.peace.airdropest.Tool;

import com.peace.airdropest.Entity.Base.GameObject;

import java.util.ArrayList;

/**
 * Created by peace on 2017/10/23.
 */

public class StringUtil {
    public static String[] splitAttributes(String line,String splitOperater){
        String[] strings = line.split(splitOperater);
        String[] attributes = new String[strings.length*2];
        for (int i = 0; i < strings.length; i++) {
            String[] attribute = strings[i].split("=");
            attributes[2*i] = attribute[0];
            attributes[2*i+1] = attribute[1];
        }
        return attributes;
    }
    public static String getNames(ArrayList<GameObject> gameObjects){
        if (gameObjects!=null){
            StringBuilder stringBuilder = new StringBuilder();
            for (GameObject gameObject : gameObjects){
                stringBuilder.append(gameObject.getId());
            }
            return stringBuilder.toString();
        }
        return "没有GameObject";
    }
    public static String getStringArray(String[] stringArray){
        if (stringArray!=null){
            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0;i<stringArray.length;i++){
                stringBuilder.append("["+i+"]:"+stringArray[i]+"\n");
            }
            return stringBuilder.toString();
        }
        return "没有数组";
    }
}
