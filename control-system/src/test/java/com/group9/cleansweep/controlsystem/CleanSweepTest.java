package com.group9.cleansweep.controlsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;

class CleanSweepTest {

	@Test
	void constructor_Test() {
		CleanSweep cleanSweep = new CleanSweep();
		
		assertEquals(0, cleanSweep.getVisitedListIndex());
		assertNull(cleanSweep.getNextTile());
		assertNotNull(cleanSweep.getPreviousTile());
	}
	
	@RepeatedTest(15)
	void doWorkTest() {
		Map<String, Tile> roomLayout = new HashMap<>();
		String[] axisX = new String []{"a", "b", "c", "d"};
		int axisYMin = 4;
		int axisYMax = 7;
		FloorPlan floorPlan = new FloorPlan(roomLayout, axisX, axisYMin, axisYMax);
		
		
		CleanSweep cleanSweep = new CleanSweep(floorPlan);
		try {
			cleanSweep.doWork();
		} catch (IOException e) {

			fail(e);
			e.printStackTrace();
		}
	}
	
	@Test
	void visitedTileUpdate_firstTile_Test() {
		CleanSweep cleanSweep = new CleanSweep();
		
		assumeTrue(cleanSweep.getNextTile() == null);
		assumeFalse(cleanSweep.getPreviousTile().equals(cleanSweep.getFirstTile()));
		
		cleanSweep.visitedTileUpdate();
		assertEquals(cleanSweep.getPreviousTile(), cleanSweep.getFirstTile());
	}
	
	@Test
	void visitedTileUpdate_allOtherTiles_revisitLimit_false_Test() {
		Tile prevTile = new Tile();
		String prevTileId = "d3";
		prevTile.setId(prevTileId);
		
		CleanSweep cleanSweep = new CleanSweep();
		
		String[] visitedList = new String[] {"a4", "b5", "d1", "c3", ""};
		cleanSweep.setVisitedList(visitedList);
		int VisitedListIndexVal = visitedList.length - 1;
		cleanSweep.setVisitedListIndex(VisitedListIndexVal);
		
		Tile nextTile = new Tile();
		nextTile.setId("a2");
		cleanSweep.setNextTile(nextTile);
		
		Tile firstTile = new Tile();
		firstTile.setId("e7");
		cleanSweep.setFirstTile(firstTile);
		
		cleanSweep.setPreviousTile(prevTile);
		
		// runs the code we want to check
		cleanSweep.visitedTileUpdate();
		
		// check the list was updated
		assertEquals(prevTileId, cleanSweep.getVisitedList()[VisitedListIndexVal]);
		
		// check the index was updated
		assertEquals(VisitedListIndexVal+1, cleanSweep.getVisitedListIndex());
		
		// Check that nextTile was not updated
		assertEquals(nextTile, cleanSweep.getNextTile());
		
		// Check that previousTile was updated
		assertEquals(cleanSweep.getNextTile(), cleanSweep.getPreviousTile());
		
		// Check that Tile isVisited attribute was updated
		assertTrue(prevTile.isVisited());
	}
	
	@Test
	void visitedTileUpdate_allOtherTiles_revisitLimit_true_Test() {
		Tile previousTile = new Tile();
		String prevTileId = "d3";
		previousTile.setId(prevTileId);
		
		Tile nextTile = new Tile();
		String nextTileId = "a2";
		nextTile.setId(nextTileId);
		
		
		CleanSweep cleanSweep = new CleanSweep();
		
		String[] visitedList = new String[] {"a4", nextTileId, "d1", "c3", ""};
		cleanSweep.setVisitedList(visitedList);
		int VisitedListIndexVal = visitedList.length - 1;
		cleanSweep.setVisitedListIndex(VisitedListIndexVal);
		
		cleanSweep.setNextTile(nextTile);
		cleanSweep.setPreviousTile(previousTile);
		
		Tile firstTile = new Tile();
		firstTile.setId("e7");
		cleanSweep.setFirstTile(firstTile);
		
		// runs the code we want to check
		cleanSweep.visitedTileUpdate();
		
		// check the list was updated
		assertEquals(prevTileId, cleanSweep.getVisitedList()[VisitedListIndexVal]);
		
		// check the index was updated
		assertEquals(VisitedListIndexVal+1, cleanSweep.getVisitedListIndex());
		
		// Check that nextTile was not updated
		assertEquals(nextTile, cleanSweep.getNextTile());
		
		// Check that previousTile was updated
		assertEquals(cleanSweep.getFirstTile(), cleanSweep.getPreviousTile());
	}
	
	@Test
	void hasReachedTileVisitLimit_true_Test() {
		Tile tile = new Tile();
		String tileId = "d3";
		tile.setId(tileId);
		
		CleanSweep cleanSweep = new CleanSweep();
		
		String[] visitedList = new String[] {"a4", "b5", tileId, "c3"};
		cleanSweep.setVisitedList(visitedList);
		cleanSweep.setVisitedListIndex(visitedList.length);
		
		assertTrue(cleanSweep.hasReachedTileVisitLimit(tile));
	}
	
	@Test
	void hasReachedTileVisitLimit_false_Test() {
		Tile tile = new Tile();
		String tileId = "d3";
		tile.setId(tileId);
		
		CleanSweep cleanSweep = new CleanSweep();
		
		String[] visitedList = new String[] {"a4", "b5", "d5", "c3"};
		cleanSweep.setVisitedList(visitedList);
		
		assertFalse(cleanSweep.hasReachedTileVisitLimit(tile));
	}
}
