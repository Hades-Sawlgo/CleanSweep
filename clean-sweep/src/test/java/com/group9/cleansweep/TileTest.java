package com.group9.cleansweep;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.group9.cleansweep.enums.TileTypeEnum;

class TileTest {

	@Test
	void constructorTest() {
		Tile tile = new Tile();
		
		assertEquals(null, tile.getId());
		assertEquals(null, tile.getSurfaceType());
		assertEquals(null, tile.getLeftNext());
		assertEquals(null, tile.getRightNext());
		assertEquals(null, tile.getTopNext());
		assertEquals(null, tile.getBottomNext());
		assertEquals(0, tile.getDirtAmount());
		assertEquals(TileTypeEnum.OPEN, tile.getTileType());
		assertEquals(false, tile.isVisited());
	}

	@Test
	void getSurroundingTileIDTest() {
		Tile tile = new Tile();
	}

	@Test
	void setSurroundingTileIDTest() {}

}
