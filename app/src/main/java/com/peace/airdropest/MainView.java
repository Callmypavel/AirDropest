package com.peace.airdropest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by ouyan on 2017/8/14.
 */

public class MainView extends android.os.Handler{
    private BaseGameView gameView;
    private Button pauseButton;
    private Activity activity;
    public MainView(Activity activity){
        this.activity = activity;
        initViews(activity);
    }
    private void initViews(final Activity activity){
        LogTool.log(this,"initViews()");
        gameView = activity.findViewById(R.id.main_gameview);
        pauseButton = activity.findViewById(R.id.main_pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameLogicService.getInstance().getMissionState()==Resource.MissionState.MISSION_PAUSED){
                    GameLogicService.getInstance().resumeMission();
                    pauseButton.setText("暂停");
                }else {
                    GameLogicService.getInstance().pauseMission();
                    pauseButton.setText("继续");
                }
            }
        });
        startGame(new Mission());
    }

    private void refreshGame(){

    }

    private void startGame(Mission mission){
        LogTool.log(this,"startGame()");
        GameLogicService.getInstance().setGameEventListener(new GameLogicService.GameEventListener() {
            @Override
            public void OnGameStarted() {

            }

            @Override
            public void OnBulletCooling(String weaponName) {
                Message message = new Message();
                message.what = Resource.MissionState.WEAPON_COOLING;
                Bundle bundle = new Bundle();
                bundle.putString("weapon_name",weaponName);
                message.setData(bundle);
                MainView.this.sendMessage(message);

            }

            @Override
            public void OnBulletShort(String weaponName) {
                Message message = new Message();
                message.what = Resource.MissionState.WEAPON_SHORT;
                Bundle bundle = new Bundle();
                bundle.putString("weapon_name",weaponName);
                message.setData(bundle);
                MainView.this.sendMessage(message);
            }

            @Override
            public void OnEnemyKilled() {
                Message message = new Message();
                message.what = Resource.MissionState.ENEMY_KILLED;
                MainView.this.sendMessage(message);

            }

            @Override
            public void OnGameOvered() {
                Message message = new Message();
                message.what = Resource.MissionState.MISSION_FAILED;
                MainView.this.sendMessage(message);

            }

        },gameView);
        GameLogicService.getInstance().startGame(mission);

    }


    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        switch (msg.what){
            case Resource.MissionState.MISSION_SUCCESSED:
                break;
            case Resource.MissionState.MISSION_FAILED:
                ((DefaultGameView)gameView).setMessage("作战失败");
                //Toast.makeText(activity,"作战失败",Toast.LENGTH_SHORT).show();
                break;
            case Resource.MissionState.WEAPON_COOLING:
                ((DefaultGameView)gameView).setMessage(bundle.getString("weapon_name")+"冷却中");
                //Toast.makeText(activity,"冷却中",Toast.LENGTH_SHORT).show();
                break;
            case Resource.MissionState.WEAPON_SHORT:
                ((DefaultGameView)gameView).setMessage(bundle.getString("weapon_name")+"弹药不足");
                //Toast.makeText(activity,"弹药不足",Toast.LENGTH_SHORT).show();
                break;
            case Resource.MissionState.ENEMY_KILLED:
                break;


        }

    }
}
