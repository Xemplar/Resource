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
		int degree = (int) ((GameScreen.gameTicks / 1.0F) % 360F);
		
		if(degree == 0){
			map.setColor(Color.CLEAR);
			map.fill();
			
			map.setColor(Color.GRAY);
			map.drawCircle(centerX, centerY, (int)((getWidth() + getHeight()) / 4));
		}
		
		int x = (int) (centerX + (MathUtils.cosDeg(degree + 0) * (getWidth() - 2) / 2F));
		int y = (int) (centerY + (MathUtils.sinDeg(degree + 0) * (getHeight() - 2) / 2F));
		
		map.setColor(color);
		map.drawLine(x, y, x, y);
		
		batch.draw(new Texture(map), getX(), getY(), getWidth(), getHeight());
	}
}
