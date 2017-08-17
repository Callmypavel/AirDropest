package com.peace.airdropest.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.peace.airdropest.R;
import com.peace.airdropest.View.NavigationView;

/**
 * Created by ouyan on 2017/8/14.
 */

public class NavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        new NavigationView(this);
    }

}
