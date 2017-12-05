/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.characters;

import com.badlogic.gdx.physics.box2d.Body;
import com.tp2.box2d.BulletUserData;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class Bullet extends BaseChar {

    public Bullet(Body body) {
        super(body);
    }

    @Override
    public BulletUserData getUserData() {
        return (BulletUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

}
