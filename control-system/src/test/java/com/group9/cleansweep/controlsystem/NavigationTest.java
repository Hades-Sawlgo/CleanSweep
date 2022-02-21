package com.group9.cleansweep.controlsystem;

import com.group9.cleansweep.FloorPlan;
import com.group9.cleansweep.Tile;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTest {

    @Test
    public void isObstacleBottomTest() {
        FloorPlan floorPlan = new FloorPlan();
        Navigation navigation = new Navigation(floorPlan);

        Tile primaryTile = new Tile();

        Tile externalTile = new Tile();
        externalTile.setIsObstacle(true);
        externalTile.setID("c3");

        primaryTile.setBottomNext(externalTile);
        assertEquals(ReflectionTestUtils.invokeMethod(navigation, "isObstacleBottom", primaryTile), true);
    }
}
