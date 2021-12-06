import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day01 {

    private static final String DATA_NAME = "resources/data01.txt";
    private static List<Integer> depths;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initDepths();
        int lastDepth = Integer.MAX_VALUE;
        int increasingDepth = 0;
        for (Integer depth : depths) {
            if (depth > lastDepth) {
                increasingDepth += 1;
            }
            lastDepth = depth;
        }
        System.out.println(increasingDepth);
    }

    private static void two() {
        initDepths();
        List<Integer> threeSum = new ArrayList<>();
        for (int i = 0; i < depths.size() - 2; i++) {
            threeSum.add(depths.get(i) + depths.get(i + 1) + depths.get(i + 2));
        }
        int lastDepth = Integer.MAX_VALUE;
        int increasingDepth = 0;
        for (Integer depth : threeSum) {
            if (depth > lastDepth) {
                increasingDepth += 1;
            }
            lastDepth = depth;
        }
        System.out.println(increasingDepth);
    }

    private static void initDepths() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day01.DATA_NAME));
            depths = reader.lines().map(Integer::parseInt).collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
