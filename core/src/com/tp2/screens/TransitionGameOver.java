/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp2.screens;

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
public class TransitionGameOver extends Game implements Screen{

    Tp2 game;
    String nome;
    
    private final int worldWidth = Config.WORLD_WIDTH;
    private final int worldHeight = Config.WORLD_HEIGHT;
    int score, distance;
    
    boolean alreadyWrote;
    
    private TextureRegion pointer;
    private TextureRegion background;
    
    private final Vector2 pointerSize = new Vector2(worldWidth*0.08f,worldHeight*0.08f); 
    private Vector2 pointerBottom;
    private Vector2 pointerCenter;
    
    private FreeTypeFontGenerator generator2;
    private FreeTypeFontParameter parameter2;
    private BitmapFont font2;
    
    MyTextInputListener listener;
    
    private FileHandle file;
    private boolean fileExists;
    private String fileContent;
    private long fileLenght;
    private String nameFromFile = "";
    private String scoreFromFile = "";
    private String distFromFile = "";
    

    public TransitionGameOver(Tp2 game, int score, int distance) {
        this.game = game;
        this.score = score;
        this.distance = distance;
        init();
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
        
        nome = "";
        generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Bosk.ttf"));
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 30;
        parameter2.color.set(com.badlogic.gdx.graphics.Color.GOLD);
        font2 = generator2.generateFont(parameter2); 
        generator2.dispose();
        
        alreadyWrote = false;
        pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
        pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.x/2);
        pointer = new TextureRegion(new Texture("images/pointer.png"));
        background = new TextureRegion(new Texture("images/background3.jpg"));
        
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
    
    private void writeScore() {
        fileContent = file.readString();
        fileLenght = file.length();
        
        if(fileLenght>0){
            if(score >= Integer.parseInt(scoreFromFile)){
                file.writeString(nome, false);
                //fileContent = file.readString();
            }
        }
        else{
            file.writeString(nome, false);
            //fileContent = file.readString();
        }
    }
    
    public void handleInput(){
        if(Gdx.input.justTouched()){
            if(!alreadyWrote){
                //chama box de input de nome
                //se escreveu, alreadyWrote vira false, la na MyTextInputListener
                listener = new MyTextInputListener();
                Gdx.input.getTextInput(listener, "Escreva seu nome", "", "seu nome");
            }
            else if(alreadyWrote){
                nome = nome+"\n"+distance+"\n"+score;
                writeScore();
                game.setScreen(new EndScreen(game));
            }
        }
    }
    
      public void update(float delta){
          pointerBottom = new Vector2(Gdx.input.getX()-pointerSize.x/2,worldHeight-Gdx.input.getY()-pointerSize.y/2);
          pointerCenter = new Vector2(pointerBottom.x+pointerSize.x/2,pointerBottom.y+pointerSize.y/2);
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
            game.batch.draw(pointer,pointerBottom.x,pointerBottom.y,pointerSize.x,pointerSize.y);
            font2.draw(game.batch, "Seu Score"+" : "+score,worldWidth*0.35f , worldHeight*0.90f);
            font2.draw(game.batch, "Sua Distância"+" : "+distance, worldWidth*0.35f, worldHeight*0.85f);    
            font2.draw(game.batch, "Seu nome"+" : "+nome, worldWidth*0.35f, worldHeight*0.95f);    
            if(fileLenght>0){
                font2.draw(game.batch,"nome: "+nameFromFile+"Dist: "+distFromFile+"Score: "+scoreFromFile, worldWidth*0.35f, worldHeight*0.55f); 
            }
            
                   
        game.batch.end();
    }

    @Override
    public void hide() {
    }

    
    
    public class MyTextInputListener implements TextInputListener {

        public MyTextInputListener() {
        }
        
        @Override
        public void input (String text) {
            nome = text;
            if(!"".equals(text) && text!=null){
                alreadyWrote = true;
            }
        }

        @Override
        public void canceled () {
   }
    }
}
