package com.xemplar.games.android.resource.tileConstructor;

import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.resource.tiles.CollideableTile;
import com.xemplar.games.android.resource.tiles.Tile;

public class CollideableTileConstructor extends TileConstructor{
	public CollideableTileConstructor(String regionID) {
	    super(regionID);
	}

	public CollideableTileConstructor(String regionID, float size) {
	    super(regionID, size);
	}

	public CollideableTileConstructor(String regionID, float width, float height) {
	    super(regionID, width, height);
	}
	
	public Tile getTile(float x, float y){
		if(select == N1){
			return new CollideableTile(new Vector2(x, y), regionID);
		} else if(select == N2){
			return new CollideableTile(new Vector2(x, y), regionID, this.bounds.width);
		} else {
			return new CollideableTile(new Vector2(x, y), regionID, this.bounds.width, this.bounds.height);
		}
	}
}
