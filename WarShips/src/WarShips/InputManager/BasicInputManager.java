package WarShips.InputManager;

import WarShips.Field;
import WarShips.Player;

public interface BasicInputManager {

    void outFieldHitsInfo(Field f);

    void outFieldFiresInfo(Field f);

    void outPlayerInfo(Player p);

    void outString(String s);

    String getString();

    int getParam();

    int[] getParams(int count);

}
