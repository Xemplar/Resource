package com.xemplar.games.android.resource.model;
import com.xemplar.games.android.resource.tiles.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.entities.*;
import com.badlogic.gdx.math.*;
import java.io.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.*;
import com.xemplar.games.android.resource.*;

public class Level {
    public Vector2 jaxonStart;
    private int width;
    private int height;

    private Tile[] tiles;

    private Array<Entity> entities;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setEntites(Array<Entity> entities){
        this.entities = entities;
    }

    public Array<Entity> getEntities(){
        return entities;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setBlocks(Tile[] tiles) {
        this.tiles = tiles;
    }
    
    public Level(int levelNum){
        jaxonStart = loadLevel(levelNum);
    }

    public Tile get(int i) {
        return tiles[i];
    }

    private Vector2 loadLevel(int num){
        Vector2 value = new Vector2(1, 1);

        width = 50;
        height = 7;

        if(num == -1){
            File file = new File(Gdx.files.getExternalStoragePath(), "levelExp.txt");

            FileHandle handle = new FileHandle(file);

            value = loadFile(handle);
        } else {
            String fileName = num + "";
            if(num < 100){
                fileName = "0" + num;
            }
            if(num < 10){
                fileName = "00" + num;
            }

            FileHandle handle = Gdx.files.internal("levels/level" + fileName + ".rlf");
            value = loadFile(handle);
        }

        value.add(0.25F, 0);

        return value;
    }

    public Vector2 loadFile(FileHandle handle){
        Vector2 value = new Vector2(1, 1);

        String lines = handle.readString(); 
        
        String[] rows = lines.split("\n");

        width = rows[0].split(",").length;
        height = rows.length;

        setupLevel(width, height);

        String[][] ids = new String[height][];

        for(int row = 0; row < height; row++){
            String[] line = rows[row].split(",");
            ids[row] = new String[width];
            for(int col = 0; col < width; col++){
                ids[row][col] = line[col];

                if(ids[row][col].equals("s")){
                    value = new Vector2(col, (row - height) * -1);
                } else if(ids[row][col].equals("00") || ids[row][col].equals("0")){
                    continue;
                } else {
                    addTile(col, (row - (height - 1)) * -1, ids[row][col]);
                }
            }
        }

        return value;
    }

    public void setupLevel(int width, int height){
        entities = new Array<Entity>();
        
        tiles = new Tile[width * height];

        for (int index = 0; index < tiles.length; index++) {
            tiles[index] = null;
        }
    }

    private void addTile(int x, int y, String id){
        Tile tile;
        
        if(id.startsWith("cf")){
            tile = new CollideableTile(new Vector2(x, y), tileName.get(id));
        } else {
            tile = new Tile(new Vector2(x, y), tileName.get(id));
        }
        tiles[x + y * width] = tile;
    }

    public static ObjectMap<String, String> tileName = new ObjectMap<String, String>();

    static {
        tileName.put("g0", "grass0");
        tileName.put("g1", "grass1");
        tileName.put("g2", "grass2");

        tileName.put("gn", "grass_n");
        tileName.put("gne", "grass_ne");
        tileName.put("ge", "grass_e");
        tileName.put("gse", "grass_se");
        tileName.put("gs", "grass_s");
        tileName.put("gsw", "grass_sw");
        tileName.put("gw", "grass_w");
        tileName.put("gnw", "grass_nw");
        tileName.put("gm", "grass_full");

        tileName.put("d0", "dirt0");
        tileName.put("d1", "dirt1");
        tileName.put("d2", "dirt2");

        tileName.put("dn", "dirt_n");
        tileName.put("dne", "dirt_ne");
        tileName.put("de", "dirt_e");
        tileName.put("dse", "dirt_se");
        tileName.put("ds", "dirt_s");
        tileName.put("dsw", "dirt_sw");
        tileName.put("dw", "dirt_w");
        tileName.put("dnw", "dirt_nw");
        tileName.put("dm", "dirt_full");
        
        tileName.put("cf0", "crystal_floor0");
        tileName.put("cf1", "crystal_floor1");
        tileName.put("cf2", "crystal_floor2");
    }
    
    private enum Type{
        Block,
        Lock,
        Item;
    }
}
