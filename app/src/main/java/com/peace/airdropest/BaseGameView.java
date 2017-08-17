package com.peace.airdropest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ouyan on 2017/8/14.
 */

public abstract class BaseGameView extends View implements View.OnTouchListener{
    protected int screenWidth;
    protected int screenHeight;
    protected int unitHeight;
    protected int unitWidth;
    //横向纵向的坐标数
    private int horizonNum = Resource.ViewConfig.DEFAULT_HORIZONTAL_NUM;
    private int verticalNum = Resource.ViewConfig.DEFAULT_VERTICAL_NUM;

    protected Bitmap backgroundBitmap;
    protected Paint defaultPaint = new Paint();

    protected Handler handler;
    public BaseGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        initParams();
        setOnTouchListener(this);
    }

    protected abstract void handleAttributes(Context context,AttributeSet attrs);

    private void initParams(){
        DisplayMetrics dm =getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        unitWidth = screenWidth/horizonNum;
        unitHeight = screenWidth/verticalNum;
        Resource.ViewConfig.SCREEN_WIDTH = screenWidth;
        Resource.ViewConfig.UNIT_WIDTH = unitWidth;
        Resource.ViewConfig.UNIT_HEIGHT = unitHeight;
    }
    public void setHandler(Handler handler){
        this.handler = handler;
    }


    protected abstract void onTouchCoordinate(int indexX, int indexY,float realX,float realY);

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int indexX = (int)x/unitWidth;
        int indexY = (int)y/unitHeight;
        onTouchCoordinate(indexX,indexY,x,y);
        return false;
    }
}
