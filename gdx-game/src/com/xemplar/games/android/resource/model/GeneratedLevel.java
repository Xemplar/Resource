package com.xemplar.games.android.resource.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.resource.tiles.Tile;

public class GeneratedLevel extends Level {
	public GeneratedLevel(int levelNum) {
		super(levelNum);
	}
	
	protected Vector2 loadLevel(int num){
		Vector2 out = super.loadLevel(num);
		
		int width = getWidth();
		int height = getHeight();
		
		int size = width * height;
		
		for(int i = 0; i < size; i++){
			Tile t = get(i);
			
			int rand = MathUtils.random(10);
			
			if(!t.isCollideable() && rand == 1){
				int y = i / width;
				int x = i % width;
				
				overlay[i] = new Tile(new Vector2(y + 1, x + 1), "tree0");
			}
		}
		
		return out;
	}
}
