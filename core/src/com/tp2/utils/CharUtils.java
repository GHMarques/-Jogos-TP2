package com.tp2.utils;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.tp2.box2d.*;
import com.tp2.enums.UserDataType;
public class CharUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.CHAR;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.FLOOR;
    }
    
    public static boolean bodyIsPlatform(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.PLATFORM;
    }
    
    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();
        if(userData == null){
            return false;
        }
        switch (userData.getUserDataType()) {
            case BULLET:
                return body.getPosition().x + userData.getWidth() / 2 > 0 && body.getPosition().x - userData.getWidth() / 2 < 20;
            case CHAR:
            case ENEMY:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }

        return true;
    }

    public static boolean bodyIsEnemy(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }
    
    public static boolean bodyIsBullet(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.BULLET;
    }

}