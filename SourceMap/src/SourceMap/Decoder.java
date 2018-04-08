package SourceMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  Decoder represents functions for parsing
 *  source map files and creating base code
 *  file
 *
 *  (uses Base64 class to work with encoded strings)
 */
public class Decoder {

    public static boolean decode(String sourceMapFile) {

        ArrayList<String> sources = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        String mappings;
        String tmp;

        try {

            Scanner file = new Scanner(Paths.get(sourceMapFile));

            while(file.hasNext()) {
                tmp = file.next();

                /*
                 * Blocks of parsing, which allows to get info
                 * about important positions of map file
                 * - names of source files
                 * - sequences of code
                 * - base64 encoded: out column | input file index | input file line | input file column | names index
                 */

                if (tmp.contains("sources")) {
                    String line = getCommonInfo(file);
                    String[] sourceNames = getClearSplittedString(line);
                    for(String s: sourceNames) {
                        sources.add(s);
                    }

                    continue;
                }

                if (tmp.contains("names")) {
                    String line = getCommonInfo(file);
                    String[] sequences = getClearSplittedString(line);
                    for(String s: sequences) {
                        names.add(s);
                    }

                    continue;
                }

                if (tmp.contains("mappings")) {
                    String line = getCommonInfo(file);
                    String[] encoded = getClearSplittedString(line);

                    // Debug info
                    for (String s: encoded) {
                        System.out.println(s);
                    }
                }
            }

            // Debug output

            System.out.println("List of input files' names");
            for (int i = 0; i < sources.size(); i++) {
                System.out.println(sources.get(i));
            }

            System.out.println("List of input files' sequences");
            for (int i = 0; i < names.size(); i++) {
                System.out.println(names.get(i));
            }

            return true;

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    private static String getCommonInfo(Scanner file) {
        StringBuilder line = new StringBuilder();
        line.append("");

        while(!line.toString().contains(",") || (line.toString().contains("[") && !line.toString().contains("],"))) {
            line.append(file.next());
        }

        return line.toString();
    }

    private static String[] getClearSplittedString(String str) {
        str = str.replace(":", "");
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace("\"", "");

        return str.split(",");
    }
}

/**
 *  FileBuilder helps to create source file
 *  by simple operations
 */
class FileBuilder {

    public  FileBuilder(String filename) {
        try {
            file = new PrintWriter(filename);
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void writeSequences(String s) {

    }

    private PrintWriter file;

}
