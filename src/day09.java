import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class day09 {

    private static final String DATA_NAME = "resources/data09.txt";
    private static List<List<Integer>> heights;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initHeights();
        int sumAllLowPoints = 0;
        for (int i = 0; i < heights.size(); i++) {
            for (int j = 0; j < heights.get(0).size(); j++) {
                int finalI = i;
                int finalJ = j;
                if (Coord.getValidAdjacentCoords(i, j).stream().allMatch(c -> heights.get(finalI).get(finalJ) < heights.get(c.x).get(c.y))) {
                    sumAllLowPoints += heights.get(i).get(j) + 1;
                }
            }
        }
        System.out.println(sumAllLowPoints);
    }

    private static void two() {
        initHeights();
        List<Integer> basinsSize = new ArrayList<>();
        for (int i = 0; i < heights.size(); i++) {
            for (int j = 0; j < heights.get(0).size(); j++) {
                int finalI = i;
                int finalJ = j;
                if (Coord.getValidAdjacentCoords(i, j).stream().allMatch(c -> heights.get(finalI).get(finalJ) < heights.get(c.x).get(c.y))) {
                    basinsSize.add(computeBasinSize(i, j));
                }
            }
        }
        System.out.println(basinsSize.stream().sorted(Comparator.reverseOrder()).limit(3).reduce((integer, integer2) -> integer * integer2).orElseThrow());
    }

    private static void initHeights() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day09.DATA_NAME));
            heights = new ArrayList<>();
            reader.lines().forEach(s -> {
                ArrayList<Integer> heightLine = new ArrayList<>();
                for (int i = 0; i < s.length(); i++) {
                    heightLine.add(s.charAt(i) - '0');
                }
                heights.add(heightLine);
            });
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int computeBasinSize(int x, int y) {
        List<Coord> alreadyCheck = new ArrayList<>();
        List<Coord> toCheck = new ArrayList<>();
        int basinSize = 0;
        toCheck.add(new Coord(x, y));
        while (!toCheck.isEmpty()) {
            Coord coord = toCheck.remove(toCheck.size() - 1);
            if (!alreadyCheck.contains(coord)) {
                alreadyCheck.add(coord);
                if (heights.get(coord.x).get(coord.y) != 9) {
                    basinSize++;
                    for (Coord c : Coord.getValidAdjacentCoords(coord.x, coord.y)) {
                        if (!alreadyCheck.contains(c)) {
                            toCheck.add(c);
                        }
                    }
                }
            }
        }
        return basinSize;
    }

    private record Coord(int x, int y) {
        public static List<Coord> getValidAdjacentCoords(int x, int y) {
            List<Coord> validAdjacent = new ArrayList<>();
            if (x - 1 >= 0) validAdjacent.add(new Coord(x - 1, y));
            if (y - 1 >= 0) validAdjacent.add(new Coord(x, y - 1));
            if (x + 1 < heights.size()) validAdjacent.add(new Coord(x + 1, y));
            if (y + 1 < heights.get(0).size()) validAdjacent.add(new Coord(x, y + 1));
            return validAdjacent;
        }
    }
}