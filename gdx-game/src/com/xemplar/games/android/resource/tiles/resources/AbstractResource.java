package com.xemplar.games.android.resource.tiles.resources;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.xemplar.games.android.resource.items.ItemStack;
import com.xemplar.games.android.resource.tiles.Tile;

public abstract class AbstractResource extends Tile{
	public AbstractResource(Vector2 pos, ResourceType type) {
		super(pos, map.get(type));
    }

    public AbstractResource(Vector2 pos, ResourceType type, float size) {
    	super(pos, map.get(type), size);
    }

    public AbstractResource(Vector2 pos, ResourceType type, float width, float height) {
    	super(pos, map.get(type), width, height);
    }
    
    public abstract int collectTicks();
    public abstract ItemStack getItem();
    
    public boolean isCollideable(){
    	return true;
    }
    
    public static enum ResourceType{
    	Wood,
    	Water,
    	Coal,
    	Stone,
    	Dirt,
    	Ore,
    	Tar;
    }
    
    public static final ObjectMap<ResourceType, String> map = new ObjectMap<ResourceType, String>();
    
    static {
    	map.put(ResourceType.Wood, "tree0");
    	map.put(ResourceType.Water, "water0");
    	map.put(ResourceType.Coal, "coal0");
    	map.put(ResourceType.Stone, "stone0");
    	map.put(ResourceType.Dirt, "dirt0");
    	map.put(ResourceType.Tar, "tar0");
    }
}
