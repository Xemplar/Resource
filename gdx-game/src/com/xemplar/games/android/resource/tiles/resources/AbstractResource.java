package com.xemplar.games.android.resource.tiles.resources;

import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.resource.tiles.Tile;

public abstract class AbstractResource extends Tile{
	public AbstractResource(Vector2 pos, String regionID) {
		super(pos, regionID);
    }

    public AbstractResource(Vector2 pos, String regionID, float size) {
    	super(pos, regionID, size);
    }

    public AbstractResource(Vector2 pos, String regionID, float width, float height) {
    	super(pos, regionID, width, height);
    }
    
    public abstract int collectTicks();
    
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
