package com.peace.airdropest.Entity.Base;

import java.util.ArrayList;

/**
 * Created by ouyan on 2017/8/21.
 */

public class Geometry {
    public static class Vector {
        protected float x;
        protected float y;
        public static Vector upwardVector = new Vector(0,-1);
        public static Vector downwardVector = new Vector(0,1);
        public static Vector leftwardVector = new Vector(-1,0);
        public static Vector rightwardVector = new Vector(1,0);

        public Vector(){

        }

        public Vector(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getModule(){
            return (float) Math.sqrt(Math.pow(getX(),2)+Math.pow(getY(),2));
        }

        protected void normalize(){
            float length = getModule();
            setX(getX()/length);
            setY(getY()/length);
        }

        public Vector reverse(){
            setX(-getX());
            setY(-getY());
            return this;
        }
        public Vector getPerpendicularVector(){
            return new Vector(-getY(),getX());
        }


    }
    public static class Vector3 extends Vector{
        float z;
        public Vector3(float x, float y,float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }
    public static class Edge {
        Vector startPoint;
        Vector endPoint;

        public Edge(Vector startPoint, Vector endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        public Vector getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(Vector startPoint) {
            this.startPoint = startPoint;
        }

        public Vector getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(Vector endPoint) {
            this.endPoint = endPoint;
        }
    }
    public static class Polygon{
        ArrayList<Edge> edges;

        public ArrayList<Edge> getEdges() {
            return edges;
        }

        public void setEdges(ArrayList<Edge> edges) {
            this.edges = edges;
        }
    }
    public static class Cube extends Polygon{
        public Cube(float centreX,float centreY,float sideLength){
            edges = new ArrayList<>();
            edges.add(new Edge(new Vector(centreX-sideLength/2,centreY-sideLength/2),new Vector(centreX+sideLength/2,centreY-sideLength/2)));
            edges.add(new Edge(new Vector(centreX+sideLength/2,centreY-sideLength/2),new Vector(centreX+sideLength/2,centreY+sideLength/2)));
            edges.add(new Edge(new Vector(centreX+sideLength/2,centreY+sideLength/2),new Vector(centreX-sideLength/2,centreY+sideLength/2)));
            edges.add(new Edge(new Vector(centreX-sideLength/2,centreY+sideLength/2),new Vector(centreX-sideLength/2,centreY-sideLength/2)));
        }
    }
    public static class Rectangle extends Polygon{
        public float width;
        public float height;
        public float centreX;
        public float centreY;
        public Rectangle(float centreX,float centreY,float width,float height){
            this.width = width;
            this.height = height;
            this.centreX = centreX;
            this.centreY = centreY;
            edges = new ArrayList<>();
            edges.add(new Edge(new Vector(centreX-width/2,centreY-height/2),new Vector(centreX+width/2,centreY-height/2)));
            edges.add(new Edge(new Vector(centreX+width/2,centreY-height/2),new Vector(centreX+width/2,centreY+height/2)));
            edges.add(new Edge(new Vector(centreX+width/2,centreY+height/2),new Vector(centreX-width/2,centreY+height/2)));
            edges.add(new Edge(new Vector(centreX-width/2,centreY+height/2),new Vector(centreX-width/2,centreY-height/2)));
        }

    }

    public static class Axis{
        Vector originatePoint;
        Vector direction;

        public Axis(Vector originatePoint, Vector direction) {
            this.originatePoint = originatePoint;
            this.direction = direction;
        }

        public Vector getOriginatePoint() {
            return originatePoint;
        }

        public void setOriginatePoint(Vector originatePoint) {
            this.originatePoint = originatePoint;
        }

        public Vector getDirection() {
            return direction;
        }

        public void setDirection(Vector direction) {
            this.direction = direction;
        }
    }
}
