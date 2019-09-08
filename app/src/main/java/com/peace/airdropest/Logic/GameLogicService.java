package com.peace.airdropest.Logic;

import com.peace.airdropest.Entity.Base.Geometry;
import com.peace.airdropest.Entity.Base.QuadTree;
import com.peace.airdropest.Entity.Equipment.Blast;
import com.peace.airdropest.Entity.Equipment.Bullet;
import com.peace.airdropest.Entity.Character.Enemy;
import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Mission.Mission;
import com.peace.airdropest.Entity.Equipment.Weapon;
import com.peace.airdropest.OneApplication;
import com.peace.airdropest.Resource;
import com.peace.airdropest.Tool.LogTool;
import com.peace.airdropest.Tool.OneBitmapUtil;
import com.peace.airdropest.Tool.StringUtil;
import com.peace.airdropest.View.BaseGameView;
import com.peace.airdropest.View.DefaultGameView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ouyan on 2017/8/14.
 */

public class GameLogicService {
    private GameLogicHandler gameLogicHandler;
    private static GameLogicService mInstance;
    private static GameEventListener gameEventListener;
    private Mission mission;
    private BaseGameView gameView;
    private Timer timer;
    private TimerTask timerTask;
    private int period = 50;
    private int currentTime = 0;

    interface GameLogicHandler {
        void startGame();
    }
    public interface GameEventListener{
        void OnGameStarted();
        void OnBulletCooling(String weaponName);
        void OnBulletShort(String weaponName);
        void OnEnemyKilled();
        void OnGameOvered();
    }
    private GameLogicService(){

    }
    public static GameLogicService getInstance(){
        if(mInstance == null){
            synchronized (GameLogicService.class){
                if(mInstance == null){
                    mInstance = new GameLogicService();
                }
            }
        }
        return mInstance;
    }

    public void startGame(Mission mission){
        this.mission = mission;
        startMission();
        gameEventListener.OnGameStarted();
    }

    public void startMission(){
        if(mission.getDialogManager().getNextDialog()==null){
            startBattle();
        }else {

        }

    }

    private void startBattle(){
        mission.setMissionState(Resource.MissionState.MISSION_STARTED);
        if(timer!=null){
            timer.cancel();
        }
        timer=new Timer(true);
        if(timerTask!=null){
            timerTask.cancel();
        }
        final AIController aiController = new AIController(mission);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                LogTool.log(this,"mission state"+mission.getMissionState(),false);
                switch (mission.getMissionState()){
                    case Resource.MissionState.MISSION_STARTED:
                        currentTime += period;
                        //生成敌人
                        generateObjects();

                        //操作敌人
                        for (Enemy enemy : mission.getAvailEnemies()){
                            enemy.getVisionObjects().clear();
                        }
                        //操作空中的子弹
                        if(mission.getAirBullets()!=null){
                            for (int i=0;i<mission.getAirBullets().size();i++) {
                                Bullet bullet = mission.getAirBullets().get(i);
                                float newHeight = bullet.getCurrentHeight() - bullet.getSpeed() * period;

                                //LogTool.log(this, "高度测量中" + newHeight);
                                bullet.setCurrentHeight(newHeight);
                                bullet.setSpeed(bullet.getSpeed() + mission.getGravity()*period*1.f/1000);
                                hit(bullet,(int)newHeight);
//                                if (newHeight <= 0) {
//                                    //LogTool.log(this, "漆黑子弹" + bullet);
//
//                                } else {
//
//                                    //LogTool.log(this, "坠落速度" + bullet.getSpeed());
//                                    // LogTool.log(this, "坠落高度" + newHeight);
//                                }

                            }
                        }

                        aiController.operateAIs(period);

                        //操作爆炸冲击波
                        if(mission.getBlasts()!=null){
                            for (int i=0;i<mission.getBlasts().size();i++) {
                                Blast blast = mission.getBlasts().get(i);
                                if(currentTime>blast.getBornTime()+blast.getPeriod()){
                                    mission.removeBlast(blast);
                                }

                            }
                        }

//                        //计算碰撞
//                        QuadTree quadTree = new QuadTree<>( mission.getAvailEnemies(),5,5,new Geometry.Rectangle(
//                                OneApplication.screenWidth/2,//centreX
//                                OneApplication.screenHeight/2,//centreY
//                                OneApplication.screenWidth,
//                                OneApplication.screenHeight
//                        ));
//                        quadTree.setProbablyHitListener(new QuadTree.ProbablyHitListener() {
//                            @Override
//                            public void onProbablyHit(ArrayList<GameObject> gameObjects) {
//                                //LogTool.log(this,"可能碰撞的物体数目："+gameObjects.size());
//                                //LogTool.log(this,"他们的名字是:"+ StringUtil.getNames(gameObjects));
//                            }
//                        });
//                        quadTree.getProbable();


                        //判断结束条件
                        for(int i=0;i<mission.getAvailEnemies().size();i++){
                            Enemy enemy = mission.getAvailEnemies().get(i);
                            GameObject.Coordinate coordinate = enemy.getCurrentCoordinate();
                            if(coordinate.indexY>=gameView.getViewHeight()*mission.getDeadlinePercent()){
                                cancelMission();
                                gameEventListener.OnGameOvered();
                            }
                        }
                        break;
                    case Resource.MissionState.MISSION_DIALOGING:

                        break;
                }
                gameView.postInvalidate();


            }
        };
        //mission.setMissionState(Resource.MissionState.MISSION_STARTED);
        timer.schedule(timerTask,0,period);
        ((DefaultGameView)gameView).initDynamicDrawing();
    }

    private void generateObjects(){
        Enemy latestEnemy = mission.getLatestEnemy();
        LogTool.log(this,"latest enemy"+currentTime+","+latestEnemy,false);
        if(latestEnemy!=null){
            if(currentTime>=latestEnemy.getGenerateTime()){
                latestEnemy.setCurrentCoordinate(latestEnemy.getBornCoordinate());
                //LogTool.log(this,"添加敌人："+currentTime+","+latestEnemy.getGenerateTime());
                mission.addAvailEnemy(latestEnemy);
            }
        }
    }
    public void initGameObjectParams(GameObject gameObject,int screenWidth,int screenHeight){
        GameObject.Coordinate percentCoordinate = gameObject.getPercentCoordinate();
        gameObject.setCurrentCoordinate(new GameObject.Coordinate(screenWidth*percentCoordinate.indexX,screenHeight*percentCoordinate.indexY));
        gameObject.setHitRadius(screenWidth*gameObject.getWidthPercent());
        gameObject.setImage(OneBitmapUtil.zoomImg(gameObject.getImage(),(int)(screenWidth*gameObject.getWidthPercent()),(int)(screenHeight*gameObject.getHeightPercent())));
    }

    private void clearAllResource(){

    }

    public int getRemainCdTime(Weapon weapon){
        int cdRemainTime = weapon.getCdTime()-(currentTime-weapon.getLastCdTime());
        if(cdRemainTime>0){
            return weapon.getCdTime()-(currentTime-weapon.getLastCdTime());
        }else {
            return 0;
        }

    }

    public Mission getMission(){
        return this.mission;
    }
    public void dialogTouch(){
        if(mission.getDialogManager().getNextDialog()==null){
            //对话播放完毕
            startBattle();
        }else {
            ((DefaultGameView) gameView).updateGameView();
        }
    }

    public void playerAttack(float indexX,float indexY){
        if(mission.getMissionState()!=Resource.MissionState.MISSION_PAUSED){
            Weapon weapon = mission.getCurrentSelectedWeapon();
            Bullet bullet = weapon.shootBullet();
            if(bullet!=null){
                if(currentTime>weapon.getLastCdTime()+weapon.getCdTime()){
                    dropBullets(bullet,indexX,indexY);
                    weapon.setLastCdTime(currentTime);
                }else {
                    gameEventListener.OnBulletCooling(weapon.getName());
                }
            }else {
                gameEventListener.OnBulletShort(weapon.getName());
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void dropBullets(Bullet bullet,float indexX,float indexY){
        LogTool.log(this,"查看投弹坐标:"+indexX+","+indexY,false);
        Bullet realBullet = Bullet.getRealBullet(bullet);
        realBullet.setCurrentCoordinate(new GameObject.Coordinate(indexX,indexY));
        realBullet.setCurrentHeight(mission.getHeight());
        mission.addAirBullet(realBullet);
    }

    public static int probablyHit(Enemy enemy,Bullet bullet,Mission mission){
        int d1 = (int)Math.pow(enemy.getCurrentCoordinate().indexX-bullet.getCurrentCoordinate().indexX,2);
        int d2 = (int)Math.pow(enemy.getCurrentCoordinate().indexY-bullet.getCurrentCoordinate().indexY,2);
        int distance = (int)Math.sqrt(d1+d2);
        if(distance<=enemy.getHitRadius()+bullet.getHitRadius()){
            //平面上可能击中
            int timeToEscape = bullet.remainTime(mission.getGravity());
            return timeToEscape;
        }
        return -1;
    }



    private void hit(Bullet bullet,int newHeight){
        for(int i=0;i<mission.getAvailEnemies().size();i++){
            Enemy enemy = mission.getAvailEnemies().get(i);
            int d1 = (int)Math.pow(enemy.getCurrentCoordinate().indexX-bullet.getCurrentCoordinate().indexX,2);
            int d2 = (int)Math.pow(enemy.getCurrentCoordinate().indexY-bullet.getCurrentCoordinate().indexY,2);
            int distance = (int)Math.sqrt(d1+d2);
            if(enterVision(enemy,bullet,distance)) {
                enemy.getVisionObjects().add(bullet);
                if(newHeight<=0) {
                    if (hitDetect(enemy, bullet, distance)) {
                        //击中判定
                        int hp = enemy.getHealthPoint() - bullet.getDamage();
                        if (hp <= 0) {
                            mission.setScores(mission.getScores() + enemy.getBaseScore());
                            mission.setKillCount(mission.getKillCount() + 1);
                            mission.killEnemy(enemy);
                            mission.setScores(mission.getScores() + enemy.getBaseScore());
                            gameEventListener.OnEnemyKilled();
                        } else {
                            enemy.setHealthPoint(hp);
                        }

                    }
                    bullet.getBlast().setBornTime(currentTime);
                    mission.addBlast(bullet.getBlast());
                    mission.removeBullet(bullet);

                }
            }


        }

    }

    private boolean enterVision(Enemy enemy,Bullet bullet,int distance){
        if(distance<=enemy.getHitRadius()+enemy.getVision()+bullet.getHitRadius()){

            //LogTool.log(this,"查看判定:"+distance+","+enemy.getHitRadius()+","+bullet.getHitRadius()+",视线"+enemy.getVision());
            return true;
        }
        return false;
    }

    private boolean hitDetect(GameObject gameObject,Bullet bullet,int distance){
        if(distance<=gameObject.getHitRadius()+bullet.getHitRadius()){
            //LogTool.log(this,"查看判定:"+distance+","+gameObject.getHitRadius()+","+bullet.getHitRadius()+"hp"+((Enemy)gameObject).getHealthPoint());
            return true;
        }
        return false;
    }


    public int getMissionState(){
        return mission.getMissionState();
    }

    public void pauseMission(){
        mission.setMissionState(Resource.MissionState.MISSION_PAUSED);
    }

    public void resumeMission(){
        mission.setMissionState(Resource.MissionState.MISSION_STARTED);
    }

    public void cancelMission(){
        currentTime = 0;
        if(timer!=null){
            timer.cancel();
        }
        timer = null;
        if(timerTask!=null){
            timerTask.cancel();
        }
        timerTask = null;
        LogTool.log(this,"计时停止");
        mission.setMissionState(Resource.MissionState.MISSION_PAUSED);
        clearAllResource();
    }

    public void setGameEventListener(GameEventListener listener,BaseGameView baseGameView){
        GameLogicService.gameEventListener = listener;
        this.gameView = baseGameView;
    }


}
