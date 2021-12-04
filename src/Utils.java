import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static List<String> parseStrings(String filename) {
        return parseFile(filename)
                .collect(Collectors.toList());
    }

    public static List<Integer> parseInts(String filename) {
        return parseFile(filename)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private static Stream<String> parseFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            return reader.lines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
