import java.util.List;

public class day02 {

    private static final String DATA_NAME = "resources/data02.txt";
    private static final List<String> datas = Utils.parseStrings(DATA_NAME);

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        int hor = 0;
        int depth = 0;
        for (String data : datas) {
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
        int hor = 0;
        int depth = 0;
        int aim = 0;
        for (String data : datas) {
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
}
