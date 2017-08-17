package com.peace.airdropest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.peace.airdropest.Logic.GameLogicService;
import com.peace.airdropest.R;
import com.peace.airdropest.View.MainView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        GameLogicService.getInstance().cancelMission();
        super.onBackPressed();
    }
}
