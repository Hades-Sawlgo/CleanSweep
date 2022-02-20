package com.group9.cleansweep;


import com.google.gson.annotations.Expose;

public class Tile {
	@Expose private String id;
	@Expose private String surfaceType;
	@Expose private Boolean isObstacle;
	@Expose private int dirtAmount;
	@Expose private boolean isChargingStation;
	@Expose private boolean visited;
	@Expose private String rightID;
	@Expose private String leftID;
	@Expose private String topID;
	@Expose private String bottomID;
	private Tile  rightNext;
	private Tile leftNext;
	private Tile topNext;
	private Tile bottomNext;

	
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

	public String[] getSurroundingTileID(){
		return new String[]{rightID, leftID, topID, bottomID};
	}

	public void setSurroundingTileID(Tile tile){
		try{
			if(tile.getRightNext() == null){
				this.rightID = null;
			} else{
				this.rightID = tile.getRightNext().id;
			}
		} catch(Exception e){
			//ignore NPE if thrown
		}
		try{
			if(tile.getLeftNext() == null){
				this.leftID = null;
			} else{
				this.leftID = tile.getLeftNext().id;
			}
		}catch(Exception e){
			//ignore NPE if thrown
		}
		try{
			if(tile.getTopNext() == null){
				this.topID = null;
			} else{
				this.topID = tile.getTopNext().id;
			}
		}catch(Exception e){
			//ignore NPE if thrown
		}
		try{
			if(tile.getBottomNext() == null){
				this.bottomID = null;
			} else{
				this.bottomID = tile.getBottomNext().id;
			}
		}catch(Exception e){
			//ignore NPE if thrown
		}

	}

	public void setSurfaceType(String surfaceType){
		this.surfaceType = surfaceType;
	}

	public void setLeftNext(Tile leftNext){
		this.leftNext = leftNext;
	}

	public void setRightNext(Tile rightTile){
		rightNext = rightTile;
	}

	public void setTopNext(Tile topNext){
		this.topNext = topNext;
	}

	public void setBottomNext(Tile bottomNext){
		this.bottomNext = bottomNext;
	}

	public void setDirtAmount(int dirtAmount){
		this.dirtAmount = dirtAmount;
	}

	public void setChargingStation(Boolean isChargingStation){
		this.isChargingStation = isChargingStation;
	}

	public void setIsObstacle(Boolean isObstacle){
		this.isObstacle = isObstacle;
	}

	public void setID(String id){
		this.id = id;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	public String getSurfaceType() {
		return surfaceType;
	}

	public int getDirtAmount() {
		return dirtAmount;
	}

	public boolean isChargingStation() {
		return isChargingStation;
	}

	public boolean isVisited() {
		return visited;
	}

	public Tile getRightNext() {
		return rightNext;
	}

	public Tile getLeftNext() {
		return leftNext;
	}

	public Tile getTopNext() {
		return topNext;
	}

	public Tile getBottomNext() {
		return bottomNext;
	}

	public String getId() {
		return id;
	}

	public Boolean getObstacle() {
		return isObstacle;
	}


}
