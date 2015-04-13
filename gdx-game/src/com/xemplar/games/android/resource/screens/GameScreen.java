package com.xemplar.games.android.resource.screens;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.xemplar.games.android.resource.model.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.tiles.*;
import com.xemplar.games.android.resource.view.*;
import com.xemplar.games.android.resource.controller.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.xemplar.games.android.resource.entities.*;

public class GameScreen implements Screen, InputProcessor {
    public static boolean useGameDebugRenderer = false;
    public static GameScreen instance;
    public static long gameTicks = 0L;
    private static TextureAtlas terrain;
    private static TextureAtlas hud;
    private ScreenButton left, right, up, down, attack;
    public World world;
    public float buttonSize = 0F;

    private Array<Tile> tiles;
    private static int levelNum;

    private WorldRenderer renderer;
    private PlayerController controller;
    private ShapeRenderer button;
    private SpriteBatch batch;
    private BitmapFont font;
    private int width, height;

    private TextureRegion controlLeft;
    private TextureRegion controlRight;
    private TextureRegion controlUp;
    private TextureRegion controlDown;

    public GameScreen(int level){
        instance = this;

        levelNum = level;

        terrain = new TextureAtlas(Gdx.files.internal("textures/terrain.pack"));

        font = new BitmapFont();

        if(level == -1){
            GameScreen.useGameDebugRenderer = true;
        } else {
            GameScreen.useGameDebugRenderer = false;
        }

        hud = new TextureAtlas(Gdx.files.internal("textures/ui.pack"));
        
        controlLeft = hud.findRegion("HUDLeft");
        controlRight = hud.findRegion("HUDLeft");
        controlRight.flip(true, false);
        controlUp = hud.findRegion("HUDJump");
        controlDown = hud.findRegion("HUDJump");
        controlDown.flip(false, true);

        world = new World(level);
        tiles = world.getTiles(world.getLevel().getWidth(), world.getLevel().getHeight());

        button = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    public void show() {
        gameTicks = 0L;

        renderer = new WorldRenderer(world, useGameDebugRenderer);
        controller = new PlayerController(world);
        controller.reset();
        Gdx.input.setInputProcessor(this);
    }

    public void render(float delta) {
        gameTicks++;

        long seconds = (long)((gameTicks / 60D) * 10L);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        controller.update(delta);
        updateEntities(delta);
        renderer.render();

        button.begin(ShapeRenderer.ShapeType.Filled);{
            if (Gdx.app.getType().equals(Application.ApplicationType.Android)){
                Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
                Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

                left.renderButton(button);
                right.renderButton(button);
                up.renderButton(button);
                down.renderButton(button);
                attack.renderButton(button);
            }

            world.getJaxon().inventory.renderGUI(button, width, height, buttonSize * 0.75F);
        } button.end();

        batch.begin(); {
            if (Gdx.app.getType().equals(Application.ApplicationType.Android)){
                left.renderText(batch);
                right.renderText(batch);
                up.renderText(batch);
                down.renderText(batch);
                attack.renderText(batch);
            }

            world.getJaxon().inventory.renderItems(batch, width, height, buttonSize * 0.75F);
            font.draw(batch, "Time: " + (seconds / 10D) + " ticks, FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - 10);
        } batch.end();
    }
    
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
        buttonSize = height / 6F;

        float[] colors = {0.5F, 0.5F, 0.5F, 0.5F};
        left = new ScreenButton(controlLeft, colors, buttonSize * (1F / 2F), buttonSize * (3F / 2F), buttonSize, buttonSize);
        right = new ScreenButton(controlRight, colors, buttonSize * (5F / 2F), buttonSize * (3F / 2F), buttonSize, buttonSize);
        up = new ScreenButton(controlUp, colors, buttonSize * (3F / 2F), buttonSize * (5F / 2F), buttonSize, buttonSize);
        down = new ScreenButton(controlDown, colors, buttonSize * (3F / 2F), buttonSize * (1F / 2F), buttonSize, buttonSize);

        attack = new ScreenButton(controlUp, colors, width - (buttonSize * (3F / 2F)), buttonSize * (1F / 2F), buttonSize, buttonSize);
    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void pause() {

    }

    public void resume() {
        //Resource.resource.setScreen(StartScreen.instance);
    }

    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    public boolean keyDown(int keycode) {
        if (keycode == Keys.W)
            controller.upPressed(-1);

        if (keycode == Keys.S)
            controller.downPressed(-1);
            
        if (keycode == Keys.A)
            controller.leftPressed(-1);

        if (keycode == Keys.D)
            controller.rightPressed(-1);

        if (keycode == Keys.PERIOD)
            controller.attackPressed(-1);

        if ((keycode == Keys.BACK) || (keycode == Keys.ESCAPE)){
            //finishLevel(ExitBlock.EXIT_NOCLEAR);
        }

        return true;
    }

    public boolean keyUp(int keycode) {
        if (keycode == Keys.W)
            controller.upReleased();

        if (keycode == Keys.S)
            controller.downReleased();

        if (keycode == Keys.A)
            controller.leftReleased();

        if (keycode == Keys.D)
            controller.rightReleased();

        if (keycode == Keys.PERIOD)
            controller.attackReleased();
        
        return true;
    }

    public boolean keyTyped(char character) {
        return false;
    }


    public boolean touchDown(int x, int y, int pointer, int button) {
        if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
            if(up.isInside(x, (y - height) * -1)){
                controller.upPressed(pointer);
            }

            if(down.isInside(x, (y - height) * -1)){
                controller.downPressed(pointer);
            }
            
            if(left.isInside(x, (y - height) * -1)){
                controller.leftPressed(pointer);
            }

            if(right.isInside(x, (y - height) * -1)){
                controller.rightPressed(pointer);
            }

            if(attack.isInside(x, (y - height) * -1)){
                controller.attackPressed(pointer);
            }

            return true;
        }
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
            if(up.isInside(x, (y - height) * -1)){
                controller.upReleased();
            }

            if(down.isInside(x, (y - height) * -1)){
                controller.downReleased();
            }

            if(left.isInside(x, (y - height) * -1)){
                controller.leftReleased();
            }

            if(right.isInside(x, (y - height) * -1)){
                controller.rightReleased();
            }

            if(attack.isInside(x, (y - height) * -1)){
                controller.attackReleased();
            }
            return true;
        }
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
            if(controller.isUpDown() && !up.isInside(x, (y - height) * -1) && controller.upPointer == pointer) {
                controller.upReleased();
            }
            if(controller.isDownDown() && !down.isInside(x, (y - height) * -1) && controller.downPointer == pointer) {
                controller.downReleased();
            }
            if(controller.isLeftDown() && !left.isInside(x, (y - height) * -1) && controller.leftPointer == pointer) {
                controller.leftReleased();
            }
            if(controller.isRightDown() && !right.isInside(x, (y - height) * -1) && controller.rightPointer == pointer) {
                controller.rightReleased();
            }
            if(controller.isAttackDown() && !attack.isInside(x, (y - height) * -1) && controller.attackPointer == pointer) {
                controller.attackReleased();
            }

            
            if(!controller.isUpDown() && up.isInside(x, (y -height) * -1) && controller.upPointer == -1){
                controller.upPressed(pointer);
            }
            if(!controller.isDownDown() && down.isInside(x, (y -height) * -1) && controller.downPointer == -1){
                controller.downPressed(pointer);
            }
            if(!controller.isLeftDown() && left.isInside(x, (y -height) * -1) && controller.leftPointer == -1){
                controller.leftPressed(pointer);
            }
            if(!controller.isRightDown() && right.isInside(x, (y -height) * -1) && controller.rightPointer == -1){
                controller.rightPressed(pointer);
            }
            if(!controller.isAttackDown() && attack.isInside(x, (y -height) * -1) && controller.attackPointer == -1){
                controller.attackPressed(pointer);
            }
            return true;
        }
        return false;
    }

    public boolean mouseMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    public void updateEntities(float delta){
        for(Entity e : world.getEntities()){
            e.update(delta);
        }
    }

    public static TextureAtlas getTextureAltlas(){
        return terrain;
	}
}