import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day13 {

    private static final String DATA_NAME = "resources/data13.txt";
    private static Set<List<Integer>> dots;
    private static List<List<Integer>> folds;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initDotsAndFolds();
        fold(folds.get(0));
        System.out.println(dots.size());
    }

    private static void two() {
        initDotsAndFolds();
        folds.forEach(day13::fold);
        displayCode();
    }

    private static void initDotsAndFolds() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day13.DATA_NAME));
            dots = new HashSet<>();
            folds = new ArrayList<>();
            String line = reader.readLine();
            do {
                dots.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            } while (!(line = reader.readLine()).isBlank());
            while ((line = reader.readLine()) != null) {
                folds.add(Arrays.stream(line.replace("fold along ", "").split("="))
                        .map(s -> switch (s) {
                                    case "x" -> 0;
                                    case "y" -> 1;
                                    default -> Integer.parseInt(s);
                                }
                        ).toList());
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fold(List<Integer> fold) {
        Set<List<Integer>> newDots = new HashSet<>();
        for (List<Integer> dot : dots) {
            final Integer foldCoord = fold.get(1);
            switch (fold.get(0)) {
                // fold along x
                case 0 -> {
                    if (dot.get(0) > foldCoord) {
                        newDots.add(List.of(foldCoord - (dot.get(0) - foldCoord), dot.get(1)));
                    } else {
                        newDots.add(dot);
                    }
                }
                // fold along y
                case 1 -> {
                    if (dot.get(1) > foldCoord) {
                        newDots.add(List.of(dot.get(0), foldCoord - (dot.get(1) - foldCoord)));
                    } else {
                        newDots.add(dot);
                    }
                }
            }
        }
        dots = newDots;
    }

    private static void displayCode() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50; j++) {
                if (dots.contains(List.of(j, i))) {
                    System.out.print("##");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}