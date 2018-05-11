package WarShips.InputManager;

import WarShips.Field;
import WarShips.Player;
import java.util.Scanner;

import static WarShips.Common.*;

/**
 *  Implements console in and out put
 */
public class ConsoleInputManager implements BasicInputManager {

    public ConsoleInputManager() {
        in = new Scanner(System.in);
    }

    public void outFieldHitsInfo(Field f) {
        int[][] field = f.getField();

        int j = 0;
        System.out.print("  ");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for(int[] row: field) {

            System.out.print(j + " ");

            for(int pos: row) {

                switch (pos) {

                    case SLOT_EMPTY: case SLOT_BUSY:
                        System.out.print("o ");
                        break;

                    case SLOT_SHIPPED:
                        System.out.print("■ ");
                        break;

                    case SLOT_FIRED:
                        System.out.print("* ");
                        break;

                    case SLOT_DESTROYED:
                        System.out.print("X ");
                        break;

                    default:
                }

            }
            System.out.println();
            j += 1;

        }
    }

    public void outFieldFiresInfo(Field f) {
        int[][] field = f.getField();

        int j = 0;
        System.out.print("  ");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for(int[] row: field) {

            System.out.print(j + " ");

            for(int pos: row) {

                switch (pos) {

                    case SLOT_SHIPPED:
                    case SLOT_BUSY:
                    case SLOT_EMPTY:
                        System.out.print("o ");
                        break;

                    case SLOT_FIRED:
                        System.out.print("* ");
                        break;

                    case SLOT_DESTROYED:
                        System.out.print("X ");
                        break;

                    default:
                }

            }
            System.out.println();
            j += 1;
        }
    }

    public void outPlayerInfo(Player p){
        System.out.print("Name: " + p.getName() + "\n");
        System.out.print("Last score: " + p.getScore() + "\n");
        System.out.print("Last fires: " + p.getFires() +"\n");
        System.out.print("Wins: " + p.getWinsToFalls()+ "\n");

    }

    public void outString(String s) {
        System.out.print(s);
    }

    public String getString() {
        return in.next();
    }

    public int getParam() {
        try {
            return Integer.valueOf(in.next());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public int[] getParams(int count) {
        if (count <= 0) {
            return null;
        }

        int[] a = new int[count];

        try {
            for(int i = 0; i < count; i++) {
                a[i] = Integer.valueOf(in.next());
            }

            return a;
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    private Scanner in;

}
