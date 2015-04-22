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
	private int lastX = -1, lastY = -1;
	
	public ProgressCircle(Color color, float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		centerX = (int) (getWidth() / 2F);
		centerY = (int) (getHeight() / 2F);
		
		map = new Pixmap((int)this.getWidth(), (int)this.getHeight(), Pixmap.Format.RGBA8888);
		
		this.color = color;
	}
	
	public void render(SpriteBatch batch){
		int degree = (int) ((GameScreen.gameTicks * 5.0F) % 720F);
		
		if(degree == 0){
			lastX = -1;
			lastY = -1;
			
			map.setColor(Color.CLEAR);
			map.fill();
			
			map.setColor(Color.GRAY);
			int x, y, preX = -1, preY = -1;
			
			for(int i = 0; i < 360; i++){
				x = (int) (centerX + (MathUtils.cosDeg(i + 0) * (getWidth() - (getWidth() / 8F)) / 2F));
				y = (int) (centerY + (MathUtils.sinDeg(i + 0) * (getHeight() - (getHeight() / 8F)) / 2F));
				
				if(preX != -1){
					map.drawLine(preX, preY, x, y);
				}
				
				preX = x;
				preY = y;
			}
			
			map.setColor(color);
		}
		
		int x = (int) (centerX + (MathUtils.cosDeg((degree / 2) + 270) * (getWidth() - (getWidth() / 8F)) / 2F));
		int y = (int) (centerY + (MathUtils.sinDeg((degree / 2) + 270) * (getHeight() - (getHeight() / 8F)) / 2F));
		
		if(lastX != -1){
			map.drawLine(lastX, lastY, x, y);
		}
		
		lastX = x;
		lastY = y;
		
		batch.draw(new Texture(map), getX(), getY(), getWidth(), getHeight());
	}
}
