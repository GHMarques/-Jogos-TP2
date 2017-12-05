package com.tp2.characters;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tp2.box2d.*;

public abstract class BaseChar extends Actor {
    public Body body;
    protected UserData userData;

    public BaseChar(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    public abstract UserData getUserData();

}