package com.peace.airdropest;

/**
 * Created by ouyan on 2017/8/14.
 */

public class Resource {
    public static class ActionMode {
        public final static int ACTION_ENEMY_DEFAULT = 1;
        public final static int ACTION_ENEMY_MEDIC = 2;
        public final static int ACTION_ENEMY_ASSAULT= 3;
        public final static int ACTION_ENEMY_ELITE = 4;

    }
    public static class MissionState{
        public static final int MISSION_STARTED = 0;
        public static final int MISSION_PAUSED= 1;
        public static final int MISSION_CANCELLED= 2;
        public static final int MISSION_SUCCESSED= 3;
        public static final int MISSION_FAILED= 4;
        public static final int WEAPON_COOLING= 5;
        public static final int WEAPON_SHORT= 6;
        public static final int ENEMY_KILLED= 7;
        public static final int MISSION_DIALOGING= 8;
    }
    public static class ViewConfig{
        public static final int DEFAULT_HORIZONTAL_NUM = 50;
        public static final int DEFAULT_VERTICAL_NUM = 50;
        public static int UNIT_WIDTH;
        public static int UNIT_HEIGHT;
        public static int SCREEN_WIDTH;
    }
    static class WeaponType{
        public static final int Rifle = 1;
        public static final int Bomb = 2;
        public static final int Lazer = 3;
    }
    public static class BuildingType {
        public final static int Bunker = 1;

    }
}
