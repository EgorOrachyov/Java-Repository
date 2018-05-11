package WarShips.Behavior;

import WarShips.Field;
import WarShips.Point2;

import static WarShips.Common.*;

/**
 *  Sets ships on game field for AI players by using
 *  random position generator
 */
public class ShipPositionGenerator {

    private static void setShipRandom(int count, int type, Field f) {

        int alreadySet = 0;
        while (alreadySet < count) {
            int x = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
            int y = (int)Math.round(Math.random() * (FIELD_SIZE - 1));
            int orientation = ORIENTATION_VERTICAL + (int)Math.round(Math.random());

            if (f.setShip(new Point2(x, y), type, orientation)) {
                alreadySet += 1;
            }
        }

    }

    public static void setShipsRandom(Field f) {

        setShipRandom(HEAVY_SHIP_COUNT, HEAVY_SHIP, f);
        setShipRandom(STANDARD_SHIP_COUNT, STANDARD_SHIP, f);
        setShipRandom(MIDDLE_SHIP_COUNT, MIDDLE_SHIP, f);
        setShipRandom(SMALL_SHIP_COUNT, SMALL_SHIP, f);
    }

}
