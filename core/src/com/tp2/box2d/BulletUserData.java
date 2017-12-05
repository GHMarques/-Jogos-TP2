/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tp2.enums.UserDataType;
import com.tp2.utils.Config;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class BulletUserData extends UserData {
    private Vector2 linearVelocity;

    public BulletUserData (float width, float height) {
        super(width, height);
        linearVelocity = Config.BULLET_LINEAR_VELOCITY;
        userDataType = UserDataType.BULLET;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }
	
	
}