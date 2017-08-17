package com.peace.airdropest.Entity.Equipment;

import android.graphics.Bitmap;

import com.peace.airdropest.Entity.Base.GameObject;

/**
 * Created by ouyan on 2017/8/16.
 */

public class Blast extends GameObject {
    private int bornTime;
    private int period;

    public int getBornTime() {
        return bornTime;
    }

    public void setBornTime(int bornTime) {
        this.bornTime = bornTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Blast(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
