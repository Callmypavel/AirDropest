package com.peace.airdropest.Logic;

import com.peace.airdropest.Entity.Equipment.Blast;
import com.peace.airdropest.Entity.Equipment.Bullet;
import com.peace.airdropest.Entity.Character.Enemy;
import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Mission.Mission;
import com.peace.airdropest.Entity.Equipment.Weapon;
import com.peace.airdropest.Resource;
import com.peace.airdropest.Tool.LogTool;
import com.peace.airdropest.View.BaseGameView;

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
        if(timer!=null){
            timer.cancel();
        }
        timer=new Timer(true);
        if(timerTask!=null){
            timerTask.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                LogTool.log(this,"mission state"+mission.getMissionState(),false);
                if(mission.getMissionState()!= Resource.MissionState.MISSION_PAUSED){
                    currentTime += period;
                    //生成敌人
                    Enemy latestEnemy = mission.getLatestEnemy();
                    LogTool.log(this,"latest enemy"+currentTime+","+latestEnemy,false);
                    if(latestEnemy!=null){
                        if(currentTime>=latestEnemy.getGenerateTime()){
                            latestEnemy.setCurrentCoordinate(latestEnemy.getBornCoordinate());
                            //LogTool.log(this,"添加敌人："+currentTime+","+latestEnemy.getGenerateTime());
                            mission.addAvailEnemy(latestEnemy);
                        }
                    }

                    //操作敌人
                    if(mission.getAvailEnemies()!=null){
                        //LogTool.log(this,"战场敌人数量:"+mission.getAvailEnemies().size());
                        for(int i=0;i<mission.getAvailEnemies().size();i++){
                            Enemy enemy = mission.getAvailEnemies().get(i);
                            GameObject.Coordinate coordinate = enemy.getCurrentCoordinate();
                            switch (enemy.getActionMode()){
                                case Resource.ActionMode.ACTION_ENEMY_DEFAULT:
                                    coordinate.indexY += enemy.getSpeed();
                                    //LogTool.log(this,"change indexY"+coordinate.indexY,false);
                                    LogTool.log(this,"change enemy"+enemy,false);
                                    break;
                            }
                            if(coordinate.indexY>= Resource.ViewConfig.SCREEN_WIDTH*mission.getDeadlinePercent()){
                                cancelMission();
                                gameEventListener.OnGameOvered();
                            }
                        }

                    }

                    //操作空中的子弹
                    if(mission.getAirBullets()!=null){
                        for (int i=0;i<mission.getAirBullets().size();i++) {
                            Bullet bullet = mission.getAirBullets().get(i);
                            float newHeight = bullet.getCurrentHeight() - bullet.getSpeed() * period;
                            if (newHeight <= 0) {
                                LogTool.log(this, "漆黑子弹" + bullet);
                                hit(bullet);
                            } else {
                                bullet.setCurrentHeight(newHeight);
                                bullet.setSpeed(bullet.getSpeed() + mission.getGravity()*period*1.f/1000);
                                //LogTool.log(this, "坠落速度" + bullet.getSpeed());
                               // LogTool.log(this, "坠落高度" + newHeight);
                            }

                        }
                    }

                    //操作爆炸冲击波
                    if(mission.getBlasts()!=null){
                        for (int i=0;i<mission.getBlasts().size();i++) {
                            Blast blast = mission.getBlasts().get(i);
                            if(currentTime>blast.getBornTime()+blast.getPeriod()){
                                mission.removeBlast(blast);
                            }

                        }
                    }

                    gameView.postInvalidate();
                }

            }
        };
        mission.setMissionState(Resource.MissionState.MISSION_STARTED);
        timer.schedule(timerTask,0,period);

    }

    private void cleadAllResource(){

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


    private void hit(Bullet bullet){
        for(int i=0;i<mission.getAvailEnemies().size();i++){
            Enemy enemy = mission.getAvailEnemies().get(i);
            if(hitDetect(enemy,bullet)) {
                //击中判定
                int hp = enemy.getHealthPoint()-bullet.getDamage();
                if(hp<=0){
                    mission.setScores(mission.getScores()+enemy.getBaseScore());
                    mission.setKillCount(mission.getKillCount()+1);
                    mission.killEnemy(enemy);
                    mission.setScores(mission.getScores()+enemy.getBaseScore());
                    gameEventListener.OnEnemyKilled();
                }else {
                    enemy.setHealthPoint(hp);
                }

            }
            bullet.getBlast().setBornTime(currentTime);
            mission.addBlast(bullet.getBlast());
            mission.removeBullet(bullet);

        }

    }

    private boolean hitDetect(GameObject gameObject,Bullet bullet){

        double d1 = Math.pow(gameObject.getCurrentCoordinate().indexX-bullet.getCurrentCoordinate().indexX,2);
        double d2 = Math.pow(gameObject.getCurrentCoordinate().indexY-bullet.getCurrentCoordinate().indexY,2);
        double distance = Math.sqrt(d1+d2);
        if(distance<=gameObject.getHitRadius()+bullet.getHitRadius()){
            LogTool.log(this,"查看判定:"+distance+","+gameObject.getHitRadius()+","+bullet.getHitRadius()+"hp"+((Enemy)gameObject).getHealthPoint());
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
        cleadAllResource();
    }

    public void setGameEventListener(GameEventListener listener,BaseGameView baseGameView){
        GameLogicService.gameEventListener = listener;
        this.gameView = baseGameView;
    }


}
