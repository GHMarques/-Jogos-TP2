
package com.tp2.characters;

import com.badlogic.gdx.physics.box2d.Body;
import com.tp2.box2d.PlatformUserData;

/**
 *
 * @author Rogenes
 */
public class Platform extends BaseChar {

    public Platform(Body body) {
        super(body);
    }

    @Override
    public PlatformUserData getUserData() {
        return (PlatformUserData) userData;
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }
    
}
