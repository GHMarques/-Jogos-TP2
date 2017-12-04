package com.tp2.utils;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class Config {
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 480;
    
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    
    //constantes do mundo
    public static final float FLOOR_X = 0;
    public static final float FLOOR_Y = 0;
    public static final float FLOOR_WIDTH = 25f;
    public static final float FLOOR_HEIGHT = 2f;
    public static final float FLOOR_DENSITY = 0f;
    
    //constantes do personagem
    public static final float CHAR_X = 2;
    public static final float CHAR_Y = FLOOR_Y + FLOOR_HEIGHT;
    public static final float CHAR_WIDTH = 1f;
    public static final float CHAR_HEIGHT = 2f;
    public static float CHAR_DENSITY = 0.5f;
    public static final float CHAR_GRAVITY_SCALE = 3f;
    public static final float CHAR_DODGE_X = 2f;
    public static final float CHAR_DODGE_Y = 1.5f;
    public static final Vector2 CHAR_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final float CHAR_HIT_ANGULAR_IMPULSE = 10f;
    
    //constantes dos inimigos
    public static final float ENEMY_X = 25f;
    public static final float ENEMY_DENSITY = CHAR_DENSITY;
    public static final float RUNNING_SHORT_ENEMY_Y = 1.5f;
    public static final float RUNNING_LONG_ENEMY_Y = 2f;
    public static final float FLYING_ENEMY_Y = 3f;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-10f, 0);
    
    //constantes das plataformas
    public static final float PLATFORM_X = 30f;
    public static final float PLATFORM_Y = 2.5f;
    public static final float PLATFORM_WIDTH = 3f;
    public static final float PLATFORM_HEIGHT = 0.3f;
    public static final float PLATFORM_DENSITY = 0;
    public static final Vector2 PLATFORM_LINEAR_VELOCITY = new Vector2(-7f, 0);



}
