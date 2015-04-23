package com.xemplar.games.android.resource.items;

import com.xemplar.games.android.resource.entities.*;
import com.xemplar.games.android.resource.tiles.*;

public class Item {
    public static Item BLUE_KEY = new Item(0, "keyBlue", 1);
    public static Item RED_KEY = new Item(1, "keyRed", 1);
    public static Item GREEN_KEY = new Item(2, "keyGreen", 1);
    public static Item YELLOW_KEY = new Item(3, "keyYellow", 1);

    public int id;
    public int maxCount;
    public String regionID;

    private ItemTile block;

    private Item(int id, String regionID, int maxStack){
        this.id = id;
        this.regionID = regionID;
        this.maxCount = maxStack;
    }
    
    public Item clone(){
        return new Item(id, regionID, maxCount);
    }

    public void setBlock(ItemTile b){
        this.block = b;
    }

    public void returnToBlock(Entity e){
        e.inventory.removeItem(e.inventory.invHasItem(this));
        if(block != null){
            block.returnItem();
        }
    }

    public String getRegionID(){
        return regionID;
	}
}
