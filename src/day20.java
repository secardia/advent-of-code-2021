import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class day20 {

    private static final String DATA_NAME = "resources/data20.txt";
    private static int[] enhancement;
    private static int[][] image;
    private static int other_pixels = 0;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initEnhancementAndImage();
        for (int i = 0; i < 2; i++) {
            enhance();
            if (enhancement[0] == 1)
                other_pixels = 1 - other_pixels;
        }
        System.out.println(sumAllLit());
    }

    private static void two() {
        initEnhancementAndImage();
        for (int i = 0; i < 50; i++) {
            enhance();
            if (enhancement[0] == 1)
                other_pixels = 1 - other_pixels;
        }
        System.out.println(sumAllLit());
    }

    private static void enhance() {
        int[][] enhancedImage = new int[image.length + 2][image.length + 2];
        for (int i = 0; i < enhancedImage.length; i++) {
            for (int j = 0; j < enhancedImage.length; j++) {
                enhancedImage[i][j] = enhancement[getBinaryValue(i - 1, j - 1)];
            }
        }
        image = enhancedImage;
    }

    private static void initEnhancementAndImage() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day20.DATA_NAME));
            String line = reader.readLine();
            enhancement = new int[line.length()];
            for (int i = 0; i < line.length(); i++) {
                enhancement[i] = line.charAt(i) == '#' ? 1 : 0;
            }
            reader.readLine();
            line = reader.readLine();
            image = new int[line.length()][line.length()];
            for (int i = 0; line != null && i < line.length(); i++) {
                for (int j = 0; j < line.length(); j++) {
                    image[i][j] = line.charAt(j) == '#' ? 1 : 0;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getBinaryValue(int x, int y) {
        int bin = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                int imageValue = getImageValue(i, j);
                bin = (bin << 1) | imageValue;
            }
        }
        return bin;
    }

    private static int getImageValue(int x, int y) {
        if (x < 0 || x >= image.length || y < 0 || y >= image.length) {
            return other_pixels;
        }
        return image[x][y];
    }

    private static int sumAllLit() {
        int total = 0;
        for (int[] line : image) {
            for (int i = 0; i < image.length; i++) {
                total += line[i];
            }
        }
        return total;
    }
}