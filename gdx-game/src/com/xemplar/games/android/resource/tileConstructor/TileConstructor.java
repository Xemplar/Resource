package com.xemplar.games.android.resource.tileConstructor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.resource.tiles.Tile;

public class TileConstructor{
	protected static final int N1 = 0x01;
	protected static final int N2 = 0x02;
	protected static final int N3 = 0x03;
	
	protected int select;
	
    public static float WIDTH = 1f;
    public static float HEIGHT = 1f;

    public String regionID;

    protected Vector2 position = new Vector2();
    protected Vector2 spawnPoint = new Vector2();
    protected Rectangle bounds = new Rectangle();
    
	public TileConstructor(String regionID) {
	    this.regionID = regionID;
	    this.bounds.width = WIDTH;
	    this.bounds.height = HEIGHT;
	    
	    select = N1;
	}

	public TileConstructor(String regionID, float size) {
	    this.regionID = regionID;
	    this.bounds.width = size;
	    this.bounds.height = size;
	    
	    select = N2;
	}

	public TileConstructor(String regionID, float width, float height) {
	    this.regionID = regionID;
	    this.bounds.width = width;
	    this.bounds.height = height;
	    
	    select = N3;
	}
	
	public Tile getTile(float x, float y){
		if(select == N1){
			return new Tile(new Vector2(x, y), regionID);
		} else if(select == N2){
			return new Tile(new Vector2(x, y), regionID, this.bounds.width);
		} else {
			return new Tile(new Vector2(x, y), regionID, this.bounds.width, this.bounds.height);
		}
	}
}
