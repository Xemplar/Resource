package com.xemplar.games.android.resource;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.xemplar.games.android.resource.screens.*;

public class Resource extends Game {
	public static Resource instance;
    
	public void create() {
		instance = this;
        
        setScreen(new GameScreen(0));
	}
}
