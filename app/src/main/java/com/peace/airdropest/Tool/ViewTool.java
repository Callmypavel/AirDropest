package com.peace.airdropest.Tool;

import android.util.TypedValue;

import com.peace.airdropest.OneApplication;

/**
 * Created by peace on 2018/3/26.
 */

public class ViewTool {
    public static int getPxFromDp(int dp){
        float pxDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, OneApplication.getInstance().getResources().getDisplayMetrics());
        return (int)pxDimension;
    }
}
