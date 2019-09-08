package com.peace.airdropest.Tool;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by peace on 2017/9/19.
 */

public class FileLoader {
    public interface FileLoaderListener{
        void onLineLoaded(String line);
    }
    public static void readline(Context context, int resId, FileLoaderListener listener) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
            String content;
            while((content=bufferedReader.readLine())!=null) {
                listener.onLineLoaded(content);
            }
            bufferedReader.close();
        }catch(Exception e) {
            e.printStackTrace();

        }
    }
}
