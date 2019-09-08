package com.peace.airdropest.Entity.Base;

/**
 * Created by ouyan on 2017/8/21.
 */

public class Collider {
    private Geometry geometry;
    public Collider(Geometry geometry){
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
