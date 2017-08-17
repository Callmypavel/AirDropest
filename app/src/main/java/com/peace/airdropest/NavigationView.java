package com.peace.airdropest;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * Created by ouyan on 2017/8/15.
 */

public class NavigationView {
    private Button startButton;
    private Button equipmentButton;
    private Button configButton;
    private Button exitButton;
    public NavigationView(Activity activity){
        initViews(activity);
    }
    public void initViews(final Activity activity){
        startButton = activity.findViewById(R.id.navi_start_button);
        equipmentButton = activity.findViewById(R.id.navi_equipment_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity,MainActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
