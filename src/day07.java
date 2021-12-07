import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class day07 {

    private static final String DATA_NAME = "resources/data07.txt";
    private static List<Integer> horizontalPositions;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initHorizontalPositions();
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < Collections.max(horizontalPositions); i++) {
            int cost = computeFuelNeeded(i);
            minCost = Math.min(minCost, cost);
        }
        System.out.println(minCost);
    }

    private static void two() {
        initHorizontalPositions();
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < Collections.max(horizontalPositions); i++) {
            int cost = computeFuelNeeded2(i);
            minCost = Math.min(minCost, cost);
        }
        System.out.println(minCost);
    }

    private static void initHorizontalPositions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day07.DATA_NAME));
            horizontalPositions = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int computeFuelNeeded(int aligningAt) {
        int cost = 0;
        for (Integer horizontalPosition : horizontalPositions) {
            cost += Math.abs(horizontalPosition - aligningAt);
        }
        return cost;
    }

    private static int computeFuelNeeded2(int aligningAt) {
        int cost = 0;
        for (Integer horizontalPosition : horizontalPositions) {
            int horizDiff = Math.abs(horizontalPosition - aligningAt);
            cost += horizDiff * (horizDiff + 1) / 2;
        }
        return cost;
    }
}
