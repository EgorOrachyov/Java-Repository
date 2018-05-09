package WarShips;

import static WarShips.Common.*;

/**
 *  Field implements logic of simple war ships'  map and
 *  provides methods to set ship, to make fire,
 *  to surrender, to geg current score
 */
public class Field {

    public Field() {
        field = new int[FIELD_SIZE][FIELD_SIZE];
        notDestroyedSlotsCount = 0;
        score = 0;
        fires = 0;

        for(int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = SLOT_EMPTY;
            }
        }
    }

    public boolean setShip(Point2 p, int type, int orientation) {

        Point2 q;

        if (orientation == ORIENTATION_VERTICAL) {
            q = new Point2(p.getX() + (getShipLength(type) - 1), p.getY());
        }
        else if (orientation == ORIENTATION_HORIZONTAL) {
            q = new Point2(p.getX(), p.getY() + (getShipLength(type) - 1));
        }
        else {
            return false;
        }

        if (!isPositionCorrect(p.getX(), p.getY()) || !isPositionCorrect(q.getX(), q.getY())) {
            return false;
        }

        int left = 0;
        int right = FIELD_SIZE - 1;
        int up = 0;
        int down = FIELD_SIZE - 1;

        left = Math.max(left, p.getX() - INDENTATION);
        up = Math.max(up, p.getY() - INDENTATION);
        right = Math.min(right, q.getX() + INDENTATION);
        down = Math.min(down, q.getY() + INDENTATION);

        if (!isPlaceFree(left, right, up, down)) {
            return false;
        }

        fillPlace(left, right, up, down, SLOT_BUSY);
        fillPlace(p.getX(), q.getX(), p.getY(), q.getY(), SLOT_SHIPPED);

        notDestroyedSlotsCount += getShipLength(type);

        return true;
    }

    public boolean fire(Point2 p) {
        if (!isPositionCorrect(p.getX(), p.getY())) {
            return false;
        }

        lastFireStatus = SLOT_FIRED;

        switch (field[p.getX()][p.getY()]) {

            case SLOT_FIRED: case SLOT_DESTROYED:
                return false;

            case SLOT_EMPTY: case SLOT_BUSY:
                field[p.getX()][p.getY()] = SLOT_FIRED;
                fires += 1;
                break;

            case SLOT_SHIPPED:
                field[p.getX()][p.getY()] = SLOT_DESTROYED;
                notDestroyedSlotsCount -= 1;
                score += 1;
                fires += 1;
                lastFireStatus = SLOT_DESTROYED;
                break;

            default: return false;
        }

        return true;
    }

    public boolean allShipsDestroyed() {
        return (notDestroyedSlotsCount == 0);
    }

    public void surrender() {
        notDestroyedSlotsCount = 0;
    }

    public int getScore() {
        return score;
    }

    public int getFires() {
        return fires;
    }

    public int[][] getField() {
        return field.clone();
    }

    public int getLastFireStatus() {
        return lastFireStatus;
    }

    private boolean isPositionCorrect(int x, int y) {
        return (0 <= x && FIELD_SIZE > x &&
                0  <= y && FIELD_SIZE > y);
    }

    private boolean isPlaceFree(int left, int right, int up, int down) {
        for(int i = left; i <= right; i++) {
            for (int j = up; j <= down; j++) {
                if (field[i][j] == SLOT_SHIPPED) {
                    return false;
                }
            }
        }

        return true;
    }

    private void fillPlace(int left, int right, int up, int down, int slot_type) {
        for(int i = left; i <= right; i++) {
            for (int j = up; j <= down; j++) {
                field[i][j] = slot_type;
            }
        }
    }

    private int[][] field;
    private int notDestroyedSlotsCount;
    private int score;
    private int fires;
    private int lastFireStatus;

}
