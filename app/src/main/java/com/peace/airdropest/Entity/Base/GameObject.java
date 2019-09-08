package com.peace.airdropest.Entity.Base;

/**
 * Created by ouyan on 2017/8/14.
 */

public class GameObject extends DrawObject {

    protected String tag;
    protected String id;
    protected Coordinate currentCoordinate;
    protected Coordinate percentCoordinate;
    protected float baseSpeed;
    protected float speed;
    protected float hitRadius;
    //以占屏幕百分比的方式定义长和宽
    protected float heightPercent;
    protected float widthPercent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Coordinate{
        public float indexX;
        public float indexY;
        public Coordinate(String indexX,String indexY){
            this.indexX = Float.parseFloat(indexX)/100;
            this.indexY = Float.parseFloat(indexY)/100;
        }
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

    public Coordinate getPercentCoordinate() {
        return percentCoordinate;
    }

    public void setPercentCoordinate(Coordinate percentCoordinate) {
        this.percentCoordinate = percentCoordinate;
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

    public float getHeightPercent() {
        return heightPercent;
    }

    public void setHeightPercent(int heightPercent) {
        this.heightPercent = heightPercent;

    }

    public float getWidthPercent() {
        return widthPercent;
    }

    public void setWidthPercent(int widthPercent) {
        this.widthPercent = widthPercent;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Geometry.Vector getDirectionToTarget(GameObject target){
        Geometry.Vector vector = new Geometry.Vector();
        vector.setX(target.getCurrentCoordinate().indexX-getCurrentCoordinate().indexX);
        vector.setY(target.getCurrentCoordinate().indexY-getCurrentCoordinate().indexY);
        vector.normalize();
        return vector;
    }

}
