/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tp2.Tp2;
import com.tp2.utils.Config;

/**
 *
 * @author Rogenes
 */
public class EndScreen extends Game implements Screen {

    final Tp2 game;
    
    private TextureRegion backgroundEnd; 
    private TextureRegion reiniciar; 
    private TextureRegion sair; 
    private TextureRegion pointer; 
    
    private final int worldWidth = Config.WORLD_WIDTH;
    private final int worldHeight = Config.WORLD_HEIGHT;
    
    private final Vector2 reiniciarBottom = new Vector2(worldWidth*0.1f,worldHeight*0.8f);
    private final Vector2 reiniciarSize = new Vector2(worldWidth*0.3f,worldHeight*0.06f); 
    
    private final Vector2 sairBottom = new Vector2(worldWidth*0.1f,worldHeight*0.65f);
    private final Vector2 sairSize = new Vector2(worldWidth*0.3f,worldHeight*0.06f); 
    
    private final Vector2 pointerSize = new Vector2(worldWidth*0.08f,worldHeight*0.08f); 
    private Vector2 pointerBottom;
    private Vector2 pointerCenter;
    
    public EndScreen(Tp2 game) {
        this.game = game;
        this.init();
    }
    
    void init(){
        
        pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
        pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.x/2);
        backgroundEnd = new TextureRegion(new Texture("images/background2.png"));
        reiniciar = new TextureRegion(new Texture("images/menu.png"));
        sair = new TextureRegion(new Texture("images/sair.png"));
        pointer = new TextureRegion(new Texture("images/pointer.png"));

    }
    
    public boolean collider(Vector2 bottom, Vector2 size, Vector2 click){
        return (click.x>bottom.x && click.x<(bottom.x+size.x)) &&
                (click.y>bottom.y && click.y<(bottom.y+size.y));
    }
    
      public void handleInput(){
        
        if(Gdx.input.justTouched()){
            if(collider(reiniciarBottom,reiniciarSize,pointerCenter)){
                //this.create();
                game.setScreen(new MenuScreen(game));
            }
            else if(collider(sairBottom,sairSize,pointerCenter)){
                System.exit(1);
            }
        }
    }
      
      public void update(float delta){
          pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
          pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.y/2);
      }
    
    
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        handleInput();
        update(delta);
            
        super.render();
        
        game.batch.begin();
            game.batch.draw(backgroundEnd, 0, 0, worldWidth,worldHeight);
            game.batch.draw(reiniciar, reiniciarBottom.x, reiniciarBottom.y, reiniciarSize.x,reiniciarSize.y);
            
            game.batch.draw(sair, sairBottom.x, sairBottom.y, sairSize.x, sairSize.y);
            game.batch.draw(pointer,pointerBottom.x,pointerBottom.y,pointerSize.x,pointerSize.y);
            //game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.batch.end();
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
    }

    @Override
    public void create() {
    }
    
    
}
