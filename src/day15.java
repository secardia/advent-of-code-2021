import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class day15 {

    private static final String DATA_NAME = "resources/data15.txt";
    private static int[][] mapRisk;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initMapRisk();
        findMinimalPath();
    }

    private static void two() {
        initMapRisk25Bigger();
        findMinimalPath();
    }

    private static void findMinimalPath() {
        boolean[][] crossed = new boolean[mapRisk.length][mapRisk.length];
        PriorityQueue<Coord> toCross = new PriorityQueue<>();
        toCross.add(new Coord(0, 0, 0));
        while (!toCross.isEmpty()) {
            Coord coord = toCross.poll();
            if (!crossed[coord.x][coord.y]) {
                if (coord.x == mapRisk.length - 1 && coord.y == mapRisk.length - 1) {
                    System.out.println(coord.score);
                    break;
                }
                crossed[coord.x][coord.y] = true;
                for (Coord c : coord.getValidAdjacent()) {
                    if (!crossed[c.x][c.y]) toCross.add(c);
                }
            }
        }
    }

    private static void initMapRisk() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day15.DATA_NAME));
            String line = reader.readLine();
            mapRisk = new int[line.length()][line.length()];
            for (int i = 0; line != null && i < line.length(); i++) {
                for (int j = 0; j < line.length(); j++) {
                    mapRisk[i][j] = line.charAt(j) - '0';
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initMapRisk25Bigger() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day15.DATA_NAME));
            String line = reader.readLine();
            int inputSize = line.length();
            mapRisk = new int[inputSize * 5][inputSize * 5];
            for (int i = 0; line != null && i < inputSize; i++) {
                for (int j = 0; j < inputSize; j++) {
                    mapRisk[i][j] = line.charAt(j) - '0';
                }
                line = reader.readLine();
            }
            reader.close();
            for (int i = inputSize; i < inputSize * 5; i++) {
                for (int j = 0; j < inputSize; j++) {
                    int val = mapRisk[i - inputSize][j] + 1;
                    if (val == 10) val = 1;
                    mapRisk[i][j] = val;
                }
            }
            for (int i = 0; i < inputSize * 5; i++) {
                for (int j = inputSize; j < inputSize * 5; j++) {
                    int val = mapRisk[i][j - inputSize] + 1;
                    if (val == 10) val = 1;
                    mapRisk[i][j] = val;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record Coord(int x, int y, Integer score) implements Comparable<Coord> {
        public List<Coord> getValidAdjacent() {
            List<Coord> ret = new ArrayList<>();
            if (x > 0) ret.add(new Coord(x - 1, y, score + mapRisk[x - 1][y]));
            if (y > 0) ret.add(new Coord(x, y - 1, score + mapRisk[x][y - 1]));
            if (x < mapRisk.length - 1) ret.add(new Coord(x + 1, y, score + mapRisk[x + 1][y]));
            if (y < mapRisk.length - 1) ret.add(new Coord(x, y + 1, score + mapRisk[x][y + 1]));
            return ret;
        }

        @Override
        public int compareTo(Coord o) {
            return score.compareTo(o.score);
        }
    }
}