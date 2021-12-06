import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class day06 {

    private static final String DATA_NAME = "resources/data06.txt";
    private static Map<Integer, Long> fish;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initFish();
        for (int i = 0; i < 80; i++) {
            computeFishNextDay();
        }
        System.out.println(fish.values().stream().reduce(Long::sum).orElseThrow());
    }

    private static void two() {
        initFish();
        for (int i = 0; i < 256; i++) {
            computeFishNextDay();
        }
        System.out.println(fish.values().stream().reduce(Long::sum).orElseThrow());
    }

    private static void initFish() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day06.DATA_NAME));
            Map<Integer, List<Integer>> collect = Arrays.stream(reader.readLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.groupingBy(integer -> integer));
            fish = new HashMap<>();
            collect.forEach((integer, integers) -> fish.put(integer, (long) integers.size()));
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void computeFishNextDay() {
        Map<Integer, Long> nextDay = new HashMap<>();
        fish.forEach((timer, fishNb) -> {
            if (timer == 0) {
                nextDay.merge(6, fishNb, Long::sum);
                nextDay.merge(8, fishNb, Long::sum);
            } else {
                nextDay.merge(timer - 1, fishNb, Long::sum);
            }
        });
        fish = nextDay;
    }
}
