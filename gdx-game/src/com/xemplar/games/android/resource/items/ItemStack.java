package com.xemplar.games.android.resource.items;

public class ItemStack {
	private Item item;
	private int count;
	private int max;
	
	public ItemStack(Item item, int initAmount){
		this.item = item;
		this.count = initAmount;
		this.max = item.maxCount;
	}
	
	public int getItems(int count){
		int pre = this.count;
		this.count -= count;
		
		if(this.count < 0){
			this.count = 0;
			
			return pre;
		}
		
		return count;
	}
	
	public int addItems(int count){
		int pre = this.count;
		
		this.count += count;
		
		if(this.count > max){
			this.count = max;
			
			return this.count - pre;
		}
		
		return count;
	}
	
	public Item getType(){
		return item;
	}
	
	public int getAmount(){
		return count;
	}
}
