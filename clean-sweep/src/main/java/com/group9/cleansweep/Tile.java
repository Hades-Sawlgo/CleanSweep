package com.group9.cleansweep;


import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;


public class Tile {
	public static final int RIGHT_ID_INDEX = 0;
	public static final int LEFT_ID_INDEX = 1;
	public static final int TOP_ID_INDEX = 2;
	public static final int BOTTOM_ID_INDEX = 3;
	
	@Expose private String rightID;
	@Expose private String leftID;
	@Expose private String topID;
	@Expose private String bottomID;
	@Expose private boolean isObstacle;

	@Getter @Setter @Expose private String id;
	@Getter @Setter @Expose private String surfaceType;
	@Getter @Setter @Expose private int dirtAmount;
	@Getter @Setter @Expose private boolean isChargingStation;
	@Getter @Setter @Expose private boolean visited;
	@Getter @Setter private Tile rightNext;
	@Getter @Setter private Tile leftNext;
	@Getter @Setter private Tile topNext;
	@Getter @Setter private Tile bottomNext;

	public Tile() {
		this.id = null;
		this.surfaceType = null;
		this.leftNext = null;
		this.rightNext = null;
		this.topNext = null;
		this.bottomNext = null;
		this.dirtAmount = 0;
		this.isChargingStation = false;
		this.isObstacle = false;
		this.visited = false;
	}

	// This is used instead of a general getter to limit its used and encourage xxxxNext attributes.
	public String[] getSurroundingTileID(){
		String[] string = new String[4];
		
		string[RIGHT_ID_INDEX] = rightID;
		string[LEFT_ID_INDEX] = leftID;
		string[TOP_ID_INDEX] = topID;
		string[BOTTOM_ID_INDEX] = bottomID;
		
		return string;
	}

	public void setSurroundingTileID(Tile tile){
		if(tile.getRightNext() == null){
			this.rightID = null;
		} else{
			this.rightID = tile.getRightNext().id;
		}
		
		if(tile.getLeftNext() == null){
			this.leftID = null;
		} else{
			this.leftID = tile.getLeftNext().id;
		}
		
		if(tile.getTopNext() == null){
			this.topID = null;
		} else{
			this.topID = tile.getTopNext().id;
		}
		
		if(tile.getBottomNext() == null){
			this.bottomID = null;
		} else{
			this.bottomID = tile.getBottomNext().id;
		}
	}
	
	public boolean getObstacle() {
		return isObstacle;
	}

	public void setIsObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}
}
