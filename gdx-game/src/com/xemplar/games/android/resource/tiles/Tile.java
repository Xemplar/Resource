package com.xemplar.games.android.resource.tiles;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.xemplar.games.android.resource.entities.*;
import com.xemplar.games.android.resource.screens.*;

public class Tile {
    public static float WIDTH = 1f;
    public static float HEIGHT = 1f;

    public String regionID;

    protected Vector2 position = new Vector2();
    protected Vector2 spawnPoint = new Vector2();
    protected Rectangle bounds = new Rectangle();
    
    public Tile(Vector2 pos, String regionID) {
        this.regionID = regionID;
        this.spawnPoint = new Vector2(pos);
        this.position = pos;
        this.bounds.setPosition(pos);
        this.bounds.width = WIDTH;
        this.bounds.height = HEIGHT;
    }

    public Tile(Vector2 pos, String regionID, float size) {
        this.regionID = regionID;
        this.spawnPoint = new Vector2(pos);
        this.position = pos;
        this.bounds.setPosition(pos);
        this.bounds.width = size;
        this.bounds.height = size;
    }

    public Tile(Vector2 pos, String regionID, float width, float height) {
        this.regionID = regionID;
        this.spawnPoint = new Vector2(pos);
        this.position = pos;
        this.bounds.setPosition(pos);
        this.bounds.width = width;
        this.bounds.height = height;
    }

    public float getWidth(){
        return this.bounds.width;
    }

    public float getHeight(){
        return this.bounds.height;
    }

    public boolean isCollideable(){
        return false;
    }

    public boolean isHidden(){
        return false;
    }

    public boolean isTouchable(){
        return false;
    }

    public boolean isAnimated(){
        return false;
    }

    public void initTouch(Entity e){

    }
    
    public void onTouch(Entity e){

    }

    public TextureRegion getTexture(){
        return GameScreen.getTextureAltlas().findRegion(regionID);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getSpawnPoint(){
        return spawnPoint;
    }

    public void render(SpriteBatch batch, float ppuX, float ppuY){
        if(!isHidden() && getTexture() != null){
            batch.draw(getTexture(), getPosition().x * ppuX, getPosition().y * ppuY, getWidth() * ppuX, getHeight() * ppuY);
        }   
    }

    public void render(TextureRegion region, SpriteBatch batch, float ppuX, float ppuY){
        if(!isHidden()){
            batch.draw(region, getPosition().x * ppuX, getPosition().y * ppuY, getWidth() * ppuX, getHeight() * ppuY);
        }   
    }
    
    public static enum Type{
		Grass,
		Dirt,
		Water,
		Lava,
		Abyss,
		Clift,
		Entity,
		Other;
	}
}
