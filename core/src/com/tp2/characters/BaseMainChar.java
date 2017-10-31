package com.tp2.characters;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.tp2.box2d.*;

public class BaseMainChar extends BaseChar {
    private boolean blnJumping;
    private boolean blnDodging;
    private boolean blnHit;
    
    public BaseMainChar(Body body) {
        super(body);
    }

    @Override
    public UserData getUserData() {
        return (CharUserData) userData;
    }
    
    public void jump() {
        if (!(blnJumping || blnDodging || blnHit)) {
            body.applyLinearImpulse(((CharUserData) getUserData()).getJumpingLinearImpulse(), body.getWorldCenter(), true);
            blnJumping = true;
        }
    }

    public void landed() {
        blnJumping = false;
    }
    
    public void dodge() {
        if (!blnJumping || blnHit) {
            body.setTransform(((CharUserData) getUserData()).getDodgePosition(), ((CharUserData) getUserData()).getDodgeAngle());
            blnDodging = true;
        }
    }

    public void stopDodge() {
        blnDodging = false;
        if (!blnHit) 
            body.setTransform(((CharUserData) getUserData()).getRunningPosition(), 0f);
    }

    public boolean isDodging() {
        return blnDodging;
    }
    
    public void hit() {
        body.applyAngularImpulse(((CharUserData) getUserData()).getHitAngularImpulse(), true);
        blnHit = true;
    }

    public boolean isHit() {
        return blnHit;
    }

}