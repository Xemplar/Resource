package com.xemplar.games.desktop.resource;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xemplar.games.android.resource.Resource;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 768;
		config.height = 432;
		//config.fullscreen = true;
		
        new LwjglApplication(new Resource(), config);
    }
}
