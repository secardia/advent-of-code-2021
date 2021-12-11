import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class day11 {

    private static final String DATA_NAME = "resources/data11.txt";
    private static int[][] fishLights;
    private static boolean[][] flashes;
    private static int nbFlash = 0;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initFishLights();
        for (int i = 0; i < 100; i++) {
            step();
        }
        System.out.println(nbFlash);
    }

    private static void two() {
        initFishLights();
        int i = 0;
        while (!areFishSynchronized()) {
            step();
            i++;
        }
        System.out.println(i);
    }


    private static void initFishLights() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day11.DATA_NAME));
            String line = reader.readLine();
            fishLights = new int[line.length()][line.length()];
            int i = 0;
            do {
                int[] lightLine = new int[line.length()];
                for (int j = 0; j < line.length(); j++) {
                    lightLine[j] = line.charAt(j) - '0';
                }
                fishLights[i] = lightLine;
                i++;
            } while ((line = reader.readLine()) != null);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initFlashes() {
        flashes = new boolean[fishLights.length][fishLights.length];
        for (int i = 0; i < flashes.length; i++) {
            boolean[] line = new boolean[flashes.length];
            Arrays.fill(line, false);
            flashes[i] = line;
        }
    }

    private static void step() {
        initFlashes();
        for (int j = 0; j < fishLights.length; j++) {
            for (int k = 0; k < fishLights[0].length; k++) {
                fishLights[j][k]++;
            }
        }
        for (int j = 0; j < fishLights.length; j++) {
            for (int k = 0; k < fishLights[0].length; k++) {
                flashIfNeeded(j, k);
            }
        }
    }

    private static void increaseNeighbors(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i != x || j != y) && i >= 0 && i < fishLights.length && j >= 0 && j < fishLights[0].length) {
                    if (!flashes[i][j]) {
                        fishLights[i][j]++;
                        flashIfNeeded(i, j);
                    }
                }
            }
        }
    }

    private static void flashIfNeeded(int x, int y) {
        if (fishLights[x][y] > 9) {
            nbFlash++;
            fishLights[x][y] = 0;
            flashes[x][y] = true;
            increaseNeighbors(x, y);
        }
    }

    private static boolean areFishSynchronized() {
        for (boolean[] line : flashes) {
            for (boolean flash : line) {
                if (!flash) {
                    return false;
                }
            }
        }
        return true;
    }
}