package com.peace.airdropest;

import java.util.ArrayList;

/**
 * Created by ouyan on 2017/8/15.
 */

public class Player {
    private static Player mInstance;
    private ArrayList<Weapon> weapons;

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    private Player(){
        weapons = new ArrayList<>();
        weapons.add(new Weapon("火神加特林",1,1000,1000,new Bullet(10)));
        weapons.add(new Weapon("乌尔班攻城巨炮",2,10000,500,new Bullet(20)));
        weapons.add(new Weapon("激光狂热者",3,20000,250,new Bullet(40)));
    }

    public static Player getInstance(){
        if(mInstance == null){
            synchronized (GameLogicService.class){
                if(mInstance == null){
                    mInstance = new Player();
                }
            }
        }
        return mInstance;
    }




}
