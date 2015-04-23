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
	private ProgressReporter reporter;
	
	private boolean started, set;
	private long startTicks;
	
	public ProgressCircle(ProgressReporter reporter, Color color, float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		this.reporter = reporter;
		started = false;
		
		centerX = (int) (getWidth() / 2F);
		centerY = (int) (getHeight() / 2F);
		
		map = new Pixmap((int)this.getWidth(), (int)this.getHeight(), Pixmap.Format.RGBA8888);
		
		this.color = color;
		
		map.setColor(Color.CLEAR);
		map.fill();
	
		map.setColor(Color.GRAY);
		int xp, yp, preX = -1, preY = -1;
	
		for(int i = 0; i < 360; i++){
			xp = (int) (centerX + (MathUtils.cosDeg(i + 0) * (getWidth() - (getWidth() / 8F)) / 2F));
			yp = (int) (centerY + (MathUtils.sinDeg(i + 0) * (getHeight() - (getHeight() / 8F)) / 2F));
		
			if(preX != -1){
				map.drawLine(preX, preY, xp, yp);
			}
		
			preX = xp;
			preY = yp;
		}
		
		map.setColor(color);
	}
	
	public void start(){
		this.startTicks = GameScreen.gameTicks;
		reporter.onStart();
		started = true;
	}
	
	public void render(SpriteBatch batch){
		if(started){
			
			int degree = (int) ((((GameScreen.gameTicks - startTicks)) * 5.0F) % 720F);
			
			if(degree == 0){
				if(set){
					set = false;
					
					reporter.onFinish();
					started = false;
				}
				
				set = true;
				
				lastX = -1;
				lastY = -1;
			
				map.setColor(Color.CLEAR);
				map.fill();
			}
		
			int x = (int) (centerX + (MathUtils.cosDeg((degree / 2) + 270) * (getWidth() - (getWidth() / 8F)) / 2F));
			int y = (int) (centerY + (MathUtils.sinDeg((degree / 2) + 270) * (getHeight() - (getHeight() / 8F)) / 2F));
			
			if(lastX != -1){
				map.drawLine(lastX, lastY, x, y);
			}
			
			lastX = x;
			lastY = y;
			
			reporter.postProgress(GameScreen.gameTicks - startTicks);
			
			batch.draw(new Texture(map), getX(), getY(), getWidth(), getHeight());
		}
	}
}
