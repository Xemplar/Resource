package com.xemplar.games.android.resource.entities;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;
import com.xemplar.games.android.resource.inventory.*;
import com.badlogic.gdx.math.*;

public class Jaxon extends Player{
    private static final float RUNNING_FRAME_DURATION = 0.06f;

    private TextureRegion jaxonIdleLeft;
    private TextureRegion jaxonIdleRight;
    private TextureRegion jaxonIdleUp;
    private TextureRegion jaxonIdleDown;
    private TextureRegion jaxonFrame;

    private float drawX;

    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation walkUpAnimation;
    private Animation walkDownAnimation;

    public Jaxon(Vector2 startPosistion){
        super(startPosistion, WIDTH * (4F / 5F), HEIGHT * (4F / 5F), 8);

        drawX = (getWidth() / 2F) - (WIDTH / 2F);
        drawX = (drawX < 0) ? -drawX : drawX;
        drawX = drawX / 4F;

        inventory = new Inventory(this, 4);
        loadTextures();
    }

    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/jaxon.pack"));

        jaxonIdleDown = atlas.findRegion("jaxonDown00");
        jaxonIdleUp = atlas.findRegion("jaxonUp00");
        jaxonIdleLeft = atlas.findRegion("jaxonLeft00");
        jaxonIdleRight = new TextureRegion(jaxonIdleLeft);
        jaxonIdleRight.flip(true, false);

        TextureRegion[] walkLeftFrames = new TextureRegion[4];

        walkLeftFrames[0] = atlas.findRegion("jaxonLeft00");
        walkLeftFrames[1] = atlas.findRegion("jaxonLeft01");
        walkLeftFrames[2] = atlas.findRegion("jaxonLeft00");
        walkLeftFrames[3] = atlas.findRegion("jaxonLeft02");

        walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
        
        TextureRegion[] walkUpFrames = new TextureRegion[4];

        walkUpFrames[0] = atlas.findRegion("jaxonUp00");
        walkUpFrames[1] = atlas.findRegion("jaxonUp01");
        walkUpFrames[2] = atlas.findRegion("jaxonUp00");
        walkUpFrames[3] = atlas.findRegion("jaxonUp02");

        walkUpAnimation = new Animation(RUNNING_FRAME_DURATION, walkUpFrames);
        
        
        TextureRegion[] walkDownFrames = new TextureRegion[4];

        walkDownFrames[0] = atlas.findRegion("jaxonDown00");
        walkDownFrames[1] = atlas.findRegion("jaxonDown01");
        walkDownFrames[2] = atlas.findRegion("jaxonDown00");
        walkDownFrames[3] = atlas.findRegion("jaxonDown02");

        walkDownAnimation = new Animation(RUNNING_FRAME_DURATION, walkDownFrames);
    }

    public boolean hasInventory() {
        return true;
    }

    public boolean hasInvSpace() {
        return inventory.hasSpace();
    }

    public void updateEntity(float delta) {
        if(isDead()){
            respawn();
        }
        
        position.mulAdd(velocity.cpy(), delta);
        bounds.x = position.x;
        bounds.y = position.y;

        if(getPosition().y < 0){
            kill();
        }
    }

    public TextureRegion getIdle(){
        if(getState().equals(Jaxon.State.WALKING)) {
            if(dir == Direction.LEFT){
                return walkLeftAnimation.getKeyFrame(getStateTime(), true);
            }
            if(dir == Direction.RIGHT){
                return walkRightAnimation.getKeyFrame(getStateTime(), true);
            }
            if(dir == Direction.FORWARD){
                return walkUpAnimation.getKeyFrame(getStateTime(), true);
            }
            if(dir == Direction.BACKWARD){
                return walkDownAnimation.getKeyFrame(getStateTime(), true);
            }
        } else {
            if(dir == Direction.LEFT){
                return jaxonIdleLeft;
            }
            if(dir == Direction.RIGHT){
                return jaxonIdleRight;
            }
            if(dir == Direction.FORWARD){
                return jaxonIdleUp;
            }
            if(dir == Direction.BACKWARD){
                return jaxonIdleDown;
            }
        }
        
        return jaxonIdleUp;
    }
    
    public void render(SpriteBatch batch, float ppuX, float ppuY) {
        jaxonFrame = getIdle();

        if(!isHidden()){
            batch.draw(jaxonFrame, (getPosition().x - drawX) * ppuX, getPosition().y * ppuY, (WIDTH / 2F) * ppuX, (HEIGHT / 2F) * ppuY);
        }
    }
}
