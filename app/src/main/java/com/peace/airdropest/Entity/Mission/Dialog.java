package com.peace.airdropest.Entity.Mission;

import android.graphics.Bitmap;

/**
 * Created by peace on 2017/9/19.
 */

public class Dialog {
    private int characterId;
    private String text;
    private Bitmap backgroundBitmap;

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Dialog(String text,int characterId) {
        this.characterId = characterId;
        this.text = text;
    }
    public Dialog(String text,int characterId,Bitmap backgroundBitmap) {
        this.characterId = characterId;
        this.text = text;
        this.backgroundBitmap = backgroundBitmap;
    }

    public Bitmap getBackgroundBitmap() {
        return backgroundBitmap;
    }
}
