package com.tp2.stages;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.tp2.screens.TransitionGameOver;
import static java.lang.Math.abs;

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
    private Enemy enemy;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    
    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private Vector3 touchPoint;
    private final Tp2 game;
    
    private TextureRegion background;
    
    
    /*Score variables*/
    private int distance = 0;
    private int score = 0;
    private int timer = 0;
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
    private float curveDifficulty;
    private float difficultyValue;
    private float scroll=0;
    
    public GameStage(Tp2 game) {
        
        this.game = game;
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();
        background = new TextureRegion(new Texture("images/cenario.png"));
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
        difficultyValue = 0;
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
        scroll+=2;
        if(scroll+worldWidth>=background.getRegionWidth()){
            scroll=0;
        }
        distance++;
        if(distance%40==0){
            score+=multiplier;
            
        }
        
        if(this.collisionEnemyBullet){
            whileCombo = true;
        }
        
        if(whileCombo){
            timer--;
            if(this.collisionEnemyBullet){
        
                combo++;
                if(combo<4){
                    timer = 500;
                    multiplier = 1;
                }
                else if(combo<10){
                    timer = 400;
                    multiplier = combo4;
                }
                else if(combo<25){
                    timer = 350;
                    multiplier = combo10;
                }
                else if(combo<75){
                    timer = 250;
                    multiplier = combo25;
                }
                else if(combo<100){
                    timer = 200;
                    multiplier = combo75;
                }
                else if(combo>=100){
                    timer = 100;
                    multiplier = combo100;
                }
            }

            if(timer<0){
                whileCombo = false;
                combo = 1;
                timer = 500;
            }
        }
        
        
        /*end of score control*/
        
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        if(enemy != null){
            difficultyValue = this.findDifficulty(score);
            curveDifficulty = getCurveValueBetween((float) difficultyValue, 5f, 20f);
            curveDifficulty*=-1;
            enemy.body.setLinearVelocity(curveDifficulty, 0);
        }
        
        if(runner.isHit()){
            for (Body body : bodies)
                world.destroyBody(body);
            game.setScreen(new TransitionGameOver(game,score,distance));
        }
        else if(this.collisionEnemyBullet){
            bullet.remove();
            isShooting = false;
            enemy.remove();
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
                enemy.remove();
                createEnemy();
                //whileCombo = false;
                
            }
            else if(CharUtils.bodyIsBullet(body)){
                isShooting = false;
                bullet.remove();
            }
                
            world.destroyBody(body);
        }
        
    }

    private void createEnemy() {
        enemy = new Enemy(MyWorld.createEnemy(world));
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
            //game.batch.draw(background,-scroll,-50, worldWidth*5, worldHeight+51);    

            game.font.draw(game.batch, "Distância"+" : "+distance, worldWidth*0.82f, worldHeight*0.95f);    
            game.font.draw(game.batch, "Score"+" : "+score,worldWidth*0.82f , worldHeight*0.90f);
            game.font.draw(game.batch, "Timer"+" : "+timer,worldWidth*0.82f , worldHeight*0.85f);
            game.font.draw(game.batch, "Combo"+" : "+multiplier,worldWidth*0.82f , worldHeight*0.8f);
            game.font.draw(game.batch, "scroll: "+scroll+"mundo: "+worldWidth+"Background: "+background.getRegionWidth(),worldWidth*0.5f , worldHeight*0.70f);
            
            
            
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
    
    
    public float getCurveValueBetween(float value, float min, float max) {
        return getCurveValue(value) * (max - min) + min;
    }
    
    //curve S
    public float getCurveValue(float value) {
        return (float) (1f / (1f + Math.pow(Math.E, -6 * (value - 0.5f))));
    }
    
    public float findDifficulty(int score){
        float returnDifficulty = (float) 0.1;
        if(score <= 50)
            returnDifficulty = (float) 0.1;
        else if(score > 50 && score <= 100)
            returnDifficulty = (float) 0.2;
        else if(score > 100 && score <= 150)
            returnDifficulty = (float) 0.3;
        else if(score > 150 && score <= 200)
            returnDifficulty = (float) 0.4;
        else if(score > 200 && score <= 300)
            returnDifficulty = (float) 0.5;
        else if(score > 300 && score <= 400)
            returnDifficulty = (float) 0.6;
        else if(score > 400 && score <= 500)
            returnDifficulty = (float) 0.7;
        else if(score > 500 && score <= 750)
            returnDifficulty = (float) 0.8;
        else if(score > 750 && score <= 1000)
            returnDifficulty = (float) 0.9;
        else
            returnDifficulty = (float) 1;
        return returnDifficulty;
    }
    

    

}
