package com.peace.airdropest;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by ouyan on 2017/8/15.
 */

public class Bullet extends GameObject{
    private int type;

    private int damage;

    private int baseDamege;
    private float currentHeight;
    private Blast blast;


    @Override
    public String toString() {
        String content = "coordinate:"+getCurrentCoordinate().toString();
        return content;
    }

    public Bullet(int baseDamege){
        image = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.parseColor("#00FF00"));
        hitRadius = 100;

        damage = baseDamege;
        Bitmap image1 = Bitmap.createBitmap(200, 200,
                Bitmap.Config.ARGB_8888);
        image1.eraseColor(Color.parseColor("#0000FF"));
        blast = new Blast(image1);
        blast.setPeriod(500);
        blast.setHitRadius(100);

    }

    public Bullet(){
        this(10);

    }

    public Bullet(int type,int baseDamege, float currentHeight, Blast blast) {
        this.type = type;
        this.damage = baseDamege;
        this.baseDamege = baseDamege;
        this.currentHeight = currentHeight;
        this.blast = blast;
    }

    @Override
    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        super.setCurrentCoordinate(currentCoordinate);
        blast.setCurrentCoordinate(currentCoordinate);
    }

    public Blast getBlast() {
        return blast;
    }

    public void setBlast(Blast blast) {
        this.blast = blast;
    }

    public static Bullet getRealBullet(Bullet bullet){
        Bullet bullet1 = new Bullet();
        bullet1.setImage(bullet.getImage());
        bullet1.setType(bullet.getType());
        bullet1.setDamage(bullet.getDamage());
        bullet1.setBaseDamege(bullet.getBaseDamege());
        return bullet1;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(float currentHeight) {
        this.currentHeight = currentHeight;
    }


    public int getBaseDamege() {
        return baseDamege;
    }

    public void setBaseDamege(int baseDamege) {
        this.baseDamege = baseDamege;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
