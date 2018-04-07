package SourceMap;

import java.util.HashMap;

public class Base64 {
    public Base64() {
        CharToInt = new HashMap<>();
        IntToChar = new HashMap<>();

        int alphabetSize = 26;

        for(int i = 0; i < alphabetSize; i++) {
            CharToInt.put(Character.valueOf('='), 0);
        }

        CharToInt.put('A', 0x00);
        CharToInt.put('B', 0x01);
        CharToInt.put('C', 0x02);
        CharToInt.put('D', 0x03);
        CharToInt.put('E', 0x04);
        CharToInt.put('F', 0x05);
        CharToInt.put('G', 0x06);
        CharToInt.put('H', 0x07);
        CharToInt.put('I', 0x08);
        CharToInt.put('J', 0x09);
        CharToInt.put('K', 0x0A);
        CharToInt.put('L', 0x0B);
        CharToInt.put('M', 0x0C);
        CharToInt.put('N', 0x0D);
        CharToInt.put('O', 0x0E);
        CharToInt.put('P', 0x0F);
        CharToInt.put('Q', 0x10);
        CharToInt.put('R', 0x11);
        CharToInt.put('S', 0x12);
        CharToInt.put('T', 0x13);
        CharToInt.put('U', 0x14);
        CharToInt.put('V', 0x15);
        CharToInt.put('W', 0x16);
        CharToInt.put('X', 0x17);
        CharToInt.put('Y', 0x18);
        CharToInt.put('Z', 0x19);
        CharToInt.put('a', 0x1A);
        CharToInt.put('b', 0x1B);
        CharToInt.put('c', 0x1C);
        CharToInt.put('d', 0x1D);
        CharToInt.put('e', 0x1E);
        CharToInt.put('f', 0x1F);
        CharToInt.put('g', 0x20);
        CharToInt.put('h', 0x21);
        CharToInt.put('i', 0x22);
        CharToInt.put('j', 0x23);
        CharToInt.put('k', 0x24);
        CharToInt.put('l', 0x25);
        CharToInt.put('m', 0x26);
        CharToInt.put('n', 0x27);
        CharToInt.put('o', 0x28);
        CharToInt.put('p', 0x29);
        CharToInt.put('q', 0x2A);
        CharToInt.put('r', 0x2B);
        CharToInt.put('s', 0x2C);
        CharToInt.put('t', 0x2D);
        CharToInt.put('u', 0x2E);
        CharToInt.put('v', 0x2F);
        CharToInt.put('w', 0x30);
        CharToInt.put('x', 0x31);
        CharToInt.put('y', 0x32);
        CharToInt.put('z', 0x33);
        CharToInt.put('0', 0x34);
        CharToInt.put('1', 0x35);
        CharToInt.put('2', 0x36);
        CharToInt.put('3', 0x37);
        CharToInt.put('4', 0x38);
        CharToInt.put('5', 0x39);
        CharToInt.put('6', 0x3A);
        CharToInt.put('7', 0x3B);
        CharToInt.put('8', 0x3C);
        CharToInt.put('9', 0x3D);
        CharToInt.put('+', 0x3E);
        CharToInt.put('/', 0x3F);

        IntToChar.put(0x00, 'A');
        IntToChar.put(0x01, 'B');
        IntToChar.put(0x02, 'C');
        IntToChar.put(0x03, 'D');
        IntToChar.put(0x04, 'E');
        IntToChar.put(0x05, 'F');
        IntToChar.put(0x06, 'G');
        IntToChar.put(0x07, 'H');
        IntToChar.put(0x08, 'I');
        IntToChar.put(0x09, 'J');
        IntToChar.put(0x0A, 'K');
        IntToChar.put(0x0B, 'L');
        IntToChar.put(0x0C, 'M');
        IntToChar.put(0x0D, 'N');
        IntToChar.put(0x0E, 'O');
        IntToChar.put(0x0F, 'P');
        IntToChar.put(0x10, 'Q');
        IntToChar.put(0x11, 'R');
        IntToChar.put(0x12, 'S');
        IntToChar.put(0x13, 'T');
        IntToChar.put(0x14, 'U');
        IntToChar.put(0x15, 'V');
        IntToChar.put(0x16, 'W');
        IntToChar.put(0x17, 'X');
        IntToChar.put(0x18, 'Y');
        IntToChar.put(0x19, 'Z');
        IntToChar.put(0x1A, 'a');
        IntToChar.put(0x1B, 'b');
        IntToChar.put(0x1C, 'c');
        IntToChar.put(0x1D, 'd');
        IntToChar.put(0x1E, 'e');
        IntToChar.put(0x1F, 'f');
        IntToChar.put(0x20, 'g');
        IntToChar.put(0x21, 'h');
        IntToChar.put(0x22, 'i');
        IntToChar.put(0x23, 'j');
        IntToChar.put(0x24, 'k');
        IntToChar.put(0x25, 'l');
        IntToChar.put(0x26, 'm');
        IntToChar.put(0x27, 'n');
        IntToChar.put(0x28, 'o');
        IntToChar.put(0x29, 'p');
        IntToChar.put(0x2A, 'q');
        IntToChar.put(0x2B, 'r');
        IntToChar.put(0x2C, 's');
        IntToChar.put(0x2D, 't');
        IntToChar.put(0x2E, 'u');
        IntToChar.put(0x2F, 'v');
        IntToChar.put(0x30, 'w');
        IntToChar.put(0x31, 'x');
        IntToChar.put(0x32, 'y');
        IntToChar.put(0x33, 'z');
        IntToChar.put(0x34, '0');
        IntToChar.put(0x35, '1');
        IntToChar.put(0x36, '2');
        IntToChar.put(0x37, '3');
        IntToChar.put(0x38, '4');
        IntToChar.put(0x39, '5');
        IntToChar.put(0x3A, '6');
        IntToChar.put(0x3B, '7');
        IntToChar.put(0x3C, '8');
        IntToChar.put(0x3D, '9');
        IntToChar.put(0x3E, '+');
        IntToChar.put(0x3F, '/');
    }

    private HashMap<Character, Integer> CharToInt;
    private HashMap<Integer, Character> IntToChar;

    public int getIntByChar(Character c) {
        return (CharToInt.containsKey(c) ? CharToInt.get(c) : -1);
    }

    public char getCharByInt(Integer i) {
        return (IntToChar.containsKey(i) ? IntToChar.get(i) : '#');
    }

}
