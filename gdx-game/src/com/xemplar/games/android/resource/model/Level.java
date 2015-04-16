package com.xemplar.games.android.resource.model;
import com.xemplar.games.android.resource.tileConstructor.CollideableTileConstructor;
import com.xemplar.games.android.resource.tileConstructor.TileConstructor;
import com.xemplar.games.android.resource.tiles.*;
import com.badlogic.gdx.utils.*;
import com.xemplar.games.android.resource.entities.*;
import com.badlogic.gdx.math.*;

import java.io.*;

import com.badlogic.gdx.files.*;
import com.badlogic.gdx.*;

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
    	System.out.println("Tex: " + id);
    	TileConstructor tile = tileName.get(id.trim());
    	
    	if(tile == null){
    		return;
    	}
    	
        tiles[x + y * width] = tile.getTile(x,  y);
    }

    public static ObjectMap<String, TileConstructor> tileName = new ObjectMap<String, TileConstructor>();

    static {
        tileName.put("g0", new TileConstructor("grass0"));
        tileName.put("g1", new TileConstructor("grass1"));
        tileName.put("g2", new TileConstructor("grass2"));

        tileName.put("gn", new TileConstructor("grass_n"));
        tileName.put("gne", new TileConstructor("grass_ne"));
        tileName.put("ge", new TileConstructor("grass_e"));
        tileName.put("gse", new TileConstructor("grass_se"));
        tileName.put("gs", new TileConstructor("grass_s"));
        tileName.put("gsw", new TileConstructor("grass_sw"));
        tileName.put("gw", new TileConstructor("grass_w"));
        tileName.put("gnw", new TileConstructor("grass_nw"));
        tileName.put("gm", new TileConstructor("grass_full"));

        tileName.put("d0", new TileConstructor("dirt0"));
        tileName.put("d1", new TileConstructor("dirt1"));
        tileName.put("d2", new TileConstructor("dirt2"));

        tileName.put("dn", new TileConstructor("dirt_n"));
        tileName.put("dne", new TileConstructor("dirt_ne"));
        tileName.put("de", new TileConstructor("dirt_e"));
        tileName.put("dse", new TileConstructor("dirt_se"));
        tileName.put("ds", new TileConstructor("dirt_s"));
        tileName.put("dsw", new TileConstructor("dirt_sw"));
        tileName.put("dw", new TileConstructor("dirt_w"));
        tileName.put("dnw", new TileConstructor("dirt_nw"));
        tileName.put("dm", new TileConstructor("dirt_full"));
        
        tileName.put("cf0", new CollideableTileConstructor("crystal_floor0"));
        tileName.put("cf1", new CollideableTileConstructor("crystal_floor1"));
        tileName.put("cf2", new CollideableTileConstructor("crystal_floor2"));
    }
}
