package com.xemplar.games.android.resource.tiles;
import com.badlogic.gdx.math.*;
import com.xemplar.games.android.resource.entities.*;
import com.xemplar.games.android.resource.items.*;

public class ItemTile extends Tile{
    private Item item;
    private boolean canBeTaken = true;

    public ItemTile(Vector2 pos, Item item){
        super(pos, item.regionID);
        this.item = item;
        this.item.setBlock(this);
    }

    public ItemTile(Vector2 pos, float size, Item item){
        super(pos, item.regionID);
        this.bounds.width = size;
        this.bounds.height = size;
        this.item = item;
        this.item.setBlock(this);
    }

    public ItemTile(Vector2 pos, float width, float height, Item item){
        super(pos, item.regionID);
        this.bounds.width = width;
        this.bounds.height = height;
        this.item = item;
        this.item.setBlock(this);
    }

    public boolean isCollideable() {
        return false;
    }

    public boolean isHidden(){
        return !canBeTaken;
    }

    public boolean isTouchable() {
        return canBeTaken;
    }

    public void returnItem(){
        canBeTaken = true;
    }

    public void onTouch(Entity e){
        if(e.hasInventory() && e.hasInvSpace() && canBeTaken){
            e.inventory.addItem(item);
            canBeTaken = false;
        }
    }
}
