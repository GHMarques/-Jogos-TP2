package com.tp2;

import com.badlogic.gdx.Game;
import com.tp2.screens.*;

/**
 * 
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
public class Tp2 extends Game {
	
    @Override
    public void create () {
        setScreen(new GameScreen());
    }
}
