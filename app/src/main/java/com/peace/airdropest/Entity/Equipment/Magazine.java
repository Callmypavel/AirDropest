package com.peace.airdropest.Entity.Equipment;

/**
 * Created by ouyan on 2017/8/17.
 */

public class Magazine extends Equipment implements Cloneable{
    private int magazineType;
    private Bullet bullet;
    private int capacity;

    @Override
    public Object clone() {
        Magazine magazine = null;
        try{
            magazine = (Magazine)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return magazine;
    }

    public Magazine(int magazineType,int capacity,Bullet bullet) {
        this.magazineType = magazineType;
        this.capacity = capacity;
        this.bullet = bullet;
    }

    public int getMagazineType() {
        return magazineType;
    }

    public void setMagazineType(int magazineType) {
        this.magazineType = magazineType;
    }

    public Bullet getBullet() {
        if(capacity>=1) {
            capacity-=1;
            return bullet;
        }else {
            return null;
        }
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
