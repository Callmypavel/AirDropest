package com.peace.airdropest.Entity.Equipment;

import com.peace.airdropest.Tool.OneBitmapUtil;

/**
 * Created by ouyan on 2017/8/17.
 */

public class Weapon extends Equipment implements Cloneable{
    private int weaponType;
    private Magazine magazine;
    private String name;
    private int baseDamage;
    private int lastCdTime;
    private int cdTime;

    @Override
    public Object clone() {
        Weapon weapon = null;
        try{
            weapon = (Weapon)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        weapon.setMagazine((Magazine) weapon.getMagazine().clone());
        return weapon;
    }

    public Weapon(String name,int weaponType,int cdTime,int capacity,Bullet bullet){
        this.name = name;
        this.weaponType = weaponType;
        this.cdTime = cdTime;
        this.lastCdTime = -cdTime;
        this.setImage(OneBitmapUtil.getBitmapFromText(weaponType+"",100,100));
        magazine = new Magazine(weaponType,capacity,bullet);
    }

    public Bullet shootBullet(){
        return getMagazine().getBullet();
    }

    public int getCdTime() {
        return cdTime;
    }

    public void setCdTime(int cdTime) {
        this.cdTime = cdTime;
    }

    public int getLastCdTime() {
        return lastCdTime;
    }

    public void setLastCdTime(int lastCdTime) {
        this.lastCdTime = lastCdTime;
    }

    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
}
