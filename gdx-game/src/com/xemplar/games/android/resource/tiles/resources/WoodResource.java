package com.xemplar.games.android.resource.tiles.resources;

import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.resource.items.ItemStack;

public class WoodResource extends AbstractResource{

	public WoodResource(Vector2 pos) {
		super(pos, AbstractResource.ResourceType.Wood);
	}
	
	public int collectTicks() {
		return 0;
	}

	public ItemStack getItem() {
		return null;
	}
	
}
