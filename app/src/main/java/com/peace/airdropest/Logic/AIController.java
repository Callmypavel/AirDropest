package com.peace.airdropest.Logic;

import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Base.Geometry;
import com.peace.airdropest.Entity.Character.Behaviour;
import com.peace.airdropest.Entity.Character.Enemy;
import com.peace.airdropest.Entity.Equipment.Bullet;
import com.peace.airdropest.Entity.Mission.Building;
import com.peace.airdropest.Entity.Mission.Mission;
import com.peace.airdropest.Resource;
import com.peace.airdropest.Tool.LogTool;

import java.util.ArrayList;

/**
 * Created by peace on 2018/3/19.
 */

public class AIController {
    private Mission mission;

    public AIController(Mission mission) {
        this.mission = mission;
    }

    public void operateAIs(int deltaTime){
        //LogTool.log(this,"operate AIS");
        if(mission.getAvailEnemies()!=null){
            //LogTool.log(this,"战场敌人数量:"+mission.getAvailEnemies().size());
            for(int i=0;i<mission.getAvailEnemies().size();i++){
                Enemy enemy = mission.getAvailEnemies().get(i);
                GameObject.Coordinate coordinate = enemy.getCurrentCoordinate();
                switch (enemy.getActionMode()){
                    case Resource.ActionMode.ACTION_ENEMY_DEFAULT:
                        coordinate.indexY += enemy.getSpeed();
                        //LogTool.log(this,"change indexY"+coordinate.indexY,false);
                        //LogTool.log(this,"change enemy"+enemy,false);
                        break;
                    case Resource.ActionMode.ACTION_ENEMY_ELITE:
                        ArrayList<Bullet> bullets = new ArrayList<>();
                        for (int j = 0; j < enemy.getVisionObjects().size(); j++) {
                            GameObject gameObject = enemy.getVisionObjects().get(j);
                            if(gameObject instanceof Bullet){
                               bullets.add((Bullet) gameObject);
                            }
                        }
                        for (int j = 0; j < bullets.size(); j++) {
                            //遍历视野中的子弹
                            //LogTool.log(this, enemy.getId() + "看到了"+bullets.get(0).getCurrentHeight());
                            Bullet bullet = bullets.get(j);
                            if (bullet==null){
                                bullets.remove(bullet);
                            }
                            int remainTime = GameLogicService.probablyHit(enemy,bullet,mission);
                            if(remainTime==-1){
                                //暂时不会被击中
                            }else {
                                //避开
                                if(enemy.getCurrentBehaviour()!=Behaviour.AVOIDIND) {

                                    enemy.setDirection(enemy.getDirectionToTarget(bullet).reverse());
                                    enemy.setSpeed(enemy.getSpeed() * enemy.getMaxSpeedRatio());
                                    enemy.setCurrentBehaviour(Behaviour.AVOIDIND);
                                }
                            }
                        }
                        if(bullets.size()==0){
                            //重置状态
                            //LogTool.log(this,enemy.getId()+"补正");
                            enemy.setDirection(Geometry.Vector.downwardVector);
                            enemy.setSpeed(enemy.getBaseSpeed());
                            enemy.setCurrentBehaviour(Behaviour.RUSHING);
                        }

                        break;
                }
                enemy.setCurrentCoordinate(enemy.getCurrentCoordinate().getDeltaCoordinate(enemy.getDirection().getX()*enemy.getSpeed()*deltaTime,enemy.getDirection().getY()*enemy.getSpeed()*deltaTime));

            }

        }
    }

}
