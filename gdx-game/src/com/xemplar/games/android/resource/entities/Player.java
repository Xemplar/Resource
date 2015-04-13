package com.xemplar.games.android.resource.entities;
import com.badlogic.gdx.math.*;

public abstract class Player extends Entity {
    public Player(Vector2 position, int health) {
        super(position, health);
    }

    public Player(Vector2 position, float size, int health) {
        super(position, size, health);
    }

    public Player(Vector2 position, float width, float height, int health) {
        super(position, width, height, health);
    }

    public Player(Vector2 position, String regionID, int health){
        super(position, regionID, health);
    }

    public Player(Vector2 position, String regionID, float size, int health){
        super(position, regionID, size, health);
    }

    public Player(Vector2 position, String regionID, float width, float height, int health){
        super(position, regionID, width, height, health);
    }
}
