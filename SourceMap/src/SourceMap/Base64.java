package SourceMap;

import java.util.HashMap;

/**
 * Base64 class introduces mentioned code table
 * with methods to encode and decode
 * sequences of chars
 */
public class Base64 {

    public Base64() {
        CharToInt = new HashMap<>();
        IntToChar = new HashMap<>();

        int alphabetSize = 26;

        // Build first 26 chars and values (Upper case)
        for (int i = 0; i < alphabetSize; i++) {
            CharToInt.put((char) ((short) ('A') + i), i);
            IntToChar.put(i, (char) ((short) ('A') + i));
        }

        // Build second 26 chars and values (Lower case)
        for (int i = 0; i < alphabetSize; i++) {
            CharToInt.put((char) ((short) ('a') + i), i + alphabetSize);
            IntToChar.put(i + alphabetSize, (char) ((short) ('a') + i));
        }

        // Build numbers from 0 to 9
        for (int i = 0; i < 10; i++) {
            CharToInt.put((char) (i), i + 2 * alphabetSize);
            IntToChar.put(i + 2 * alphabetSize, (char) (i));
        }

        // Build arithmetic symbols
        CharToInt.put('+', 0x3E);
        CharToInt.put('/', 0x3F);

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

    public String getDecodedBits(String s) {
        StringBuilder result = new StringBuilder();

        for (int index = 0; index < s.length(); index++) {
            int code = getIntByChar(s.charAt(index));

            // 5 + 1 is num of bits in one base64 point
            for (int i = 5; i >= 0; i--) {
                result.append(String.valueOf(1 & code >>> i));
            }
        }

        return result.toString();
    }
}
