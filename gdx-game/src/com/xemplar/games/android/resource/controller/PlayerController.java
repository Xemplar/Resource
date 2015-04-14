package com.xemplar.games.android.resource.controller;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.tiles.*;
import com.xemplar.games.android.resource.model.*;
import com.xemplar.games.android.resource.entities.*;
import java.util.*;

public class PlayerController {
    enum Keys {
        LEFT, RIGHT, UP, DOWN, ATTACK
    }
    
    private static final float MAX_VEL = 4f;
    private static final float DAMP = 0.50f;
    private static final float ACCELERATION = 150f;

    private boolean isLeftDown;
    private boolean isRightDown;
    private boolean isUpDown;
    private boolean isDownDown;
    private boolean isAttackDown;

    public int leftPointer;
    public int rightPointer;
    public int downPointer;
    public int upPointer;
    public int attackPointer;

    private Array<Tile> collidable = new Array<Tile>();

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    private static Tile[] tiles;
    private static int length;

    private World world;
    private Jaxon jaxon;

    static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.ATTACK, false);
    };

    public PlayerController(World world) {
        this.world = world;
        this.jaxon = world.getJaxon();

        tiles = world.getLevel().getTiles();
        length = tiles.length;
    }

    public void leftPressed(int pointer) {
        keys.get(keys.put(Keys.LEFT, true));
        leftPointer = pointer;
        isLeftDown = true;
    }

    public void rightPressed(int pointer) {
        keys.get(keys.put(Keys.RIGHT, true));
        rightPointer = pointer;
        isRightDown = true;
    }

    public void upPressed(int pointer) {
        keys.get(keys.put(Keys.UP, true));
        upPointer = pointer;
        isUpDown = true;
    }

    public void downPressed(int pointer) {
        keys.get(keys.put(Keys.DOWN, true));
        downPointer = pointer;
        isDownDown = true;
    }
    
    public void attackPressed(int pointer) {
        keys.get(keys.put(Keys.ATTACK, true));
        attackPointer = pointer;
        isAttackDown = true;
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
        leftPointer = -1;
        isLeftDown = false;
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
        rightPointer = -1;
        isRightDown = false;
    }

    public void upReleased() {
        keys.get(keys.put(Keys.UP, false));
        upPointer = -1;
        isUpDown = false;
    }
    
    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
        downPointer = -1;
        isDownDown = false;
    }

    public void attackReleased() {
        keys.get(keys.put(Keys.ATTACK, false));
        attackPointer = -1;
        isAttackDown = false;
    }

    public void reset() {
        keys.get(keys.put(Keys.LEFT, false));
        leftPointer = -1;
        isLeftDown = false;

        keys.get(keys.put(Keys.RIGHT, false));
        rightPointer = -1;
        isRightDown = false;

        keys.get(keys.put(Keys.UP, false));
        upPointer = -1;
        isUpDown = false;
        
        keys.get(keys.put(Keys.DOWN, false));
        downPointer = -1;
        isDownDown = false;
        
        keys.get(keys.put(Keys.ATTACK, false));
        attackPointer = -1;
        isAttackDown = false;
    }

    public void update(float delta) {
        processInput();
        
        jaxon.getAcceleration().scl(delta);
        jaxon.getVelocity().add(jaxon.getAcceleration().x, jaxon.getAcceleration().y);
        checkCollisionWithBlocks(delta);
        jaxon.getVelocity().x *= DAMP;
        jaxon.getVelocity().y *= DAMP;

        if (jaxon.getVelocity().x > MAX_VEL) {
            jaxon.getVelocity().x = MAX_VEL;
        }

        if (jaxon.getVelocity().x < -MAX_VEL) {
            jaxon.getVelocity().x = -MAX_VEL;
        }

        if (jaxon.getVelocity().y > MAX_VEL) {
            jaxon.getVelocity().y = MAX_VEL;
        }

        if (jaxon.getVelocity().y < -MAX_VEL) {
            jaxon.getVelocity().y = -MAX_VEL;
        }
        
        jaxon.update(delta);
    }

    private void checkCollisionWithBlocks(float delta) {
        jaxon.getVelocity().scl(delta);
        Rectangle jaxonRect = rectPool.obtain();
        jaxonRect.set(jaxon.getBounds().x, jaxon.getBounds().y, jaxon.getBounds().width, jaxon.getBounds().height);

        populateCollidableBlocks();
        jaxonRect.x += jaxon.getVelocity().x;
        world.getCollisionRects().clear();

        for (Tile tile : collidable) {
            if (tile == null) continue;

            if (jaxonRect.overlaps(tile.getBounds()) && (tile.isTouchable())) {
                tile.onTouch(jaxon);
            }

            if (jaxonRect.overlaps(tile.getBounds()) && tile.isCollideable()) {
                jaxon.getVelocity().x = 0;
                world.getCollisionRects().add(tile.getBounds());

                if (jaxon.getBounds().overlaps(tile.getBounds())) {
                    float jaxonX = jaxon.getPosition().x;
                    float blockX = tile.getPosition().x;

                    System.out.println("got stuck");

                    if (jaxonX < blockX) {
                        jaxon.getPosition().x = tile.getPosition().x - jaxon.getWidth();
                    } else {
                        jaxon.getPosition().x = tile.getPosition().x + tile.getWidth();
                    }
                }
                break;
            }
        }

        populateCollidableBlocks();
        jaxonRect.y += jaxon.getVelocity().y;
        world.getCollisionRects().clear();

        for (Tile tile : collidable) {
            if (tile == null) continue;

            if (jaxonRect.overlaps(tile.getBounds()) && (tile.isTouchable())) {
                tile.onTouch(jaxon);
            }

            if (jaxonRect.overlaps(tile.getBounds()) && tile.isCollideable()) {
                jaxon.getVelocity().y = 0;
                world.getCollisionRects().add(tile.getBounds());

                if (jaxon.getBounds().overlaps(tile.getBounds())) {
                    float jaxonY = jaxon.getPosition().y;
                    float blockY = tile.getPosition().y;

                    System.out.println("got stuck");

                    if (jaxonY < blockY) {
                        jaxon.getPosition().y = tile.getPosition().y - jaxon.getHeight();
                    } else {
                        jaxon.getPosition().y = tile.getPosition().y + tile.getHeight();
                    }
                }
                break;
            }
        }

        jaxon.getVelocity().scl(1 / delta);
    }

    private void populateCollidableBlocks() {
        collidable.clear();

        Vector2 pos = jaxon.getPosition().cpy().add(jaxon.getVelocity().cpy());

        for (int i = 0; i < length; i++) {
            Tile current = tiles[i];

            if (current != null) {
                if (current.isCollideable()) {
                    float xDist = Math.abs(current.getPosition().x - pos.x);
                    float yDist = Math.abs(current.getPosition().y - pos.y);

                    if (xDist < 1F && yDist < 1F) {
                        collidable.add(current);
                    }
                } else if (tiles[i].isTouchable()) {
                    float xDist = Math.abs(current.getPosition().x - pos.x);
                    float yDist = Math.abs(current.getPosition().y - pos.y);

                    if (xDist < 1F && yDist < 1F) {
                        collidable.add(current);
                    }
                }
            }
        }

        int size = world.getEntities().size;

        for(int i = 0; i < size; i++){
            Entity current = world.getEntities().get(i);

            if (current.isCollideable()) {
                float xDist = Math.abs(current.getPosition().x - pos.x);
                float yDist = Math.abs(current.getPosition().y - pos.y);

                if (xDist < 1F && yDist < 1F) {
                    collidable.add(current);
                }
            } else if (current.isTouchable()) {
                float xDist = Math.abs(current.getPosition().x - pos.x);
                float yDist = Math.abs(current.getPosition().y - pos.y);

                if (xDist < 1F && yDist < 1F) {
                    collidable.add(current);
                }
            }
        }
    }


    private boolean processInput() {
    	boolean hzPressed = false;
    	boolean vrPressed = false;
    	
        if (keys.get(Keys.LEFT)) {
            jaxon.setDirection(Entity.Direction.LEFT);
            jaxon.getAcceleration().x = -ACCELERATION;
            
            hzPressed = true;
            
            jaxon.setState(Entity.State.WALKING);
        } if (keys.get(Keys.RIGHT)) {
            jaxon.setDirection(Entity.Direction.RIGHT);
            jaxon.getAcceleration().x = ACCELERATION;
            
            hzPressed = true;
            
            jaxon.setState(Entity.State.WALKING);
        } if (keys.get(Keys.UP)) {
            jaxon.setDirection(Entity.Direction.FORWARD);
            jaxon.getAcceleration().y = ACCELERATION;
            
            vrPressed = true;
            
            jaxon.setState(Entity.State.WALKING);
        } if (keys.get(Keys.DOWN)) {
            jaxon.setDirection(Entity.Direction.BACKWARD);
            jaxon.getAcceleration().y = -ACCELERATION;
            
            vrPressed = true;
            
            jaxon.setState(Entity.State.WALKING);
        } if(!hzPressed || !vrPressed) {
        	if(!hzPressed){
        		jaxon.getAcceleration().x = 0;
        	}
        	
        	if(!vrPressed){
        		jaxon.getAcceleration().y = 0;
        	}
        	
            jaxon.setState(Entity.State.IDLE);
        }
        return false;
    }

    public boolean isLeftDown() {
        return isLeftDown;
    }

    public boolean isRightDown() {
        return isRightDown;
    }

    public boolean isUpDown() {
        return isUpDown;
    }
    
    public boolean isDownDown() {
        return isDownDown;
    }

    public boolean isAttackDown() {
        return isAttackDown;
	}
}
