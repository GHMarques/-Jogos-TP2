/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tp2.Tp2;
import com.tp2.utils.Config;

/**
 *
 * @author Rogenes
 */

public class MenuScreen extends Game implements Screen {
    
    final Tp2 game;
    
    boolean teste = false;
    
    private final int worldWidth = Config.WORLD_WIDTH;
    private final int worldHeight = Config.WORLD_HEIGHT;
    
    private final Vector2 iniciarBottom = new Vector2(worldWidth*0.1f,worldHeight*0.8f);
    private final Vector2 iniciarSize = new Vector2(worldWidth*0.3f,worldHeight*0.06f); 
    
    private final Vector2 scoreBottom = new Vector2(worldWidth*0.1f,worldHeight*0.65f);
    private final Vector2 scoreSize = new Vector2(worldWidth*0.3f,worldHeight*0.06f); 
    
    private final Vector2 pointerSize = new Vector2(worldWidth*0.08f,worldHeight*0.08f); 
    private Vector2 pointerBottom;
    private Vector2 pointerCenter;
    
    private TextureRegion background; 
    private TextureRegion iniciar; 
    private TextureRegion score; 
    private TextureRegion pointer; 
    
    
    //private Batch batch;
    
    
    public MenuScreen(Tp2 game) {
        this.game = game;
        this.init();
       
    }
    
    void init(){
        
        pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
        pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.x/2);
        background = new TextureRegion(new Texture("images/background.png"));
        iniciar = new TextureRegion(new Texture("images/iniciar.png"));
        score = new TextureRegion(new Texture("images/score.png"));
        pointer = new TextureRegion(new Texture("images/pointer.png"));

    }
    
    
    public boolean collider(Vector2 bottom, Vector2 size, Vector2 click){
        return (click.x>bottom.x && click.x<(bottom.x+size.x)) &&
                (click.y>bottom.y && click.y<(bottom.y+size.y));
    }
    
      public void handleInput(){
        
        if(Gdx.input.justTouched()){
            if(collider(iniciarBottom,iniciarSize,pointerCenter)){
                game.setScreen(new GameScreen(game));
                teste = !teste;
            }
        }
        
    }
    
      public void update(float delta){
          pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
          pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.y/2);
      }
    
    @Override
      public void create(){
      }

    @Override
    public void render(float delta) {
             
        handleInput();
        update(delta);
            
        super.render();
        
        game.batch.begin();
            game.batch.draw(background, 0, 0, worldWidth,worldHeight);
            game.batch.draw(iniciar, iniciarBottom.x, iniciarBottom.y, iniciarSize.x,iniciarSize.y);
            game.batch.draw(score, scoreBottom.x, scoreBottom.y, scoreSize.x,scoreSize.y);
            game.batch.draw(pointer,pointerBottom.x,pointerBottom.y,pointerSize.x,pointerSize.y);
            //game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.batch.end();
    }
    
   @Override
    public void show() {
    }
  
 
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (this.screen != null) this.screen.hide();
    }

    
    
}
