/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.tp2.Tp2;
import com.tp2.utils.Config;
/**
 *
 * @author Rogenes
 */
public class HighScoreScreen extends Game implements Screen{

    Tp2 game;
    
    private final int worldWidth = Config.WORLD_WIDTH;
    private final int worldHeight = Config.WORLD_HEIGHT;
    int score, distance;
    
    boolean alreadyWrote;
    
    private TextureRegion pointer;
    private TextureRegion background;
    private TextureRegion voltar;
    
    
    private final Vector2 pointerSize = new Vector2(worldWidth*0.08f,worldHeight*0.08f); 
    private Vector2 pointerBottom;
    private Vector2 pointerCenter;
    
    private Vector2 voltarBottom;
    private Vector2 voltarSize;
    
    
    private FreeTypeFontGenerator generator2;
    private FreeTypeFontParameter parameter2;
    private BitmapFont font2;
    
    private FileHandle file;
    private boolean fileExists;
    private String fileContent;
    private long fileLenght;
    private String nameFromFile = "";
    private String scoreFromFile = "";
    private String distFromFile = "";
    
    
    
    public HighScoreScreen(Tp2 game) {
        this.game = game;
        init();
    }
    
    public boolean collider(Vector2 bottom, Vector2 size, Vector2 click){
        return (click.x>bottom.x && click.x<(bottom.x+size.x)) &&
                (click.y>bottom.y && click.y<(bottom.y+size.y));
    }
    
    public void handleInput(){
        if(Gdx.input.justTouched()){
            if(collider(voltarBottom,voltarSize,pointerCenter)){
                game.setScreen(new MenuScreen(game));
            }
        }
    }
    
      public void update(float delta){
          pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
          pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.y/2);
      }
    
    public void init(){
        int i,countSpace = 0;
        boolean GetScoreFromFile = false;
        boolean GetDistFromFile = false;
        boolean GetNameFromFile = true;
        
        
        file = Gdx.files.local("score/youCanCheatButThereIsNoFunInDoingIt.txt");
        fileExists = Gdx.files.local("score/youCanCheatButThereIsNoFunInDoingIt.txt").exists();
        if(!fileExists){
            file.writeString("", false);
        }
        fileContent = file.readString();
        fileLenght = file.length();
        
        generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Bosk.ttf"));
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 40;
        parameter2.color.set(com.badlogic.gdx.graphics.Color.GRAY);
        parameter2.borderColor.set(com.badlogic.gdx.graphics.Color.BLACK);
        parameter2.borderWidth = 3;
        font2 = generator2.generateFont(parameter2); 
        generator2.dispose();
        
        pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
        pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.x/2);
        
        voltarBottom = new Vector2(worldWidth*0.37f,worldHeight*0.12f);
        voltarSize = new Vector2(worldWidth*0.26f,worldHeight*0.05f);
        
        
        pointer = new TextureRegion(new Texture("images/pointer.png"));
        background = new TextureRegion(new Texture("images/background4.png"));
        voltar = new TextureRegion(new Texture("images/voltar.png"));
        
        
        if(fileLenght>0){
            for(i=0;i<fileLenght;i++){
                
                if(GetNameFromFile && fileContent.charAt(i)!='\n'){
                    nameFromFile+=fileContent.charAt(i);
                }
                
                if(GetDistFromFile && fileContent.charAt(i)!='\n'){
                    distFromFile+=fileContent.charAt(i);
                }
                    
                if(GetScoreFromFile && fileContent.charAt(i)!='\n'){
                    scoreFromFile+=fileContent.charAt(i);
                }
                
                if(fileContent.charAt(i)=='\n'){
                    GetNameFromFile = false;
                    countSpace++;
                    if(countSpace==1){
                        GetDistFromFile = true;
                    }
                    
                    else if(countSpace==2){
                        GetScoreFromFile = true;
                        GetDistFromFile = false;
                    }
                }
            }
        }
    }
    
    
    
    @Override
    public void create() {
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
            
            game.batch.draw(background,0,0,worldWidth,worldHeight);
            game.batch.draw(voltar,voltarBottom.x,voltarBottom.y,voltarSize.x,voltarSize.y);
            
            
            
            font2.draw(game.batch,"HighScore!",worldWidth*0.4f, worldHeight*0.95f); 
            if(fileLenght>0){
                    font2.draw(game.batch,"Nome: "+nameFromFile,worldWidth*0.37f, worldHeight*0.8f); 
                    font2.draw(game.batch,"Score: "+scoreFromFile,worldWidth*0.37f, worldHeight*0.7f); 
                    font2.draw(game.batch,"Distância: "+distFromFile,worldWidth*0.37f, worldHeight*0.6f); 
                    
            }
            game.batch.draw(pointer,pointerBottom.x,pointerBottom.y,pointerSize.x,pointerSize.y);
            
                   
        game.batch.end();
    }

    @Override
    public void hide() {
    }
    
}
