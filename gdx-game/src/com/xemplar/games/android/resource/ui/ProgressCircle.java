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
		float degree = (float) ((GameScreen.gameTicks / 1F) % 360F);
		
		if(degree == 0){
			map.setColor(Color.CLEAR);
			map.fill();
		}
		
		int x = (int) (MathUtils.cosDeg(degree) * getWidth());
		int y = (int) (MathUtils.sinDeg(degree) * getHeight()); 
		
		map.setColor(color);
		map.drawLine(x, y, centerX, centerY);
		
		batch.draw(new Texture(map), getX(), getY(), getWidth(), getHeight());
	}
}
