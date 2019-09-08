package com.peace.airdropest.Entity.Mission;

import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.OneApplication;
import com.peace.airdropest.Tool.LogTool;

/**
 * Created by peace on 2017/10/23.
 */

public class Building extends GameObject {
    private int buildingType;


    public void setSizePercent(String lengthPercent,String widthPercent) {
//        LogTool.log(this,"查看建筑比例:"+lengthPercent+","+widthPercent);
        this.heightPercent = Float.parseFloat(lengthPercent)/100;
        this.widthPercent = Float.parseFloat(widthPercent)/100;
//        setSize((int)heightPercent* OneApplication.screenHeight,(int)this.widthPercent*OneApplication.screenWidth);
//        LogTool.log(this,"查看建筑比例:"+  this.heightPercent+","+  this.widthPercent);
    }
//    public void setSize(String length,String width) {
//        this.length = Integer.parseInt(length);
//        this.width = Integer.parseInt(width);
//    }
//    public void setSize(int length,int width) {
//        this.length = length;
//        this.width = width;
//    }
//
//    public int getLength() {
//        return length;
//    }
//
//    public void setLength(int length) {
//        this.length = length;
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }

    public int getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(int buildingType) {
        this.buildingType = buildingType;
    }
}
