package com.xemplar.games.android.resource.tiles;
import com.badlogic.gdx.math.*;

public class CollideableTile extends Tile {
    public CollideableTile(Vector2 pos, String regionID) {
        super(pos, regionID);
    }

    public CollideableTile(Vector2 pos, String regionID, float size) {
        super(pos, regionID, size);
    }

    public CollideableTile(Vector2 pos, String regionID, float width, float height) {
        super(pos, regionID, width, height);
    }

    public boolean isCollideable() {
        return true;
    }
}
