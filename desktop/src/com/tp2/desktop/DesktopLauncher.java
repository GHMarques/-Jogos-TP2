package com.tp2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tp2.Tp2;
import com.tp2.utils.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.width = Config.WORLD_WIDTH;
            config.height = Config.WORLD_HEIGHT;
            new LwjglApplication(new Tp2(), config);
	}
}
