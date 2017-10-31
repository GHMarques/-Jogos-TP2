package com.tp2.characters;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.tp2.box2d.*;

public class Floor extends BaseChar {

    public Floor(Body body) {
        super(body);
    }
    
    @Override
    public FloorUserData getUserData() {
        return (FloorUserData) userData;
    }

}
