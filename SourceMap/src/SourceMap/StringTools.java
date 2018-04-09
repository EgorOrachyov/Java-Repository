package SourceMap;

import java.util.Scanner;

public class StringTools {
    public static String getCommonInfo(Scanner file) {
        StringBuilder line = new StringBuilder();
        line.append("");

        while (file.hasNext() && (!line.toString().contains(",") || (line.toString().contains("[") && !line.toString().contains("],")))) {
            line.append(file.next());
        }

        return line.toString();
    }

    public static String getClearStringIgnoreQuotes(String str) {
        str = str.replace(":", "");
        str = str.replace("[", "");
        str = str.replace("]", "");

        return str;
    }

    public static String getClearString(String str) {
        str = getClearStringIgnoreQuotes(str);
        str = str.replace("\"", "");

        return str;
    }

    public static String[] getClearSplittedString(String str) {
        return getClearString(str).split(",");
    }

    public static String multiplySeqByInt(String seq, int scalar) {
        if (scalar > 0) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < scalar; i++) {
                result.append(seq);
            }

            return result.toString();
        } else {
            return "";
        }
    }
}
