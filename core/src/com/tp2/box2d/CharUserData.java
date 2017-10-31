package com.tp2.box2d;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.math.Vector2;
import com.tp2.enums.*;
import com.tp2.utils.*;

public class CharUserData extends UserData {

    private Vector2 jumpingLinearImpulse;
    private final Vector2 runningPosition = new Vector2(Config.CHAR_X, Config.CHAR_Y);
    private final Vector2 dodgePosition = new Vector2(Config.CHAR_DODGE_X, Config.CHAR_DODGE_Y);

    public CharUserData(float width, float height) {
        super(width, height);
        jumpingLinearImpulse = Config.CHAR_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.CHAR;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }
    
    public float getDodgeAngle() {
        // In radians
        return (float) (-90f * (Math.PI / 180f));
    }

    public Vector2 getRunningPosition() {
        return runningPosition;
    }

    public Vector2 getDodgePosition() {
        return dodgePosition;
    }
    public float getHitAngularImpulse() {
        return Config.CHAR_HIT_ANGULAR_IMPULSE;
    }

}