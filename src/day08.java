import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day08 {

    private static final String DATA_NAME = "resources/data08.txt";
    private static List<List<String>> lines;

    public static void main(String[] args) {
        one();
        two();
    }

    // 1 -> 2 seg
    // 4 -> 4 seg
    // 7 -> 3 seg
    // 8 -> 7 seg
    private static void one() {
        initLines();
        int uniqueNumberSegmentDigit = 0;
        for (List<String> line : lines) {
            for (String s : getOutput(line)) {
                if (List.of(2, 3, 4, 7).contains(s.length())) {
                    uniqueNumberSegmentDigit++;
                }
            }
        }
        System.out.println(uniqueNumberSegmentDigit);
    }

    // 1 -> 2 seg
    // 4 -> 4 seg
    // 7 -> 3 seg
    // 8 -> 7 seg
    // 9 -> 6 seg + contains 4
    // 0 -> 9 found + 6 seg + contains 1
    // 6 -> 9+0 found + 6 seg
    // 3 -> 5 seg + contains 1
    // 5 -> 5 seg + 6 contains
    // 2 -> 3+5 found + 5 seg
    private static void two() {
        initLines();
        int total = 0;
        for (List<String> line : lines) {
            Map<Integer, String> digitToSegments = new HashMap<>();
            while (digitToSegments.entrySet().size() < 10) {
                for (String s : line) {
                    switch (s.length()) {
                        case 2 -> digitToSegments.putIfAbsent(1, s);
                        case 3 -> digitToSegments.putIfAbsent(7, s);
                        case 4 -> digitToSegments.putIfAbsent(4, s);
                        case 5 -> {
                            if (containsInAnyOrder(s, digitToSegments.get(1))) {
                                digitToSegments.putIfAbsent(3, s);
                            } else if (containsInAnyOrder(digitToSegments.get(6), s)) {
                                digitToSegments.putIfAbsent(5, s);
                            } else if (digitToSegments.get(3) != null && digitToSegments.get(5) != null) {
                                digitToSegments.putIfAbsent(2, s);
                            }
                        }
                        case 6 -> {
                            if (containsInAnyOrder(s, digitToSegments.get(4))) {
                                digitToSegments.putIfAbsent(9, s);
                            } else if (digitToSegments.get(9) != null && containsInAnyOrder(s, digitToSegments.get(1))) {
                                digitToSegments.putIfAbsent(0, s);
                            } else if (digitToSegments.get(9) != null && digitToSegments.get(0) != null) {
                                digitToSegments.putIfAbsent(6, s);
                            }
                        }
                        case 7 -> digitToSegments.putIfAbsent(8, s);
                    }
                }
            }
            Map<String, Integer> reversedMap = digitToSegments.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            total += computeValue(line, reversedMap);
        }
        System.out.println(total);
    }

    private static int computeValue(List<String> line, Map<String, Integer> segmentsToDigit) {
        StringBuilder str = new StringBuilder();
        for (String s : getOutput(line)) {
            str.append(segmentsToDigit.get(s));
        }
        return Integer.parseInt(str.toString());
    }

    private static void initLines() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day08.DATA_NAME));
            lines = new ArrayList<>();
            reader.lines().forEach(inputLine -> {
                List<String> line = new ArrayList<>();
                String[] split = inputLine.split(" \\| ");
                for (String str : split) {
                    // Sort strings
                    for (String s : str.split(" ")) {
                        char[] chars = s.toCharArray();
                        Arrays.sort(chars);
                        line.add(String.valueOf(chars));
                    }
                }
                lines.add(line);
            });
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getOutput(List<String> strings) {
        return strings.subList(10, 14);
    }

    // Return true if s1 contains all char of s2 in any order
    private static boolean containsInAnyOrder(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(String.valueOf(s2.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}