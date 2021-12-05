import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    public static List<List<Integer>> get2dListOf0(int row, int col) {
        List<List<Integer>> lists = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            List<Integer> list = new ArrayList<>(col);
            for (int j = 0; j < col; j++) {
                list.add(0);
            }
            lists.add(list);
        }
        return lists;
    }
}
