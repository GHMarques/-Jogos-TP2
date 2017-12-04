package com.tp2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tp2.screens.*;

/**
 * 
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class Tp2 extends Game {
	
    //Game game;
    private SpriteBatch batch;
    private BitmapFont font; 

    public Tp2() {
    }
   
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new MenuScreen(this));
        //setScreen(new GameScreen());
        
    }
    
    @Override
    public void render() {
		super.render(); //important!
	}
	
    @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
    
}
