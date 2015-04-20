package com.xemplar.games.android.resource.view;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.entities.*;
import com.xemplar.games.android.resource.model.*;
import com.xemplar.games.android.resource.screens.GameScreen;
import com.xemplar.games.android.resource.tiles.*;

public class WorldRenderer {
    public static final float CAMERA_WIDTH = 20f;
    public static final float CAMERA_HEIGHT = 12f;
    private static final float camera_height_mod = CAMERA_HEIGHT - 2;

    private World world;
    private OrthographicCamera cam;
    private Vector2 center = new Vector2();
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Inventory **/

    private SpriteBatch spriteBatch;

    private boolean debug = false;
    private float ppuX;
    private float ppuY;

    public WorldRenderer(World world, boolean debug) {
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, camera_height_mod / 2f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
    }

    public void render() {
        world.getJaxon().getBounds().getCenter(center);
        moveCamera(center.x, center.y + 1F);
        spriteBatch.setProjectionMatrix(cam.combined);

        spriteBatch.begin();
        drawBlocks();
        drawEntities();
        drawJaxon();
        spriteBatch.end();

        if (debug) drawDebug();
    }

    public void moveCamera(float x, float y){
    	
        float xCam = CAMERA_WIDTH / 2F;
        float yCam = camera_height_mod / 2F;

        if (x > (CAMERA_WIDTH / 2F)) {
            xCam = x;
        } if(x > (world.getLevel().getWidth() - (CAMERA_WIDTH / 2F))){
            xCam = world.getLevel().getWidth() - (CAMERA_WIDTH / 2F);
        }

        if (y > (camera_height_mod / 2F)) {
            yCam = y;
        } if(y > (world.getLevel().getHeight() - (camera_height_mod / 2F)) - 1){
            yCam = ((world.getLevel().getHeight()) - (camera_height_mod / 2F)) - 1;
        }

        cam.position.set(xCam, yCam + 1, 0);
        cam.update();
    }

    private void drawBlocks() {
    	Array<Tile> tiles = world.getTiles((int) CAMERA_WIDTH, (int) camera_height_mod);
    	Array<Tile> ovelays = world.getOverlayTiles((int) CAMERA_WIDTH, (int) camera_height_mod);
    	
    	for (Tile tile : tiles) {
            if(!tile.isHidden()){
                tile.render(spriteBatch, ppuX, ppuY);
            }
        }
    	
    	for (Tile tile : ovelays) {
            if(!tile.isHidden()){
                tile.render(spriteBatch, ppuX, ppuY);
            }
        }
    }

    private void drawJaxon() {
        Jaxon jaxon = world.getJaxon();

        jaxon.render(spriteBatch, ppuX, ppuY);
    }

    private void drawEntities(){
        Array<Entity> entites = world.getEntities();

        for(Entity entity : entites){
            entity.render(spriteBatch, ppuX, ppuY);
        }
    }

    private void drawDebug() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Tile tile : world.getTiles((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
            Rectangle rect = tile.getBounds();
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        
        for (Tile tile : world.getOverlayTiles((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
            Rectangle rect = tile.getBounds();
            debugRenderer.setColor(new Color(0, 0, 1, 1));
            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }

        Jaxon jaxon = world.getJaxon();
        Rectangle rect = jaxon.getBounds();
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        debugRenderer.end();
    }

    public void setSize (int w, int h) {
        ppuX = 1;
        ppuY = 1;
    }
}
