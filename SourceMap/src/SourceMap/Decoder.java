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

        ArrayList<FileBuilder> sources = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        String mappings;
        String tmp;
        Base64 Base64Table = new Base64();

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
                        sources.add(new FileBuilder(s));
                    }

                    continue;
                }

                if (tmp.contains("names")) {
                    String line = getCommonInfo(file);
                    String[] sequences = getClearStringIgnoreQuotes(line).split(",");
                    for(String s: sequences) {
                        s = s.substring(1, s.length() - 1);
                        names.add(s);
                    }

                    continue;
                }

                if (tmp.contains("mappings")) {
                    String[] lines = getClearString(getCommonInfo(file)).split(";");
                    //String[] encoded = getClearSplittedString(line);

                    int output_line = 0;
                    int output_column = 0;
                    int input_file = 0;
                    int input_line = 0;
                    int input_column = 0;
                    int input_name = 0;

                    for (String current: lines) {

                        System.out.println(current);

                        String[] maps = getClearSplittedString(current);

                        for (String map: maps) {

                            System.out.println(map);
                            System.out.println(Base64Table.getDecodedBits(map));

                            ArrayList<Integer> indexes = getDecodedIndexesFromBits(Base64Table.getDecodedBits(map));

                            for (Integer num: indexes) {
                                System.out.println(num);
                            }

                            // 0: output_column 1: input_file 2: input_line 3: input_column 4: input_name

                            if (indexes.size() == fiveIndexes) {

                                output_column += indexes.get(0);
                                input_file += indexes.get(1);
                                input_line += indexes.get(2);
                                input_column = (indexes.get(2) == 0 ? input_column + indexes.get(3) : indexes.get(3));
                                input_name += indexes.get(4);

                                sources.get(input_file).writeSequences(names.get(input_name), input_line, input_column);
                            }
                        }

                        output_line += 1;
                        output_column = 0;
                    }
                }
            }

            // Debug output

            System.out.println("List of input files' names");
            for (int i = 0; i < sources.size(); i++) {
                System.out.println(sources.get(i).getFilename());
                sources.get(i).closeFile();
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

    private static String getClearStringIgnoreQuotes(String str) {
        str = str.replace(":", "");
        str = str.replace("[", "");
        str = str.replace("]", "");

        return str;
    }

    private static String getClearString(String str) {
        str = str.replace(":", "");
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace("\"", "");

        return str;
    }

    private static String[] getClearSplittedString(String str) {
        return getClearString(str).split(",");
    }

    private static ArrayList<Integer> getDecodedIndexesFromBits(String seq) {
        ArrayList<Integer> result = new ArrayList<>();

        int i = 0;
        while (i < seq.length()) {

            if (seq.charAt(i) == '1') {
                int sign = seq.charAt(i + 5) == '1' ? -1 : 1;
                int base = 0;
                int value = Integer.valueOf(seq.substring(i + 1, i + 5), 2);


                i += 6;
                base = 4;
                while (seq.charAt(i) == '1') {
                    value += Integer.valueOf(seq.substring(i + 1, i + 6) + multiplySeqByInt("0", base), 2);
                    base += 5;
                    i += 6;
                }

                value += Integer.valueOf(seq.substring(i + 1, i + 6) + multiplySeqByInt("0", base), 2);
                result.add(sign * value);
            }
            else {
                int sign = seq.charAt(i + 5) == '1' ? -1 : 1;
                result.add(sign * Integer.valueOf(seq.substring(i + 1, i + 5), 2));
            }

            i += 6;
        }

        return result;
    }

    private static String multiplySeqByInt(String seq, int scalar) {
        if (scalar > 0) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < scalar; i++) {
                result.append(seq);
            }

            return result.toString();
        }
        else {
            return "";
        }
    }

    private static int fiveIndexes = 5;
}

/**
 *  FileBuilder helps to create source file
 *  by simple operations
 */
class FileBuilder {

    public  FileBuilder(String filename) {
        try {
            this.filename = filename;
            file = new PrintWriter(filename);
            currentLine = 0;
            buffer = "                                                                 ";
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void flushFile() {
        file.flush();
    }

    public void closeFile() {
        file.append(buffer);
        file.close();;
    }

    public void writeSequences(String s, int line, int column) {
        System.out.println(s + " " + line + " " + column);
        if (currentLine == line) {
            StringBuilder tmp = new StringBuilder(buffer);
            //tmp.insert(column, s);
            tmp.replace(column, column + s.length(), s);
            buffer = tmp.toString();
        }
        else {
            file.append(buffer);
            while (currentLine < line) {
                file.append('\n');
                currentLine += 1;
            }
            buffer = "                                                                 ";
            StringBuilder tmp = new StringBuilder(buffer);
            //tmp.insert(column, s);
            tmp.replace(column, column + s.length(), s);
            buffer = tmp.toString();
        }
    }

    public String getFilename() {
        return filename;
    }

    private String filename;
    private PrintWriter file;
    private int currentLine;
    private String buffer;

}
