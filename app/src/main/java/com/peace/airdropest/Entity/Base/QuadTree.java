package com.peace.airdropest.Entity.Base;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;

import com.peace.airdropest.Entity.Base.Geometry.Rectangle;
import com.peace.airdropest.Tool.LogTool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by peace on 2017/9/14.
 */

public class QuadTree<T extends GameObject> {
    public QuadTree[] childQuadTrees;
    public Rectangle bound;
    private ArrayList<T> allGameObjects;
    private ArrayList<T> gameObjects;
    private int currentDepth;
    private int maxDepth;
    private int maxUnitNumber;
    private Boolean isLeaf;
    public interface ProbablyHitListener{
        void onProbablyHit(ArrayList<GameObject> gameObjects);
    }
    private ProbablyHitListener probablyHitListener;

    public void setProbablyHitListener(ProbablyHitListener probablyHitListener) {
        this.probablyHitListener = probablyHitListener;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public QuadTree(int maxDepth, int unitNumber,Rectangle bound){
        gameObjects = new ArrayList<>();
        this.maxDepth = maxDepth;
        this.maxUnitNumber = unitNumber;
        childQuadTrees = new QuadTree[4];
        this.bound = bound;
    }

    public QuadTree(ArrayList<T> allGameObjects, int maxDepth, int unitNumber,Rectangle bound){
        this.allGameObjects = allGameObjects;
        this.maxDepth = maxDepth;
        this.maxUnitNumber = unitNumber;
        childQuadTrees = new QuadTree[4];
        this.bound = bound;
        initQuadTree();
    }

    private void initQuadTree(){
        gameObjects = allGameObjects;
        createNewLayer(this);
        for (int i = 0; i < gameObjects.size(); i++) {
            T gameObject =  gameObjects.get(0);
            addChild(gameObject,this);
        }

    }

    public void getProbable(){
        //ArrayList<ArrayList<? extends GameObject>> collections = new ArrayList();
        traversal(this);
    }

    private void traversal(QuadTree quadTree){
        boolean isLeaf = true;//判断是否叶子结点
        for (int i = 0; i < 4; i++) {
            if(quadTree.childQuadTrees[i]!=null){
                traversal(quadTree.childQuadTrees[i]);
                //collections.add(quadTree.gameObjects);
                isLeaf = false;
            }
        }
        if(isLeaf){
            //叶子结点停止递归
            //collections.add(quadTree.gameObjects);
            if (probablyHitListener!=null){
                probablyHitListener.onProbablyHit(quadTree.gameObjects);
            }
            return ;
        }


    }

    private void addChild(T gameObject,QuadTree quadTree){
        if(quadTree.gameObjects.size()+1>maxUnitNumber){
            //LogTool.log(this,"本层树已满");
            if(currentDepth<=maxDepth){
                //LogTool.log(this,"深度未满");
                //满了，要前往下一层
                currentDepth+=1;
                //LogTool.log(this,"当前深度"+currentDepth);
                //createNewLayer(quadTree);
                if(gameObject.getCurrentCoordinate().indexX<quadTree.bound.centreX){
                    //左边
                    if(gameObject.getCurrentCoordinate().indexY<quadTree.bound.centreY){
                        //左上
                        //0
                        addChild(gameObject,quadTree.childQuadTrees[0]);
                    }else {
                        //左下
                        //2
                        addChild(gameObject,quadTree.childQuadTrees[2]);
                    }
                }else {
                    //右边
                    if (gameObject.getCurrentCoordinate().indexY < bound.centreY) {
                        //右上
                        //1
                        addChild(gameObject,quadTree.childQuadTrees[1]);

                    } else {
                        //右下
                        //3
                        addChild(gameObject,quadTree.childQuadTrees[3]);

                    }
                }
                //spilit(this,gameObject);
            }else {
                //LogTool.log(this,"深度达到上限，直接添加");
                addChildInThisLayer(quadTree,gameObject);
            }
        }else {
//            if (!isNewLayer) {
                //LogTool.log(this, "直接加在本层");
                addChildInThisLayer(quadTree, gameObject);
//            }else {
//                LogTool.log(this, "创建新层并加在新层");
//                addChildInNewLayer(quadTree,gameObject);
//            }
        }
    }

    private void addChildInThisLayer(QuadTree motherQuadTree,T gameObject){
        if(gameObject.getCurrentCoordinate().indexX<motherQuadTree.bound.centreX){
            //左边
            if(gameObject.getCurrentCoordinate().indexY<motherQuadTree.bound.centreY){
                //左上
                //0
                if ( motherQuadTree.childQuadTrees[0]==null){
                    createNewLayer(motherQuadTree);
                }
                motherQuadTree.childQuadTrees[0].gameObjects.add(gameObject);
            }else {
                //左下
                //2
                if ( motherQuadTree.childQuadTrees[2]==null){
                    createNewLayer(motherQuadTree);
                }
                motherQuadTree.childQuadTrees[2].gameObjects.add(gameObject);
            }
        }else {
            //右边
            if (gameObject.getCurrentCoordinate().indexY < bound.centreY) {
                //右上
                //1
                if ( motherQuadTree.childQuadTrees[1]==null){
                    createNewLayer(motherQuadTree);
                }
                motherQuadTree.childQuadTrees[1].gameObjects.add(gameObject);
            } else {
                //右下
                //3
                if ( motherQuadTree.childQuadTrees[3]==null){
                    createNewLayer(motherQuadTree);
                }
                motherQuadTree.childQuadTrees[3].gameObjects.add(gameObject);
            }
        }
    }

    private void createNewLayer(QuadTree motherQuadTree){
        float newWidth = motherQuadTree.bound.width/2;
        float newHeight = motherQuadTree.bound.height/2;
        motherQuadTree.childQuadTrees[0] = new QuadTree(maxDepth,maxUnitNumber, new Rectangle(
                bound.centreX-newWidth,//centreX
                bound.centreY-newHeight,//centreY
                newWidth,
                newHeight
        ));
        motherQuadTree.childQuadTrees[2] = new QuadTree(maxDepth,maxUnitNumber,new Rectangle(
                bound.centreX-newWidth,//centreX
                bound.centreY+newHeight,//centreY
                newWidth,
                newHeight
        ));
        motherQuadTree.childQuadTrees[1] = new QuadTree(maxDepth,maxUnitNumber,new Rectangle(
                bound.centreX+newWidth,//centreX
                bound.centreY-newHeight,//centreY
                newWidth,
                newHeight
        ));
        motherQuadTree.childQuadTrees[3] = new QuadTree(maxDepth,maxUnitNumber,new Rectangle(
                bound.centreX+newWidth,//centreX
                bound.centreY+newHeight,//centreY
                newWidth,
                newHeight
        ));

    }

//    private void spilit(QuadTree motherQuadTree,GameObject gameObject){
//        QuadTree quadTree = new QuadTree(gameObjects,maxDepth,maxUnitNumber);
//        float newWidth = bound.width/2;
//        float newHeight = bound.height/2;
//        if(gameObject.getCurrentCoordinate().indexX<bound.centreX){
//            //左边
//            if(gameObject.getCurrentCoordinate().indexY<bound.centreY){
//                //左上
//                quadTree.bound = new Rectangle(
//                        bound.centreX-newWidth,//centreX
//                        bound.centreY-newHeight,//centreY
//                        newWidth,
//                        newHeight
//                );
//                quadTree.addChild(gameObject);
//                motherQuadTree.childQuadTrees[0]=quadTree;
//            }else {
//                //左下
//                quadTree.bound = new Rectangle(
//                        bound.centreX-newWidth,//centreX
//                        bound.centreY+newHeight,//centreY
//                        newWidth,
//                        newHeight
//                );
//                quadTree.addChild(gameObject);
//                motherQuadTree.childQuadTrees[2]=quadTree;
//            }
//        }else {
//            //右边
//            if(gameObject.getCurrentCoordinate().indexY<bound.centreY){
//                //右上
//                quadTree.bound = new Rectangle(
//                        bound.centreX+newWidth,//centreX
//                        bound.centreY-newHeight,//centreY
//                        newWidth,
//                        newHeight
//                );
//                quadTree.addChild(gameObject);
//                motherQuadTree.childQuadTrees[1]=quadTree;
//            }else {
//                //右下
//                quadTree.bound = new Rectangle(
//                        bound.centreX+newWidth,//centreX
//                        bound.centreY+newHeight,//centreY
//                        newWidth,
//                        newHeight
//                );
//                quadTree.addChild(gameObject);
//                motherQuadTree.childQuadTrees[3]=quadTree;
//            }
//        }
//        currentDepth+=1;
//
//    }
}
