/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.box2d;

import com.badlogic.gdx.math.Vector2;
import com.tp2.enums.UserDataType;
import com.tp2.utils.Config;

/**
 *
 * @author Rogenes
 */
public class PlatformUserData extends UserData{

    private Vector2 linearVelocity;
    
    public PlatformUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.PLATFORM;
        linearVelocity = Config.PLATFORM_LINEAR_VELOCITY;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }
    
    
    
    
}
