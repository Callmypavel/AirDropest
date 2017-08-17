package com.peace.airdropest.Entity.Base;

import com.peace.airdropest.Entity.Base.DrawObject;

/**
 * Created by ouyan on 2017/8/14.
 */

public class GameObject extends DrawObject {

    protected String tag;
    protected Coordinate currentCoordinate;
    protected float speed;
    protected float hitRadius;
    public static class Coordinate{
        public float indexX;
        public float indexY;
        public Coordinate(float indexX,float indexY){
            this.indexX = indexX;
            this.indexY = indexY;
        }
        public Coordinate getDeltaCoordinate(float deltaX,float deltaY){
            float indexX = this.indexX+deltaX;
            float indexY = this.indexY+deltaY;
            return new Coordinate(indexX,indexY);
        }

        @Override
        public String toString() {
            return "坐标:("+indexX+","+indexY+")";
        }
    }

    public float getHitRadius() {
        return hitRadius;
    }

    public void setHitRadius(float hitRadius) {
        this.hitRadius = hitRadius;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }



    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
