package com.tp2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.tp2.screens.*;

/**
 * 
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class Tp2 extends Game {
	
    //Game game;
    public SpriteBatch batch;
    public BitmapFont font; 
    public FreeTypeFontGenerator generator;
    public FreeTypeFontParameter parameter;
    
    public Tp2() {
    }
   
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Bosk.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color.set(com.badlogic.gdx.graphics.Color.VIOLET);
        parameter.borderColor.set(com.badlogic.gdx.graphics.Color.BLACK);
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter); 
        generator.dispose();
        
        setScreen(new MenuScreen(this));
        
    }
    
    @Override
    public void render() {
		super.render(); //important!
	}
	
    @Override
	public void dispose() {
		batch.dispose();
	}
    
}
