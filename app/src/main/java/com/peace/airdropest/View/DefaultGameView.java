package com.peace.airdropest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Equipment.Blast;
import com.peace.airdropest.Entity.Equipment.Bullet;
import com.peace.airdropest.Entity.Character.Enemy;
import com.peace.airdropest.Entity.Equipment.Weapon;
import com.peace.airdropest.Entity.Mission.Building;
import com.peace.airdropest.Entity.Mission.Dialog;
import com.peace.airdropest.Logic.GameLogicService;
import com.peace.airdropest.OneApplication;
import com.peace.airdropest.Resource;
import com.peace.airdropest.Tool.LogTool;
import com.peace.airdropest.Tool.OneBitmapUtil;
import com.peace.airdropest.Tool.ViewTool;

/**
 * Created by ouyan on 2017/8/14.
 */

public class DefaultGameView extends BaseGameView {
    private Paint textPaint;
    private GameLogicService gameLogicService = GameLogicService.getInstance();
    protected int operateHeight;
    private int weaponNumber;
    private String message;
    public DefaultGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        textPaint = new Paint();
        textPaint.setTextSize(40);
        viewHeight = screenHeight;
        viewWidth = screenWidth;
        operateHeight = viewHeight-ViewTool.getPxFromDp(40);
        backgroundBitmap = OneBitmapUtil.getBitmapFromColor(viewWidth,viewHeight, Color.WHITE);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogTool.log(this,"销毁游戏画面");
        isDraw = false;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        updateGameView();
    }

    public void updateGameView(){
        //LogTool.log(this,"刷新游戏画面");
        Canvas canvas = holder.lockCanvas();
        doDraw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void initDynamicDrawing(){
        isDraw = true;
        backgroundBitmap = OneBitmapUtil.getBitmapFromColor(viewWidth,viewHeight, Color.WHITE);
        new Thread(){
            public void run() {
                //LogTool.log(this,"线程开始",false);
                while(isDraw){
                    //LogTool.log(this,"线程轮询",false);
                    try {
                        updateGameView();
                        Thread.sleep(50);
                    } catch (Exception e) {
                        LogTool.log(this,"定时线程出错");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void doDraw(Canvas canvas){
        //LogTool.log(this,"查看关卡状态"+gameLogicService.getMission().getMissionState());
        switch (gameLogicService.getMission().getMissionState()){
            case Resource.MissionState.MISSION_STARTED:
                //LogTool.log(this,"绘制游戏画面");
                drawBackGround(canvas);
                drawEnemies(canvas);
                drawBuildings(canvas);
                drawBullets(canvas);
                drawBlasts(canvas);
                drawUIs(canvas);
                break;
            case Resource.MissionState.MISSION_DIALOGING:
                //LogTool.log(this,"绘制对话");
                drawDialog(canvas);
                break;
        }

    }

    public void setMessage(String message){
        this.message = message;
    }

    private void drawDialog(Canvas canvas){
        Dialog dialog = gameLogicService.getMission().getDialogManager().getDialogToDisplay();
        if(dialog==null){
            return;
        }
        if(dialog.getBackgroundBitmap()!=null){
            backgroundBitmap = dialog.getBackgroundBitmap();
        }
        drawBackGround(canvas);
        String characterName = gameLogicService.getMission().getDialogManager().getCharacterRefence(dialog.getCharacterId());
        canvas.drawText(characterName,100,screenHeight-800,textPaint);
        canvas.drawText(dialog.getText(),100,screenHeight-700,textPaint);
        LogTool.log(this+"drawDialog()","当前对话"+characterName+":"+dialog.getText());
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
            canvas.drawText(""+enemy.getId()+enemy.getHealthPoint(),centreX-30,centreY,textPaint);

        }

    }
    private void drawBuildings(Canvas canvas){
        for (int i=0;i<gameLogicService.getMission().getBuildings().size();i++){
            Building building = gameLogicService.getMission().getBuildings().get(i);
            GameObject.Coordinate coordinate = building.getCurrentCoordinate();
            if(coordinate==null){
                gameLogicService.initGameObjectParams(building,screenWidth,screenHeight);
            }
            float centreX = building.getCurrentCoordinate().indexX;
            float centreY = building.getCurrentCoordinate().indexY;
            float drawStartX = centreX-building.getImage().getWidth()/2;
            float drawStartY = centreY-building.getImage().getHeight()/2;
            canvas.drawBitmap(building.getImage(),drawStartX,drawStartY,defaultPaint);

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
            canvas.drawText(weapon.getName(),screenWidth*1.f/weaponNumber*i,operateHeight,textPaint);
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
        switch (GameLogicService.getInstance().getMission().getMissionState()){
            case Resource.MissionState.MISSION_STARTED:
                if(y<=operateHeight) {
                    GameLogicService.getInstance().playerAttack(x, y);
                }else {
                    GameLogicService.getInstance().getMission().switchWeapon((int)x/(screenWidth/weaponNumber));
                }
                break;
            case Resource.MissionState.MISSION_DIALOGING:
                GameLogicService.getInstance().dialogTouch();
                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(viewWidth,viewHeight);
    }
}
