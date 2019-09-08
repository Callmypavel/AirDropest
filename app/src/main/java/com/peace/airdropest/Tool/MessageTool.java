package com.peace.airdropest.Tool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.peace.airdropest.Resource;
import com.peace.airdropest.View.MainView;

/**
 * Created by peace on 2018/3/26.
 */

public class MessageTool {
    public static void sendMessageWithInfoInBundle(Handler handler,String key,String value,int what){
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key,value);
        message.setData(bundle);
        handler.sendMessage(message);
        LogTool.log("MessageTool","send message :key:"+key+",value:"+value);
    }
}
