package com.xemplar.games.android.resource.entities;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.inventory.*;
import com.xemplar.games.android.resource.tiles.*;

public abstract class Entity extends Tile {
    public enum State {
        IDLE, WALKING, DYING
    }

    public enum Direction{
        FORWARD, BACKWARD, LEFT, RIGHT
    }
    
    public static final float SPEED = 5f;  // unit per second
    public static final int UNLIMITED = 0xF00000;
    protected int health = 0;
    protected int maxHealth;

    public Inventory inventory;
    private boolean hidden, reset;

    protected float stateTime = 0;
    protected Vector2 acceleration = new Vector2();
    protected Vector2 velocity = new Vector2();
    protected State state = State.IDLE;
    protected Direction dir;

    public Entity(Vector2 position, int health) {
        super(position, "tochLit");
        this.position = position;
        this.bounds.width = WIDTH;
        this.bounds.height = HEIGHT;
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        this.health = health;
        this.maxHealth = health;
    }

    public Entity(Vector2 position, float size, int health) {
        this(position, health);
        this.bounds.width = size;
        this.bounds.height = size;
    }

    public Entity(Vector2 position, float width, float height, int health) {
        this(position, health);
        this.bounds.width = width;
        this.bounds.height = height;
    }

    public Entity(Vector2 position, String regionID, int health){
        super(position, regionID);
        this.health = health;
        this.position = position;
        this.bounds.width = WIDTH;
        this.bounds.height = HEIGHT;
        this.bounds.x = position.x;
        this.bounds.y = position.y;

        this.regionID = regionID;
    }

    public Entity(Vector2 position, String regionID, float size, int health){
        this(position, regionID, health);
        this.bounds.width = size;
        this.bounds.height = size;
    }

    public Entity(Vector2 position, String regionID, float width, float height, int health){
        this(position, regionID, health);
        this.bounds.width = width;
        this.bounds.height = height;
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public void setDirection(Direction dir){
        this.dir = dir;
    }

    public Direction getDirection(){
        return dir;
    }

    public State getState(){
        return state;
    }

    public float getStateTime(){
        return stateTime;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setX(position.x);
        this.bounds.setY(position.y);
    }

    public void setCheckPoint(Vector2 point){
        this.spawnPoint = point.cpy();
        this.spawnPoint.add(0.025F, 0);
    }

    public void respawn(){
        this.health = maxHealth;
        velocity.set(0F, 0F);
        setPosition(spawnPoint.cpy());

        reset = false;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public Vector2 getAcceleration(){
        return acceleration;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void kill(){
        this.health = 0;
    }

    public void hurt(int amt){
        if(!isDead()){
            this.health = this.health - amt;
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public abstract boolean hasInventory();
    public abstract boolean hasInvSpace();
    public abstract void updateEntity(float delta);

    public void update(float delta){
        if(isDead()){
            if(hasInventory() && inventory.hasItems()){
                inventory.clear();
            }

            if(!reset){
                hidden = true;
                reset = true;
                Timer.schedule(run, 1F);
            }
        }
        
        stateTime += delta;
        
        if(!isHidden()){
            updateEntity(delta);
        }
    }

    Timer.Task run = new Timer.Task(){
        public void run(){
            hidden = false;
        }
    };
}
