package com.xemplar.games.android.resource.screens;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public class ScreenButton {
    private Rectangle bounds = new Rectangle();
    private boolean pressed, use;
    private String text = "";
    private float[] colors;
    private float[] pressedColors;
    public float x, y, height, width;
    private BitmapFont font;
    private TextureRegion tex;
    private int action;

    public ScreenButton(BitmapFont font, String text, float[] colors, float x, float y, float width, float height){
        this.bounds.set(x, y, width, height);
        this.text = text;
        this.colors = colors;
        this.font = font;
        this.use = false;

        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
    }

    public ScreenButton(BitmapFont font, String text, float[] colors, float[] pressedColors, float x, float y, float width, float height){
        this.bounds.set(x, y, width, height);
        this.text = text;
        this.colors = colors;
        this.pressedColors = pressedColors;
        this.font = font;
        this.use = true;

        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
    }

    public ScreenButton(TextureRegion tex, float[] colors, float x, float y, float width, float height){
        this.bounds.set(x, y, width, height);
        this.tex = tex;
        this.colors = colors;
        this.use = false;

        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
    }

    public ScreenButton(TextureRegion tex, float[] colors, float[] pressedColors, float x, float y, float width, float height){
        this.bounds.set(x, y, width, height);
        this.tex = tex;
        this.colors = colors;
        this.pressedColors = pressedColors;
        this.use = true;

        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
    }
    
    public ScreenButton setActionNumber(int action){
        this.action = action;

        return this;
    }

    public int getAction(){
        return action;
    }

    public ScreenButton setPressed(boolean pressed){
        this.pressed = pressed;

        return this;
    }

    public boolean isInside(Vector2 vec){
        return bounds.contains(vec);
    }

    public boolean isInside(float x, float y){
        return bounds.contains(x, y);
    }

    public void renderText(SpriteBatch batch){
        if(tex != null){
            batch.draw(tex, x, y, width, height);
        } else {
            font.draw(batch, text, bounds.getX() + (bounds.getHeight() * 2F), bounds.getY() + ((bounds.getHeight() / 2F) + (font.getLineHeight() / 4F)));
        }
    }

    public void renderButton(ShapeRenderer render){
        if(pressed && use){
            render.setColor(pressedColors[0], pressedColors[1], pressedColors[2], pressedColors[3]);
        } else {
            render.setColor(colors[0], colors[1], colors[2], colors[3]);
        }
        render.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
