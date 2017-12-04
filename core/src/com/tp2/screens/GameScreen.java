package com.tp2.screens;

/**
 *
 * @author GHMarques <gustavo.marques2011@gmail.com>
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tp2.Tp2;
import com.tp2.stages.*;

public class GameScreen extends Game implements Screen {

    private GameStage stage;
    final Tp2 game;

    public GameScreen(Tp2 game) {
        
        stage = new GameStage();
        this.game = game;
    }

    @Override
    public void render(float delta) {
        
        super.render();
        
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
        stage.draw();
        stage.act(delta);
    }
    

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void create() {
    }

}