package com.group9.cleansweep;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Map;

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
	void setAndGet_SurroundingTileID_noArgs_TilesWithoutNulls_Test() {
		String[] axisX = {"a", "b", "c"};
		int axisYMin = 1;
		int axisYMax = axisYMin+2;
		
		FloorPlan floorPlan = FloorPlanTest.createSimpleFloorPlan_withoutAssignments(axisX, axisYMin, axisYMax);
		Map<String, Tile> roomLayout = floorPlan.getFloorPlanMap();
		
		int testTileXidx = 1;
		int testTileYval = axisYMin+1;
		Tile testTile = roomLayout.get(axisX[testTileXidx] + testTileYval);
		
		// the middle element is the second index in the x and y
		floorPlan.assignAdjacentTiles(testTile, testTileXidx, testTileYval);
		String[] testTileSurroundTileIds = testTile.getSurroundingTileID();
		
		assumeTrue(
				testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.TOP_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.LEFT_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.RIGHT_ID_INDEX] == null);
		
		testTile.setSurroundingTileID();
		testTileSurroundTileIds = testTile.getSurroundingTileID();

		assertEquals(testTile.getBottomNext().getId(), testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX]);
		assertEquals(testTile.getTopNext().getId(), testTileSurroundTileIds[Tile.TOP_ID_INDEX]);
		assertEquals(testTile.getLeftNext().getId(), testTileSurroundTileIds[Tile.LEFT_ID_INDEX]);
		assertEquals(testTile.getRightNext().getId(), testTileSurroundTileIds[Tile.RIGHT_ID_INDEX]);
	}

	@Test
	void setAndGet_SurroundingTileID_withArgs_TilesWithoutNulls_Test() {
		String[] axisX = {"a", "b", "c"};
		int axisYMin = 1;
		int axisYMax = axisYMin+2;
		
		FloorPlan floorPlan = FloorPlanTest.createSimpleFloorPlan_withoutAssignments(axisX, axisYMin, axisYMax);
		Map<String, Tile> roomLayout = floorPlan.getFloorPlanMap();
		
		int refTileXidx = 1;
		int refTileYval = axisYMin+1;
		Tile refTile = roomLayout.get(axisX[refTileXidx] + refTileYval);
		
		// the middle element is the second index in the x and y
		floorPlan.assignAdjacentTiles(refTile, refTileXidx, refTileYval);
		refTile.setSurroundingTileID();
		String[] refTileSurroundTileIds = refTile.getSurroundingTileID();
		
		Tile testTile = new Tile();
		String[] testTileSurroundTileIds = testTile.getSurroundingTileID();
		
		assumeTrue(
				refTileSurroundTileIds[Tile.BOTTOM_ID_INDEX] != null
				&& refTileSurroundTileIds[Tile.TOP_ID_INDEX] != null
				&& refTileSurroundTileIds[Tile.LEFT_ID_INDEX] != null
				&& refTileSurroundTileIds[Tile.RIGHT_ID_INDEX] != null);
		
		assumeTrue(
				testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.TOP_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.LEFT_ID_INDEX] == null
				&& testTileSurroundTileIds[Tile.RIGHT_ID_INDEX] == null);
		
		testTile.setSurroundingTileID(refTile);
		testTileSurroundTileIds = testTile.getSurroundingTileID();
		
		assertEquals(refTile.getBottomNext().getId(), testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX]);
		assertEquals(refTile.getTopNext().getId(), testTileSurroundTileIds[Tile.TOP_ID_INDEX]);
		assertEquals(refTile.getLeftNext().getId(), testTileSurroundTileIds[Tile.LEFT_ID_INDEX]);
		assertEquals(refTile.getRightNext().getId(), testTileSurroundTileIds[Tile.RIGHT_ID_INDEX]);
	}
	
	@Test
	void setAndGet_SurroundingTileID_noArgs_TilesWithAllNulls_Test() {		
		Tile testTile = new Tile();
		assumeTrue(
				testTile.getRightNext() == null
				&& testTile.getLeftNext() == null
				&& testTile.getTopNext() == null
				&& testTile.getBottomNext() == null);
		
		testTile.setSurroundingTileID();
		String[] testTileSurroundTileIds = testTile.getSurroundingTileID();
		
		assertNull(testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.TOP_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.LEFT_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.RIGHT_ID_INDEX]);
	}
	
	@Test
	void setAndGet_SurroundingTileID_withArgs_TilesWithAllNulls_Test() {		
		Tile refTile = new Tile();
		assumeTrue(
				refTile.getRightNext() == null
				&& refTile.getLeftNext() == null
				&& refTile.getTopNext() == null
				&& refTile.getBottomNext() == null);
		
		Tile testTile = new Tile();
		assumeTrue(
				testTile.getRightNext() == null
				&& testTile.getLeftNext() == null
				&& testTile.getTopNext() == null
				&& testTile.getBottomNext() == null);
		
		testTile.setSurroundingTileID(refTile);
		String[] testTileSurroundTileIds = testTile.getSurroundingTileID();
		
		assertNull(testTileSurroundTileIds[Tile.BOTTOM_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.TOP_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.LEFT_ID_INDEX]);
		assertNull(testTileSurroundTileIds[Tile.RIGHT_ID_INDEX]);
	}
}
