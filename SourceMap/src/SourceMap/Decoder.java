package SourceMap;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Decoder {
    public static boolean decode(String sourceMapFile, String outputFile) {
        return true;
    }

    public static boolean decode(String sourceMapFile, String[] outputFile) {
        return true;
    }

    public static boolean decode(String sourceMapFile) {
        try {

            Scanner file = new Scanner(Paths.get(sourceMapFile));
            while (file.hasNext()) {
                System.out.println(file.next());
            }

            return true;
        } catch (IOException exception) {
            System.out.println("File parsing interrupted:" + '\n' + exception.getMessage());
            return false;
        }
    }
}
