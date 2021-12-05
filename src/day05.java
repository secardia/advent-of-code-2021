import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day05 {

    private static final String DATA_NAME = "resources/data05.txt";
    private static List<Line> lines;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initLines();
        // Filter horizontal/vertical line
        lines = lines.stream().filter(line -> line.x1 == line.x2 || line.y1 == line.y2).collect(Collectors.toList());
        int maxX = getMaxX() + 1;
        int maxY = getMaxY() + 1;
        List<List<Integer>> diagram = Utils.get2dListOf0(maxX, maxY);
        for (Line line : lines) {
            int x1 = Math.min(line.x1, line.x2) - 1;
            int y1 = Math.min(line.y1, line.y2) - 1;
            int x2 = Math.max(line.x1, line.x2);
            int y2 = Math.max(line.y1, line.y2);
            while (x1 != x2 || y1 != y2) {
                if (x1 != x2) {
                    x1++;
                }
                if (y1 != y2) {
                    y1++;
                }
                int val = diagram.get(x1).get(y1);
                diagram.get(x1).set(y1, val + 1);
            }
        }
        // Compute overlap nb
        int overlap = 0;
        for (List<Integer> list : diagram) {
            for (Integer integer : list) {
                if (integer >= 2) {
                    overlap++;
                }
            }
        }
        System.out.println(overlap);
    }

    private static void two() {
        initLines();
        int maxX = getMaxX() + 1;
        int maxY = getMaxY() + 1;
        List<List<Integer>> diagram = Utils.get2dListOf0(maxX, maxY);
        // Compute horizontal/vertical lines
        List<Line> horVert = lines.stream().filter(line -> line.x1 == line.x2 || line.y1 == line.y2).collect(Collectors.toList());
        for (Line line : horVert) {
            int x1 = Math.min(line.x1, line.x2);
            int y1 = Math.min(line.y1, line.y2);
            int x2 = Math.max(line.x1, line.x2);
            int y2 = Math.max(line.y1, line.y2);
            int val = diagram.get(x1).get(y1);
            diagram.get(x1).set(y1, val + 1);
            while (x1 != x2 || y1 != y2) {
                if (x1 != x2) {
                    x1++;
                }
                if (y1 != y2) {
                    y1++;
                }
                val = diagram.get(x1).get(y1);
                diagram.get(x1).set(y1, val + 1);
            }
        }
        // Compute diagonal lines
        List<Line> diag = lines.stream().filter(line -> Math.abs(line.x1 - line.x2) == Math.abs(line.y1 - line.y2)).collect(Collectors.toList());
        for (Line line : diag) {
            int x1 = line.x1;
            int y1 = line.y1;
            int x2 = line.x2;
            int y2 = line.y2;
            int val = diagram.get(x1).get(y1);
            diagram.get(x1).set(y1, val + 1);
            while (x1 != x2) {
                x1 = x1 < x2 ? x1 + 1 : x1 - 1;
                y1 = y1 < y2 ? y1 + 1 : y1 - 1;
                val = diagram.get(x1).get(y1);
                diagram.get(x1).set(y1, val + 1);
            }
        }
        // Compute overlap nb
        int overlap = 0;
        for (List<Integer> list : diagram) {
            for (Integer integer : list) {
                if (integer >= 2) {
                    overlap++;
                }
            }
        }
        System.out.println(overlap);
    }

    private static int getMaxX() {
        int max = Integer.MIN_VALUE;
        for (Line line : lines) {
            if (line.x1 > max) {
                max = line.x1;
            }
            if (line.x2 > max) {
                max = line.x2;
            }
        }
        return max;
    }

    private static int getMaxY() {
        int max = Integer.MIN_VALUE;
        for (Line line : lines) {
            if (line.y1 > max) {
                max = line.y1;
            }
            if (line.y2 > max) {
                max = line.y2;
            }
        }
        return max;
    }

    private static void initLines() {
        try {
            lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(day05.DATA_NAME));
            // Parse lines
            reader.lines().forEach(s -> {
                String pos1 = s.split(" -> ")[0];
                String pos2 = s.split(" -> ")[1];
                int x1 = Integer.parseInt(pos1.split(",")[0]);
                int y1 = Integer.parseInt(pos1.split(",")[1]);
                int x2 = Integer.parseInt(pos2.split(",")[0]);
                int y2 = Integer.parseInt(pos2.split(",")[1]);
                lines.add(new Line(x1, y1, x2, y2));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record Line(int x1, int y1, int x2, int y2) {
    }
}
