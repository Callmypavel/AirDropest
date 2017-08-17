package com.peace.airdropest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.peace.airdropest.Entity.Equipment.Blast;
import com.peace.airdropest.Entity.Equipment.Bullet;
import com.peace.airdropest.Entity.Character.Enemy;
import com.peace.airdropest.Entity.Equipment.Weapon;
import com.peace.airdropest.Logic.GameLogicService;

/**
 * Created by ouyan on 2017/8/14.
 */

public class DefaultGameView extends BaseGameView {
    private Paint textPaint;
    private GameLogicService gameLogicService = GameLogicService.getInstance();
    private int operateHeight;
    private int weaponNumber;
    private String message;
    public DefaultGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        textPaint = new Paint();
        textPaint.setTextSize(40);
        operateHeight = screenWidth-200;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackGround(canvas);
        drawEnemies(canvas);
        drawBullets(canvas);
        drawBlasts(canvas);
        drawUIs(canvas);
    }

    public void setMessage(String message){
        this.message = message;
    }

    private void drawBackGround(Canvas canvas){
        if(backgroundBitmap!=null){
            canvas.drawBitmap(backgroundBitmap,0,0,defaultPaint);
        }
    }

    private void drawEnemies(Canvas canvas){
        for (int i=0;i<gameLogicService.getMission().getAvailEnemies().size();i++){
            Enemy enemy = gameLogicService.getMission().getAvailEnemies().get(i);

            float centreX = enemy.getCurrentCoordinate().indexX;
            float centreY = enemy.getCurrentCoordinate().indexY;
            float drawStartX = centreX-enemy.getImage().getWidth()/2;
            float drawStartY = centreY-enemy.getImage().getHeight()/2;
            canvas.drawBitmap(enemy.getImage(),drawStartX,drawStartY,defaultPaint);
            //canvas.drawLine(centreX, centreY,centreX+80,centreY, defaultPaint);
            canvas.drawText(""+enemy.getHealthPoint(),centreX-30,centreY,textPaint);

        }

    }

    private void drawBullets(Canvas canvas){
        for (int i=0;i<GameLogicService.getInstance().getMission().getAirBullets().size();i++) {

            Bullet bullet = gameLogicService.getMission().getAirBullets().get(i);
            float centreX = bullet.getCurrentCoordinate().indexX;
            float centreY = bullet.getCurrentCoordinate().indexY;
            float drawStartX = centreX-bullet.getImage().getWidth()/2;
            float drawStartY = centreY-bullet.getImage().getHeight()/2;

            canvas.drawBitmap(bullet.getImage(), drawStartX, drawStartY, defaultPaint);
            canvas.drawLine(centreX, centreY,centreX+80,centreY, defaultPaint);
            canvas.drawText(bullet.getCurrentHeight()+"m",centreX+80,centreY,textPaint);
        }
    }


    private void drawBlasts(Canvas canvas){
        for (int i=0;i<gameLogicService.getMission().getBlasts().size();i++){
            Blast blast = gameLogicService.getMission().getBlasts().get(i);
            float centreX = blast.getCurrentCoordinate().indexX;
            float centreY = blast.getCurrentCoordinate().indexY;
            float drawStartX = centreX-blast.getImage().getWidth()/2;
            float drawStartY = centreY-blast.getImage().getHeight()/2;
            canvas.drawBitmap(blast.getImage(),drawStartX,drawStartY,defaultPaint);
            canvas.drawText("Boom",centreX-30,centreY,textPaint);
        }
    }

    private void drawUIs(Canvas canvas){
        weaponNumber = gameLogicService.getMission().getPlayerWeapons().size();
        for (int i=0;i<weaponNumber;i++){
            Weapon weapon = gameLogicService.getMission().getPlayerWeapons().get(i);
            canvas.drawText(weapon.getName(),screenWidth*1.f/weaponNumber*i,screenWidth,textPaint);
            int remainCdTime = gameLogicService.getRemainCdTime(weapon);
            if(remainCdTime!=0){
                canvas.drawText(gameLogicService.getRemainCdTime(weapon)+"ms",screenWidth*1.f/weaponNumber*i,screenWidth+100,textPaint);
            }

        }
        canvas.drawText("当前选择的武器是:"+gameLogicService.getMission().getCurrentSelectedWeapon().getName(),0,200,textPaint);
        if(message!=null){
            canvas.drawText(message,0,300,textPaint);
        }

        canvas.drawText("得分:"+gameLogicService.getMission().getScores(),0,100,textPaint);
        canvas.drawText("击杀总数:"+gameLogicService.getMission().getKillCount(),400,100,textPaint);
        canvas.drawText("剩余子弹:"+gameLogicService.getMission().getCurrentSelectedWeapon().getMagazine().getCapacity(),800,100,textPaint);
    }

    @Override
    protected void handleAttributes(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onTouchCoordinate(int indexX, int indexY,float x,float y) {
        if(y<=operateHeight) {
            GameLogicService.getInstance().playerAttack(x, y);
        }else {
            GameLogicService.getInstance().getMission().switchWeapon((int)x/(screenWidth/weaponNumber));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(screenWidth,screenHeight-500);
    }
}
