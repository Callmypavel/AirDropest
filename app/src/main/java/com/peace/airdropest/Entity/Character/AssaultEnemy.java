package com.peace.airdropest.Entity.Character;

import com.peace.airdropest.Resource;

/**
 * Created by peace on 2017/10/23.
 */

public class AssaultEnemy extends Enemy {
    public AssaultEnemy(){
        setActionMode(Resource.ActionMode.ACTION_ENEMY_ASSAULT);
    }
}
