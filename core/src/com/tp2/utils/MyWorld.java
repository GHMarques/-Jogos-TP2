package com.tp2.utils;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tp2.box2d.*;
import com.tp2.enums.*;
import com.tp2.utils.*;

public class MyWorld {

    public static World createWorld() {
        return new World(Config.WORLD_GRAVITY, true);
    }

    public static Body createFloor(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Config.FLOOR_X, Config.FLOOR_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Config.FLOOR_WIDTH, Config.FLOOR_HEIGHT / 2);
        body.createFixture(shape, Config.FLOOR_DENSITY);
        body.setUserData(new FloorUserData());
        shape.dispose();
        return body;
    }
    
    public static Body createRunner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Config.CHAR_X, Config.CHAR_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Config.CHAR_WIDTH/2, Config.CHAR_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Config.CHAR_GRAVITY_SCALE);
        body.createFixture(shape, Config.CHAR_DENSITY);
        body.resetMassData();
        body.setUserData(new CharUserData(Config.CHAR_WIDTH, Config.CHAR_HEIGHT));
        shape.dispose();
        return body;
    }
    
    public static Body createRunnerBullet(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(x, y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Config.CHAR_BULLET_WIDTH/2, Config.CHAR_BULLET_HEIGHT);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Config.CHAR_GRAVITY_SCALE);
        body.createFixture(shape, Config.CHAR_DENSITY);
        body.resetMassData();
        body.setUserData(new BulletUserData(Config.CHAR_WIDTH, Config.CHAR_HEIGHT));
        shape.dispose();
        return body;
    }
    
    public static Body createEnemy(World world) {
        EnemyType enemyType = Rand.getRandomEnemyType();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(enemyType.getX(), enemyType.getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemyType.getWidth() / 2, enemyType.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, enemyType.getDensity());
        body.resetMassData();
        EnemyUserData userData = new EnemyUserData(enemyType.getWidth(), enemyType.getHeight());
        body.setUserData(userData);
        shape.dispose();
        return body;
    }
    
    public static Body createPlatform(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(Config.PLATFORM_X, Config.PLATFORM_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Config.PLATFORM_WIDTH/ 2, Config.PLATFORM_HEIGHT/ 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Config.PLATFORM_DENSITY);
        body.resetMassData();
        body.setUserData(new PlatformUserData(Config.PLATFORM_WIDTH, Config.PLATFORM_HEIGHT));
        shape.dispose();
        return body;
    }

}
