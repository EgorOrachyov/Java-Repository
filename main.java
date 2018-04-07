import SourceMap.Base64;
import SourceMap.Decoder;

import java.nio.file.Paths;

public class main {
    public static void main(String[] args) {
        Base64 B = new Base64();
        System.out.println(B.getCharByInt(0b000010));
        System.out.println(B.getCharByInt(0b101000010));
        System.out.println(B.getIntByChar('/'));
        System.out.println(B.getIntByChar('&'));

        boolean isOk = Decoder.decode("test1.txt");
    }
}
