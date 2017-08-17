package com.peace.airdropest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ouyan on 2017/8/14.
 */

public class Mission {

    private int missionState;
    private int height;
    private int gravity;
    public float deadlinePercent;
    public ArrayList<Enemy> enemies;
    public ArrayList<Enemy> availEnemies;
    private ArrayList<Bullet> airBullets;
    private ArrayList<Weapon> playerWeapons;
    private ArrayList<Blast> blasts;

    private int scores;
    private int hitCount = 0;
    private int killCount = 0;
    private int selectedWeaponIndex;



    @SuppressWarnings("unchecked")
    public Mission(){
        enemies = new ArrayList<>();
        for (int i=2;i<50;i++){
            Enemy enemy = new Enemy();
            enemy.setSpeed(1f);
            enemy.setBornCoordinate(new GameObject.Coordinate(i*enemy.getImage().getWidth(),0));
            enemy.setGenerateTime((i-1)*2000);
            enemies.add(enemy);
        }
        setEnemies(enemies);
        height = 20000;
        gravity = 10;
        deadlinePercent = 0.8f;
        copyWeaponsFromPlayer();
    }

    private void copyWeaponsFromPlayer(){
        Player player = Player.getInstance();
        playerWeapons = new ArrayList<>();
        for(Weapon weapon : player.getWeapons()){
            Weapon weapon1 = (Weapon) weapon.clone();
            LogTool.log(this,"检查对象复制"+weapon+","+weapon1);
            LogTool.log(this,"检查对象复制"+weapon.getMagazine()+","+weapon1.getMagazine());
            playerWeapons.add(weapon);
        }

    }


    public Weapon getCurrentSelectedWeapon(){
        return playerWeapons.get(selectedWeaponIndex);
    }




    public float getDeadlinePercent() {
        return deadlinePercent;
    }

    public void setDeadlinePercent(float deadlinePercent) {
        this.deadlinePercent = deadlinePercent;
    }

    public void switchWeapon(int index){
        selectedWeaponIndex = index;
    }

    public ArrayList<Bullet> getAirBullets() {
        if(airBullets==null){
            airBullets = new ArrayList<>();
        }
        return airBullets;
    }

    public ArrayList<Weapon> getPlayerWeapons() {
        if(playerWeapons==null){
            playerWeapons = new ArrayList<>();
        }
        return playerWeapons;
    }

    public ArrayList<Blast> getBlasts(){
        if(blasts==null){
            blasts = new ArrayList<>();
        }
        return blasts;
    }

    public void removeBlast(Blast blast){
        if(blasts!=null){
            blasts.remove(blast);
        }
    }

    public void addAirBullet(Bullet bullet) {
        if(airBullets==null){
            airBullets = new ArrayList<>();
        }
        airBullets.add(bullet);
    }

    public void addBlast(Blast blast) {
        if(blasts==null){
            blasts = new ArrayList<>();
        }
        blasts.add(blast);
    }

    public void removeBullet(Bullet bullet) {
        airBullets.remove(bullet);
    }


    public void killEnemy(Enemy enemy){
        availEnemies.remove(enemy);
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }


    public ArrayList<Enemy> getAvailEnemies() {
        if(availEnemies==null){
            availEnemies = new ArrayList<>();
        }
        return availEnemies;
    }
    public void addAvailEnemy(Enemy enemy) {
        if(availEnemies==null){
            availEnemies = new ArrayList<>();
        }
        availEnemies.add(enemy);
        enemies.remove(enemy);
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
        generateTimeArray();
    }

    public void addEnemies(Enemy gameObject){
        if(enemies!=null){
            enemies.add(gameObject);
        }
        generateTimeArray();
    }

    private void generateTimeArray(){
        if(enemies!=null){
            Collections.sort(enemies, new Comparator<Enemy>() {
                @Override
                public int compare(Enemy g1, Enemy g2) {
                    if(g1.getGenerateTime()<g2.getGenerateTime()){
                        return 1;
                    }else if(g1.getGenerateTime()>g2.getGenerateTime()){
                        return -1;
                    }else {
                        return 0;
                    }
                }
            });

        }

    }


    public int getMissionState(){
        return this.missionState;
    }

    public Enemy getLatestEnemy(){
        if(enemies.size()==0){
            return null ;
        }
        Enemy enemy = enemies.get(enemies.size()-1);
        return enemy;
    }
    public void removeLatestEnemy(Enemy enemy){
        enemies.remove(enemy);
    }


    public void setMissionState(int state){
        this.missionState = state;
    }


}
