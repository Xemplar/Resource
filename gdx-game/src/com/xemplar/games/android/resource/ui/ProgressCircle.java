package com.xemplar.games.android.resource.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.xemplar.games.android.resource.screens.GameScreen;

public class ProgressCircle extends View{
	private Color color;
	private int centerX, centerY;
	private Pixmap map;
	
	public ProgressCircle(Color color, float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		centerX = (int) (getWidth() / 2F);
		centerY = (int) (getHeight() / 2F);
		
		map = new Pixmap((int)this.getWidth(), (int)this.getHeight(), Pixmap.Format.RGBA8888);
		
		this.color = color;
	}
	
	public void render(SpriteBatch batch){
		float degree = (float) (((GameScreen.gameTicks) / 2F) % 360);
		
		if(degree >= 360){
			map.setColor(Color.CLEAR);
			//map.fill();
		}
		
		float x = (float) (MathUtils.cosDeg(degree)) * getWidth();
		float y = (float) (MathUtils.sinDeg(degree)) * getHeight(); 
		
		System.out.println("X: " + x);
		
		map.setColor(color);
		map.drawLine((int)x, centerY, centerX, (int)y);
		
		batch.draw(new Texture(map), getX(), getY(), getWidth(), getHeight());
	}
}
