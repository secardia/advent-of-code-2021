import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day10 {

    private static final String DATA_NAME = "resources/data10.txt";
    private static final Map<Character, Character> OPEN_TO_CLOSE = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static List<String> chunks;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initChunks();
        Map<Character, Integer> charScores = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
        int syntaxErrorScore = 0;
        for (String chunk : chunks) {
            Stack<Character> stack = new Stack<>();
            // Cross the chunk
            for (int i = 0; i < chunk.length(); i++) {
                char c = chunk.charAt(i);
                if (OPEN_TO_CLOSE.containsKey(c)) {
                    stack.add(OPEN_TO_CLOSE.get(c));
                } else if (OPEN_TO_CLOSE.containsValue(c)) {
                    if (stack.pop() != c) {
                        syntaxErrorScore += charScores.get(c);
                        break;
                    }
                }
            }
        }
        System.out.println(syntaxErrorScore);
    }

    private static void two() {
        initChunks();
        Map<Character, Integer> charScores = Map.of(')', 1, ']', 2, '}', 3, '>', 4);
        List<Long> scores = new ArrayList<>();
        for (String chunk : chunks) {
            Stack<Character> stack = new Stack<>();
            boolean syntaxError = false;
            // Fill stack and filter chunk with syntax error
            for (int i = 0; i < chunk.length(); i++) {
                char c = chunk.charAt(i);
                if (OPEN_TO_CLOSE.containsKey(c)) {
                    stack.add(OPEN_TO_CLOSE.get(c));
                } else if (OPEN_TO_CLOSE.containsValue(c) && stack.pop() != c) {
                    syntaxError = true;
                    break;
                }
            }
            // Process incomplete lines
            if (!syntaxError) {
                long incompleteScore = 0;
                while (!stack.empty()) {
                    incompleteScore *= 5;
                    incompleteScore += charScores.get(stack.pop());
                }
                scores.add(incompleteScore);
            }
        }
        scores.sort(Comparator.naturalOrder());
        System.out.println(scores.get(scores.size() / 2));
    }

    private static void initChunks() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day10.DATA_NAME));
            chunks = reader.lines().collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}