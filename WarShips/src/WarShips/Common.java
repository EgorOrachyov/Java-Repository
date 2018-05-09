package WarShips;

public class Common {

    // Size for playing map and
    // indentation between ships
    public static final int FIELD_SIZE              = 10;
    public static final int INDENTATION             = 1;

    // Available ships types
    public static final int SMALL_SHIP              = 1;
    public static final int MIDDLE_SHIP             = 2;
    public static final int STANDARD_SHIP           = 3;
    public static final int HEAVY_SHIP              = 4;

    // Number of ships for each type
    public static final int SMALL_SHIP_COUNT        = 4;
    public static final int MIDDLE_SHIP_COUNT       = 3;
    public static final int STANDARD_SHIP_COUNT     = 2;
    public static final int HEAVY_SHIP_COUNT        = 1;

    // Id of available actions
    public static final int ACTION_DO_NOTHING       = 0;
    public static final int ACTION_SET_SHIP         = 1;
    public static final int ACTION_FIRE             = 2;
    public static final int ACTION_SURRENDER        = 3;

    // Orientation of ships
    public static final int ORIENTATION_VERTICAL    = 1;
    public static final int ORIENTATION_HORIZONTAL  = 2;

    // Flags to mark game field
    public static final int SLOT_EMPTY              = 0;
    public static final int SLOT_SHIPPED            = 1;
    public static final int SLOT_FIRED              = 2;
    public static final int SLOT_DESTROYED          = 3;
    public static final int SLOT_BUSY               = 4;

    // Status of operation flag
    public static final boolean STATUS_OK           = true;
    public static final boolean STATUS_NOT_OK       = false;

    public static int getShipLength(int type) {
        return type;
    }

}
