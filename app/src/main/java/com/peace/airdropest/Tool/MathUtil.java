package com.peace.airdropest.Tool;


import com.peace.airdropest.Entity.Base.Geometry.*;
/**
 * Created by ouyan on 2017/8/21.
 */

public class MathUtil {

    public boolean collisionDetect(Polygon polygon1,Polygon polygon2){
        for(Edge edge : polygon1.getEdges()){
            Axis axis = getSeperateAxis(edge);
            Edge projectedEdge = getEdgeOnAxis(edge,axis);
            for(Edge edge1 : polygon2.getEdges()){
                Edge projectedEdge1 = getEdgeOnAxis(edge1,axis);
                if(isOverlay(projectedEdge,projectedEdge1)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOverlay(Edge edge,Edge edge1){
        if(edge1.getStartPoint().getX()<edge.getEndPoint().getX()
                ||edge1.getEndPoint().getX()>edge.getStartPoint().getX()){
            return true;
        }
        return false;
    }

    public Edge getEdgeOnAxis(Edge edge,Axis axis){
        Vector startVector = getVectorOnAxis(edge.getStartPoint(),axis);
        Vector endVector = getVectorOnAxis(edge.getEndPoint(),axis);
        if(startVector.getX()>endVector.getX()){
            return new Edge(endVector,startVector);
        }else {
            return new Edge(startVector,endVector);
        }

    }

    public Vector getVectorOnAxis(Vector point, Axis axis){
        Vector vector = axis.getDirection();
        return new Vector(vector.getX()*point.getX()+vector.getY()*point.getY(),0);
    }

    public static Vector getProjection(Vector vector1,Vector vector2){
        //vector1投影在vector2上
        float costheta = (vector1.getX()*vector2.getX()+vector1.getY()*vector2.getY())/(vector1.getModule()*vector2.getModule());
        Vector projection = new Vector(vector1.getX()*costheta,vector1.getY()*costheta);
        return projection;
    }

    public static Axis getSeperateAxis(Edge edge){
        Vector edgeVector = new Vector(edge.getEndPoint().getX()-edge.getStartPoint().getX(),edge.getEndPoint().getY()-edge.getStartPoint().getY());
        Axis axis = new Axis(new Vector(0,0),edgeVector.getPerpendicularVector());
        return axis;
    }

    public Vector getVectorFromEdge(Edge edge){
        return new Vector(edge.getEndPoint().getX()-edge.getStartPoint().getX(),edge.getEndPoint().getY()-edge.getStartPoint().getY());
    }



}
