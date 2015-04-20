package com.xemplar.games.android.resource.tiles.resources;

import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.resource.tiles.Tile;

public abstract class AbstractResource extends Tile{
	public AbstractResource(Vector2 pos, String regionID) {
		super(pos, regionID);
    }

    public AbstractResource(Vector2 pos, String regionID, float size) {
        this.regionID = regionID;
        this.spawnPoint = new Vector2(pos);
        this.position = pos;
        this.bounds.setPosition(pos);
        this.bounds.width = size;
        this.bounds.height = size;
    }

    public AbstractResource(Vector2 pos, String regionID, float width, float height) {
        this.regionID = regionID;
        this.spawnPoint = new Vector2(pos);
        this.position = pos;
        this.bounds.setPosition(pos);
        this.bounds.width = width;
        this.bounds.height = height;
    }
    
    public static enum ResourceType{
    	Wood,
    	Water,
    	Coal,
    	Stone,
    	Iron,
    	Dirt,
    	Gold,
    	Steel,
    	Copper,
    	Tin,
    	Silver,
    	Diamond,
    	Brass,
    	Bronze,
    	Tar;
    }
}
