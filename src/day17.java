import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class day17 {

    private static final String DATA_NAME = "resources/data17.txt";
    private static int startX;
    private static int endX;
    private static int startY;
    private static int endY;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initPacket();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                max = Math.max(max, fire(i, j).orElse(Integer.MIN_VALUE));
            }
        }
        System.out.println(max);
    }

    private static void two() {
        initPacket();
        int total = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = -1000; j < 1000; j++) {
                if (fire(i, j).isPresent()) {
                    total++;
                }
            }
        }
        System.out.println(total);
    }

    private static Optional<Integer> fire(int veloX, int veloY) {
        int max = Integer.MIN_VALUE;
        int x = 0;
        int y = 0;
        while (!isReached(x, y) && isReachable(x, y)) {
            x += veloX;
            y += veloY;
            if (veloX < 0) {
                veloX++;
            } else if (veloX > 0) {
                veloX--;
            }
            veloY--;
            max = Math.max(max, y);
        }
        if (isReached(x, y)) {
            return Optional.of(max);
        }
        return Optional.empty();
    }

    private static boolean isReached(int x, int y) {
        return x >= startX && x <= endX && y <= startY && y >= endY;
    }

    private static boolean isReachable(int x, int y) {
        return x <= endX && y >= endY;
    }

    private static void initPacket() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day17.DATA_NAME));
            String line = reader.readLine();
            line = line.replace("target area: x=", "");
            final String[] split = line.split(", y=");
            startX = Integer.parseInt(split[0].split("\\.\\.")[0]);
            endX = Integer.parseInt(split[0].split("\\.\\.")[1]);
            startY = Integer.parseInt(split[1].split("\\.\\.")[1]);
            endY = Integer.parseInt(split[1].split("\\.\\.")[0]);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}