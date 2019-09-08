package com.peace.airdropest.Entity.Character;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Base.Geometry;
import com.peace.airdropest.Resource;

import java.util.ArrayList;

/**
 * Created by ouyan on 2017/8/15.
 */

public class Enemy extends GameObject {
    protected int actionMode;
    private int generateTime;
    private Coordinate bornCoordinate;
    private int healthPoint;
    private int baseScore;
    private int baseExp;
    private int vision;
    private float maxSpeedRatio;
    private Geometry.Vector direction;
    private ArrayList<GameObject> visionObjects;
    private GameObject currentTarget;
    private int currentBehaviour;
    @Override
    public String toString() {
        return "出生坐标:"+bornCoordinate.toString();
    }

    public Enemy(){
        baseScore=10;
        healthPoint = 100;
        image = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.parseColor("#FF0000"));
        hitRadius = 50;
        actionMode = Resource.ActionMode.ACTION_ENEMY_DEFAULT;
        baseSpeed = 0.03f;
        speed = baseSpeed;
        vision = 200;
        maxSpeedRatio = 1.f;
        bornCoordinate = new Coordinate(Resource.ViewConfig.DEFAULT_HORIZONTAL_NUM/2,0);
        visionObjects = new ArrayList<>();
        direction = Geometry.Vector.downwardVector;
    }

    public Enemy(int actionMode, int generateTime, Coordinate bornCoordinate, int healthPoint, int baseScore, int baseExp) {
        this.actionMode = actionMode;
        this.generateTime = generateTime;
        this.bornCoordinate = bornCoordinate;
        this.healthPoint = healthPoint;
        this.baseScore = baseScore;
        this.baseExp = baseExp;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(int baseExp) {
        this.baseExp = baseExp;
    }

    public Coordinate getBornCoordinate() {
        return bornCoordinate;
    }

    public void setBornCoordinate(Coordinate bornCoordinate) {
        this.bornCoordinate = bornCoordinate;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getActionMode() {
        return actionMode;
    }

    public void setActionMode(int actionMode) {
        this.actionMode = actionMode;
    }

    public int getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(int generateTime) {
        this.generateTime = generateTime;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public ArrayList<GameObject> getVisionObjects() {
        return visionObjects;
    }

    public void setVisionObjects(ArrayList<GameObject> visionObjects) {
        this.visionObjects = visionObjects;
    }

    public GameObject getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(GameObject currentTarget) {
        this.currentTarget = currentTarget;
    }

    public int getCurrentBehaviour() {
        return currentBehaviour;
    }

    public void setCurrentBehaviour(int currentBehaviour) {
        this.currentBehaviour = currentBehaviour;
    }

    public Geometry.Vector getDirection() {
        return direction;
    }

    public void setDirection(Geometry.Vector direction) {
        this.direction = direction;
    }

    public float getMaxSpeedRatio() {
        return maxSpeedRatio;
    }

    public void setMaxSpeedRatio(float maxSpeedRatio) {
        this.maxSpeedRatio = maxSpeedRatio;
    }
}
