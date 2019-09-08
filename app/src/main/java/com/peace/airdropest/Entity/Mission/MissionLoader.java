package com.peace.airdropest.Entity.Mission;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.peace.airdropest.Entity.Base.GameObject;
import com.peace.airdropest.Entity.Mission.DialogManager;
import com.peace.airdropest.Entity.Mission.Mission;
import com.peace.airdropest.OneApplication;
import com.peace.airdropest.R;
import com.peace.airdropest.Resource;
import com.peace.airdropest.Tool.FileLoader;
import com.peace.airdropest.Tool.LogTool;
import com.peace.airdropest.Tool.OneBitmapUtil;
import com.peace.airdropest.Tool.StringUtil;

/**
 * Created by peace on 2017/9/19.
 */

public class MissionLoader {
    private static int resId = R.raw.mission;
    private static String currentLoadingAttribute;
    public static Mission loadMission(final Context context){
        return loadMission(context,resId);
    }
    public static Mission loadMission(final Context context,int resId){
        final Mission mission = new Mission();
        final DialogManager dialogManager = new DialogManager();
        if(!dialogManager.hasCharacterReferences()){
            loadReferences(context,dialogManager);
        }
        FileLoader.readline(context, resId, new FileLoader.FileLoaderListener() {
            @Override
            public void onLineLoaded(String line) {
                //LogTool.log(this+"+onLineLoaded()",line);
                if(line.startsWith("[")&&line.endsWith("]")){
                    changeAttribute(line);
                }else {
                    if(currentLoadingAttribute!=null){
                        switch (currentLoadingAttribute){
                            case "[base_info]":loadBaseInfos(line,mission);break;
                            case "[dialog_detail]":loadDialogs(context,line,dialogManager);break;
                            case "[building_detail]":loadBuildings(line,mission);break;
                        }
                    }
                }
            }
        });
        mission.setDialogManager(dialogManager);
        if(dialogManager.getDialogToDisplay()==null){
            mission.setMissionState(Resource.MissionState.MISSION_STARTED);
        }else {
            mission.setMissionState(Resource.MissionState.MISSION_DIALOGING);
        }
        return mission;
    }
    private static void changeAttribute(String baseInfoLine){
        if(!baseInfoLine.equals("[all_end]")){
            currentLoadingAttribute = baseInfoLine;
        }else {
            currentLoadingAttribute = null;
        }


    }
    private static void loadBuildings(String baseInfoLine,Mission mission){
        if(!baseInfoLine.trim().equals("")) {
            //开始载入建筑
            Building building = new Building();
            String[] attributes = StringUtil.splitAttributes(baseInfoLine,";");

            //LogTool.log("MissionLoader+loadBuildings()+属性",StringUtil.getStringArray(attributes));
            //第一个属性是建筑类型
            if(attributes[1].equals("Bunker")){
                building.setBuildingType(Resource.BuildingType.Bunker);
            }
            //第二个属性是建筑坐标
            String attribute = attributes[3];
            String[] strings = attribute.split(",");
            building.setPercentCoordinate(new GameObject.Coordinate(strings[0],strings[1]));
            //第三个属性是建筑的大小
            attribute = attributes[5];
            strings = attribute.split(",");
            building.setSizePercent(strings[0],strings[1]);
            //第四个属性是建筑的贴图
            //LogTool.log("MissionLoader+loadBuildings()+比例",building.getWidthPercent()+","+OneApplication.screenWidth);
            if(attributes[7].startsWith("color_")){
                switch (attributes[7]){
                    case "color_blue":building.setImage(OneBitmapUtil.getBitmapFromColor((int)(building.getWidthPercent()* OneApplication.screenWidth),(int)(building.getHeightPercent()*OneApplication.screenHeight), Color.BLUE));break;
                }
            }
            mission.addBuilding(building);

        }
    }

    private static void loadReferences(Context context, final DialogManager dialogManager){
        FileLoader.readline(context, R.raw.id_reference, new FileLoader.FileLoaderListener() {
            @Override
            public void onLineLoaded(String line) {
                //LogTool.log(this+"+onLineLoaded()",line);
                if(line.startsWith("[")&&line.endsWith("]")){
                    changeAttribute(line);
                }else {
                    if(currentLoadingAttribute!=null){
                        switch (currentLoadingAttribute){
                            case "[character]":
                                if(!line.trim().equals("")){
                                    dialogManager.addCharacterRefence(line.split(":")[1]);
                                }

                                break;
                        }
                    }
                }
            }
        });

    }

    private static void loadBaseInfos(String baseInfoLine,Mission mission){
        if(!baseInfoLine.trim().equals("")) {
            String[] strings = baseInfoLine.split("=");
            String attribute = strings[0];
            String value = strings[1];
            switch (attribute) {
                case "mission_name":
                    mission.setMissionName(value);
                    break;
                case "mission_height":
                    mission.setHeight(Integer.parseInt(value));
                    break;
            }
        }

    }


    private static void loadDialogs(Context context,String dialogLine,DialogManager dialogManager){
        if(!dialogLine.trim().equals("")) {
            LogTool.log(MissionLoader.class+"+loadDialogs()",dialogLine);
            String[] dialogInfos = dialogLine.split("\\;");
            String[] strings = dialogInfos[0].split("=");
            String[] strings1 = dialogInfos[1].split("=");
            int characterId = Integer.parseInt(strings[1]);
            String text = strings1[1];
            if (dialogInfos.length > 2) {
                String[] strings2 = dialogInfos[2].split("=");
                Bitmap bitmap = OneBitmapUtil.getBitmapFromName(context, strings2[1]);
                dialogManager.addDialog(characterId, text, bitmap);
            } else {
                dialogManager.addDialog(characterId, text);
            }
        }

    }

}
