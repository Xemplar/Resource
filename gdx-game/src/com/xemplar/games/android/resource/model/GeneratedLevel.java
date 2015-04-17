package com.xemplar.games.android.resource.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.resource.tiles.Tile;

public class GeneratedLevel extends Level {
	public GeneratedLevel(int levelNum) {
		super(levelNum);
		
		overlay = generateTrees();
	}
	
	public Tile[] generateTrees(){
		int width = getWidth();
		int height = getHeight();
		
		int size = width * height;
		Tile[] tiles = new Tile[width * height];
		
		for(int i = 0; i < size; i++){
			Tile t = get(i);
			
			if(!t.isCollideable()){
				tiles[i] = (t);
			}
		}
		
		for(int i = 0; i < 10; i++){
			float randX = MathUtils.random(10) + 1;
			float randY = MathUtils.random(10) + 1;
			
			tiles.add(new Tile(new Vector2(randX, randY), "tree0"));
		}
		
		return tiles.toArray();
	}
	
	public void render(){
		
	}
}
