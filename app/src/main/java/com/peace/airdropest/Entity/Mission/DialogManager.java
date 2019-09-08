package com.peace.airdropest.Entity.Mission;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by peace on 2017/9/19.
 */

public class DialogManager {
    private LinkedList<Dialog> dialogs = new LinkedList<>();
    private ArrayList<String> characerRefence = new ArrayList<>();
    private int dialogIndex = 0;
    public Dialog getDialogToDisplay(){
        if(dialogs.size()!=0) {
            return dialogs.get(dialogIndex);
        }else {
            return null;
        }
    }
    public void addDialog(int characterId,String text){
        dialogs.add(new Dialog(text,characterId));
    }
    public void addDialog(int characterId,String text,Bitmap bitmap){
        dialogs.add(new Dialog(text,characterId,bitmap));
    }
    public boolean hasCharacterReferences(){
        return characerRefence.size()!=0;
    }
    public Dialog getNextDialog(){
        dialogIndex+=1;
        if(dialogIndex==dialogs.size()-1){
            dialogIndex -= 1;
            return null;
        }
        return getDialogToDisplay();
    }
    public Dialog getPreviousDialog(){
        dialogIndex-=1;
        if(dialogIndex==-1){
            dialogIndex += 1;
            return null;
        }
        return dialogs.get(dialogIndex);
    }
    public void addCharacterRefence(String characterName){
        characerRefence.add(characterName);
    }
    public String getCharacterRefence(int characherId){
        return characerRefence.get(characherId);
    }
}
