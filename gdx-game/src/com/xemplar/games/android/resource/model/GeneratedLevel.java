package com.xemplar.games.android.resource.model;

import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.resource.tiles.Tile;

public class GeneratedLevel extends Level {
	public GeneratedLevel(int levelNum) {
		super(levelNum);
	}
	
	public void generateTrees(){
		int width = getWidth();
		int height = getHeight();
		
		int size = width * height;
		Array<Tile> tiles = new Array<Tile>();
		
		for(int i = 0; i < size; i++){
			Tile t = get(i);
			
			if(!t.isCollideable()){
				tiles.add(t);
			}
		}
	}
}
