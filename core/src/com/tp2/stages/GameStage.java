package com.tp2.stages;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tp2.utils.*;
import com.tp2.characters.*;
import com.badlogic.gdx.utils.Array;
import com.tp2.box2d.BulletUserData;
import java.util.ArrayList;
import com.tp2.Tp2;
import com.tp2.screens.EndScreen;

public class GameStage extends Stage implements ContactListener {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private final int worldWidth = Config.WORLD_WIDTH;
    private final int worldHeight = Config.WORLD_HEIGHT;
    
    private World world;
    private Floor floor;
    private BaseMainChar runner;
    private Platform platform;
    private Bullet bullet;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    
    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private Vector3 touchPoint;
    private final Tp2 game;
    
    /*Score variables*/
    private int count = 0;
    private int score = 0;
    private int timer = 1000;
    private int multiplier = 1;
    private int combo = 0;
    private int combo4 = 4;
    private int combo10 = 10;
    private int combo25 = 25;
    private int combo75 = 75;
    private int combo100 = 100;
    
    private boolean isShooting = false;
    private boolean collisionEnemyBullet;
    private int countDestroy;
    private boolean whileCombo = false;
    
    public GameStage(Tp2 game) {
        
        this.game = game;
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();
    }

    private void setUpWorld() {
        world = MyWorld.createWorld();
        world.setContactListener(this);
        setUpFloor();
        setUpRunner();
        createEnemy();
        isShooting = false;
        collisionEnemyBullet = false;
        countDestroy = 0;
        //setUpPlatform();
    }

    private void setUpFloor() {
        floor = new Floor(MyWorld.createFloor(world));
        addActor(floor);
    }

    private void setUpRunner() {
        runner = new BaseMainChar(MyWorld.createRunner(world));
        addActor(runner);
    }
    
    private void setUpPlatform() {
        platform = new Platform(MyWorld.createPlatform(world));
        addActor(platform);
    }
    
    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }
    
    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void act(float delta) {
        
        super.act(delta);
        
        
        /*Score control*/
        
        count++;
        if(count%40==0){
            score+=multiplier;
        }
        
        /*
        if(matou bicho){
            whileCombo = true;
        }
        
        if(whileCombo){
            timer--;
            if(matou bicho){
        
                    combo++;
                    if(combo<4){
                        timer = 1000;
                        multiplier = 1;
                    }
                    else if(combo<10){
                        timer = 900;
                        multiplier = combo4;
                    }
                    else if(combo<25){
                        timer = 800;
                        multiplier = combo10;
                    }
                    else if(combo<75){
                        timer = 700;
                        multiplier = combo25;
                    }
                    else if(combo<100){
                        timer = 500;
                        multiplier = combo75;
                    }
                    else if(combo>=100){
                        timer = 300;
                        multiplier = combo100;
                    }
            }

            if(timer<1){
                whileCombo = false;
                combo = 1;
                timer = 1000;
            }
        }
        
        */
        
        /*end of score control*/
        
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        if(runner.isHit()){
            for (Body body : bodies)
                world.destroyBody(body);
            game.setScreen(new EndScreen(game));
        }
        else if(this.collisionEnemyBullet){
            for (Body body : bodies) {
                if(CharUtils.bodyIsBullet(body)){
                    world.destroyBody(body);
                    isShooting = false;
                }
                    
                if(CharUtils.bodyIsEnemy(body))
                    world.destroyBody(body);
            }
            this.collisionEnemyBullet = false;
            createEnemy();
        }
        else{
            for (Body body : bodies) {
                update(body);
            }
        }
        

        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }


    }
    

    private void update(Body body) {
        if (!CharUtils.bodyInBounds(body)) {
            if (CharUtils.bodyIsEnemy(body) && !runner.isHit()) {
                createEnemy();
                
            }
            else if(CharUtils.bodyIsBullet(body))
                isShooting = false;
            world.destroyBody(body);
        }
        
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(MyWorld.createEnemy(world));
        addActor(enemy);
    }
    
    public void createBullet(){
        bullet = new Bullet(MyWorld.createRunnerBullet(world, runner.body.getPosition().x, runner.body.getPosition().y));
        addActor(bullet);
        isShooting = true;
        //Bullet bullet = new Bullet(MyWorld.createRunnerBullet(world, runner.getX(), runner.getY()));
        //addActor(bullet);
        //Bullet bullet1 = new Bullet(MyWorld.createRunnerBullet(world, 2, 2f));
    }

    @Override
    public void draw() {
        
        
        game.batch.begin();
            game.font.draw(game.batch, "Distância"+" : "+count, worldWidth*0.8f, worldHeight*0.95f);    
            game.font.draw(game.batch, "Score"+" : "+score,worldWidth*0.8f , worldHeight*0.90f);
        game.batch.end();
        super.draw();
        renderer.render(world, camera.combined);
        
    }
    
    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
            
        if (detectCollision(a, b)){
            runner.hit();
        }
            
        else if (detectCollisionBulletEnemy(a, b)){
            //isShooting = false;
            collisionEnemyBullet = true;
        }
        else if (((CharUtils.bodyIsRunner(a) && CharUtils.bodyIsGround(b)) ||
                (CharUtils.bodyIsGround(a) && CharUtils.bodyIsRunner(b))) ||
                (CharUtils.bodyIsRunner(a) && CharUtils.bodyIsPlatform(b)) ||
                (CharUtils.bodyIsPlatform(a) && CharUtils.bodyIsRunner(b))
                ) {
            runner.landed();
        }
        
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode){
            //movement
            case Keys.UP:
                runner.jump();
                break;
            case Keys.DOWN:
                runner.dodge();
                break;
            //shoot
            case Keys.SPACE:
                if(!isShooting)
                    createBullet();
                break;
        }
        return true;
    }
    
    @Override
    public boolean keyUp(int keycode)
    {
	if (runner.isDodging()) {
            runner.stopDodge();
        }
        return true;
    }
    
    public boolean detectCollision(Body a, Body b){
        boolean blnReturn = false;
        if ((CharUtils.bodyIsRunner(a) && CharUtils.bodyIsEnemy(b)) ||
                (CharUtils.bodyIsEnemy(a) && CharUtils.bodyIsRunner(b)))
            blnReturn = true;
        return blnReturn;
    }
    
    public boolean detectCollisionBulletEnemy(Body a, Body b){
        boolean blnReturn = false;
        if ((CharUtils.bodyIsBullet(a) && CharUtils.bodyIsEnemy(b)) ||
                (CharUtils.bodyIsEnemy(a) && CharUtils.bodyIsBullet(b)))
            blnReturn = true;
        return blnReturn;
    }
    
    

    

}
