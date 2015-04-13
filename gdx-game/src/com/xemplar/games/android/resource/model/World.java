package com.xemplar.games.android.resource.model;
import com.xemplar.games.android.resource.tiles.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.xemplar.games.android.resource.entities.*;

public class World {
    private Array<Rectangle> collisionRects = new Array<Rectangle>();
    private Array<Player> players = new Array<Player>();
    private Level level;
    private Jaxon jaxon;
    
    public Array<Rectangle> getCollisionRects() {
        return collisionRects;
    }

    public Jaxon getJaxon(){
        return (Jaxon) getPlayer(0);
    }
    
    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Level getLevel() {
        return level;
    }

    public Array<Entity> getEntities(){
        return level.getEntities();
    }

    public Tile getTile(Vector2 pos){
        Array<Tile> blocks = getTiles(level.getWidth(), level.getHeight());
        for(Tile block : blocks){
            if(block.getBounds().contains(pos)){
                return block;
            }
        }

        return null;
    }

    public Array<Tile> getTiles(int width, int height) {
        int x = (int)jaxon.getPosition().x - width;
        int y = (int)jaxon.getPosition().y - height;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }

        int x2 = x + 2 * width;
        int y2 = y + 2 * height;

        if (x2 > level.getWidth()) {
            x2 = level.getWidth() - 1;
        }
        if (y2 > level.getHeight()) {
            y2 = level.getHeight() - 1;
        }

        Array<Tile> tiles = new Array<Tile>();
        Tile tile = null;

        for (int col = x; col <= x2; col++) {
            for (int row = y; row <= y2; row++) {
                if(col >= level.getWidth()) continue;
                if(row >= level.getHeight()) continue;

                tile = level.getTiles()[col + row * level.getWidth()];
                if (tile != null) {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }

    public World(int levelNum){
        level = new Level(levelNum);
        jaxon = new Jaxon(level.jaxonStart);
        players.add(jaxon);
	}
}
