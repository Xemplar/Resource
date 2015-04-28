package com.xemplar.games.android.resource.tiles.resources;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.xemplar.games.android.resource.entities.Entity;
import com.xemplar.games.android.resource.entities.Player;
import com.xemplar.games.android.resource.items.ItemStack;
import com.xemplar.games.android.resource.screens.GameScreen;
import com.xemplar.games.android.resource.tiles.Tile;
import com.xemplar.games.android.resource.ui.ProgressReporter;

public abstract class AbstractResource extends Tile{
	private static int count = 0;
	private int id;
	private boolean set, still;
	
	public AbstractResource(Vector2 pos, ResourceType type) {
		super(pos, map.get(type));
		this.id = new Integer(count);
		count++;
    }

    public AbstractResource(Vector2 pos, ResourceType type, float size) {
    	super(pos, map.get(type), size);
    	this.id = new Integer(count);
    	count++;
    }

    public AbstractResource(Vector2 pos, ResourceType type, float width, float height) {
    	super(pos, map.get(type), width, height);
    	this.id = new Integer(count);
    	count++;
    }
    
    public abstract int collectTicks();
    public abstract ItemStack getItem();
    
    public boolean isCollideable(){
    	return true;
    }
    
    public boolean isTouchable(){
    	return true;
    }
    
    public void onTouch(Entity e){
    	if(e instanceof Player){
    		if(!set){
    			set = true;
    		
    			ProgressReporter report = new ProgressReporter(){
    				public void postProgress(long progress) {
    					System.out.println("progress " + progress);
    				}

    				public void onFinish() {
    					GameScreen.taskFinished(id + "");
    					System.out.println("finish");
    				}
    				
    				public void onStart() {
    					System.out.println("start");
    				}
        		};
        	
        		GameScreen.postTask(id + "", report, collectTicks());
    		}
    	}
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
