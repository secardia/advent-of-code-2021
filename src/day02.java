import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class day02 {

    private static final String DATA_NAME = "resources/data02.txt";
    private static List<String> instructions;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initInstructions();
        int hor = 0;
        int depth = 0;
        for (String data : instructions) {
            String move = data.split(" ")[0];
            int x = Integer.parseInt(data.split(" ")[1]);
            switch (move) {
                case "forward" -> hor += x;
                case "down" -> depth += x;
                case "up" -> depth -= x;
            }
        }
        System.out.println(hor * depth);
    }

    private static void two() {
        initInstructions();
        int hor = 0;
        int depth = 0;
        int aim = 0;
        for (String data : instructions) {
            String move = data.split(" ")[0];
            int x = Integer.parseInt(data.split(" ")[1]);
            switch (move) {
                case "forward" -> {
                    hor += x;
                    depth += aim * x;
                }
                case "down" -> aim += x;
                case "up" -> aim -= x;
            }
        }
        System.out.println(hor * depth);
    }

    private static void initInstructions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day02.DATA_NAME));
            instructions = reader.lines().collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
