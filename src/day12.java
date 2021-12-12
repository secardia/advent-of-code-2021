import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class day12 {

    private static final String DATA_NAME = "resources/data12.txt";
    private static Map<String, List<String>> caveToAdjacentCave;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initCaves();
        System.out.println(findPath(List.of("start")).size());
    }

    private static void two() {
        initCaves();
        System.out.println(findPath2(List.of("start")).size());
    }

    private static List<List<String>> findPath(List<String> crossedCaves) {
        String lastCrossed = crossedCaves.get(crossedCaves.size() - 1);
        if (lastCrossed.equals("end")) {
            return List.of(crossedCaves);
        }
        List<String> caveToCross = caveToAdjacentCave.get(lastCrossed).stream()
                .filter(cave -> !(cave.equals(cave.toLowerCase()) && crossedCaves.contains(cave))).toList();
        return caveToCross.stream().flatMap(cave -> findPath(mergeLists(crossedCaves, List.of(cave))).stream()).toList();
    }

    private static List<List<String>> findPath2(List<String> crossedCaves) {
        String lastCrossed = crossedCaves.get(crossedCaves.size() - 1);
        if (lastCrossed.equals("end")) {
            return List.of(crossedCaves);
        }
        boolean containsSmallCaveTwice = crossedCaves.stream()
                .collect(Collectors.groupingBy(s -> s)).values().stream()
                .anyMatch(caves -> caves.get(0).equals(caves.get(0).toLowerCase()) && caves.size() >= 2);
        List<String> caveToCross = caveToAdjacentCave.get(lastCrossed).stream()
                .filter(cave -> !(cave.equals("start") ||
                        containsSmallCaveTwice && cave.equals(cave.toLowerCase()) && crossedCaves.contains(cave))).toList();
        return caveToCross.stream().flatMap(cave -> findPath2(mergeLists(crossedCaves, List.of(cave))).stream()).toList();
    }

    private static void initCaves() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day12.DATA_NAME));
            caveToAdjacentCave = new HashMap<>();
            reader.lines().forEach(s -> {
                        String[] caves = s.split("-");
                        caveToAdjacentCave.merge(caves[0], List.of(caves[1]), day12::mergeLists);
                        caveToAdjacentCave.merge(caves[1], List.of(caves[0]), day12::mergeLists);
                    }
            );
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> mergeLists(List<String> list1, List<String> list2) {
        return Stream.concat(list1.stream(), list2.stream()).toList();
    }
}