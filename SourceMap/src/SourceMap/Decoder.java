package SourceMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Decoder represents functions for parsing
 * source map files and creating base code
 * file
 *
 * (uses Base64 class to work with encoded strings)
 */
public class Decoder {

    public static boolean decode(String sourceMapFile) {

        ArrayList<FileBuilder> sources = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        String tmp;
        Base64 Base64Table = new Base64();

        try {

            Scanner file = new Scanner(Paths.get(sourceMapFile));

            while (file.hasNext()) {
                tmp = file.next();

                /*
                 * Blocks of parsing, which allows to get info
                 * about important positions of map file
                 * - names of source files
                 * - sequences of code
                 * - base64 encoded: out column | input file index | input file line | input file column | names index
                 */

                if (tmp.contains("sources")) {
                    String line = StringTools.getCommonInfo(file);
                    String[] sourceNames = StringTools.getClearSplittedString(line);
                    for (String s : sourceNames) {
                        sources.add(new FileBuilder(s));
                    }

                    continue;
                }

                if (tmp.contains("names")) {

                    String line = StringTools.getCommonInfo(file);

                    line = StringTools.getClearStringIgnoreQuotes(line);
                    int i = 0;
                    boolean isItWord = false;
                    StringBuilder current = new StringBuilder("");

                    while (i < line.length()) {
                        if (line.charAt(i) == '\"') {
                            if (!isItWord) {
                                isItWord = true;
                            } else {
                                names.add(current.toString());
                                current = new StringBuilder("");
                                isItWord = false;
                            }
                        } else {
                            if (isItWord) {
                                if (line.substring(i, i + 2).equals("\\\"")) {
                                    current.append('\"');
                                    i += 1;
                                } else {
                                    current.append(line.charAt(i));
                                }
                            }
                        }

                        i += 1;
                    }

                    continue;
                }

                if (tmp.contains("mappings")) {
                    String[] lines = StringTools.getClearString(StringTools.getCommonInfo(file)).split(";");

                    int output_line = 0;
                    int output_column = 0;
                    int input_file = 0;
                    int input_line = 0;
                    int input_column = 0;
                    int input_name = 0;

                    for (String current : lines) {

                        String[] maps = StringTools.getClearSplittedString(current);

                        for (String map : maps) {

                            ArrayList<Integer> indexes = getDecodedIndexesFromBits(Base64Table.getDecodedBits(map));

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

            // Out names of NEW created files
            System.out.print("Created files: ");
            for(FileBuilder source: sources) {
                source.closeFile();
                System.out.print(source.getFilename() + " ");
            }
            System.out.println();

            return true;

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    private static ArrayList<Integer> getDecodedIndexesFromBits(String seq) {
        ArrayList<Integer> result = new ArrayList<>(capasityForArrays);

        int i = 0;
        while (i < seq.length()) {

            if (seq.charAt(i) == '1') {
                int sign = seq.charAt(i + 5) == '1' ? -1 : 1;
                int base = 0;
                int value = Integer.valueOf(seq.substring(i + 1, i + 5), 2);


                i += 6;
                base = 4;
                while (seq.charAt(i) == '1') {
                    value += Integer.valueOf(seq.substring(i + 1, i + 6) + StringTools.multiplySeqByInt("0", base), 2);
                    base += 5;
                    i += 6;
                }

                value += Integer.valueOf(seq.substring(i + 1, i + 6) + StringTools.multiplySeqByInt("0", base), 2);
                result.add(sign * value);
            } else {
                int sign = seq.charAt(i + 5) == '1' ? -1 : 1;
                result.add(sign * Integer.valueOf(seq.substring(i + 1, i + 5), 2));
            }

            i += 6;
        }

        return result;
    }

    private static int fiveIndexes = 5;
    private static int capasityForArrays = 6;
}

/**
 * FileBuilder helps to create source file
 * by simple operations
 */
class FileBuilder {

    public FileBuilder(String filename) {
        try {
            this.filename = filename;
            file = new PrintWriter(filename);
            currentLine = 0;
            buffer = StringTools.multiplySeqByInt(" ", BUFFER_SIZE);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void flushFile() {
        file.flush();
    }

    public void closeFile() {
        file.append(buffer);
        file.close();
        ;
    }

    public void writeSequences(String s, int line, int column) {

        if (currentLine == line) {
            StringBuilder tmp = new StringBuilder(buffer);
            tmp.replace(column, column + s.length(), s);
            buffer = tmp.toString();
        } else {
            file.append(buffer);
            while (currentLine < line) {
                file.append('\n');
                currentLine += 1;
            }
            buffer = StringTools.multiplySeqByInt(" ", BUFFER_SIZE);
            StringBuilder tmp = new StringBuilder(buffer);
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
    private final static int BUFFER_SIZE = 300;
}

