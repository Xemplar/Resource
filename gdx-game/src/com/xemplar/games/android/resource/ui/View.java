package com.xemplar.games.android.resource.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class View {
	private Rectangle bounds;
	private Color bgColor;
	private Color borderColor;
	private Texture bg;
	
	public View(Texture bg, float x, float y, float width, float height){
		this.bg = bg;
		bounds = new Rectangle(x, y, width, height);
		
		Pixmap map = new Pixmap((int)bounds.width, (int)bounds.height, Pixmap.Format.RGBA8888);
		map.setColor(borderColor);
		map.fillRectangle(0, 0, map.getWidth(), map.getHeight());
		map.setColor(bgColor);
		map.drawRectangle(0, 0, map.getWidth(), map.getHeight());
		
		bg = new Texture(map);
	}
	
	public float getX(){
		return bounds.x;
	}
	
	public float getY(){
		return bounds.y;
	}
	
	public float getWidth(){
		return bounds.width;
	}
	
	public float getHeight(){
		return bounds.height;
	}
	
	public void setBorderColor(Color color){
		this.borderColor = color;
	}
	
	public void setBorderColor(int color){
		this.borderColor = new Color(color);
	}
	
	public void setBGColor(Color color){
		this.bgColor = color;
	}
	
	public void setBGColor(int color){
		this.bgColor = new Color(color);
	}
	
	public int getBGColorInt(){
		return Color.rgba8888(bgColor);
	}
	
	public Color getBGColor(){
		return bgColor;
	}
	
	public void render(SpriteBatch batch){
		batch.draw(bg, bounds.x, bounds.y, bounds.width, bounds.height);
	}
}
