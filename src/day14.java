import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day14 {

    private static final String DATA_NAME = "resources/data14.txt";
    private static Map<Pair, Long> polymerTemplatePairs;
    private static Map<Pair, List<Pair>> pairInsertion;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initPolymerTemplateAndPairInsertion();
        // Process insertion
        for (int i = 0; i < 10; i++) {
            Map<Pair, Long> inserted = new HashMap<>();
            for (Map.Entry<Pair, Long> entry : polymerTemplatePairs.entrySet()) {
                List<Pair> pairsToInsert = pairInsertion.get(entry.getKey());
                for (Pair pair : pairsToInsert) {
                    inserted.merge(pair, entry.getValue(), Long::sum);
                }
            }
            polymerTemplatePairs = inserted;
        }
        // Compute number of each character
        int[] quantities = new int[26];
        for (Map.Entry<Pair, Long> entry : polymerTemplatePairs.entrySet()) {
            quantities[entry.getKey().c1() - 'A'] += entry.getValue();
            quantities[entry.getKey().c2() - 'A'] += entry.getValue();
        }
        // Compute min and max
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int quantity : quantities) {
            // It is odd because we count the first and last char 1 time (and the other 2 times as we count pairs)
            if (quantity % 2 == 1) quantity++;
            if (quantity != 0) {
                min = Math.min(min, quantity / 2);
                max = Math.max(max, quantity / 2);
            }
        }
        System.out.println(max - min);
    }

    private static void two() {
        initPolymerTemplateAndPairInsertion();
        // Process insertion
        for (int i = 0; i < 40; i++) {
            Map<Pair, Long> inserted = new HashMap<>();
            for (Map.Entry<Pair, Long> entry : polymerTemplatePairs.entrySet()) {
                List<Pair> pairsToInsert = pairInsertion.get(entry.getKey());
                for (Pair pair : pairsToInsert) {
                    inserted.merge(pair, entry.getValue(), Long::sum);
                }
            }
            polymerTemplatePairs = inserted;
        }
        // Compute number of each character
        int[] quantities = new int[26];
        for (Map.Entry<Pair, Long> entry : polymerTemplatePairs.entrySet()) {
            quantities[entry.getKey().c1() - 'A'] += entry.getValue();
            quantities[entry.getKey().c2() - 'A'] += entry.getValue();
        }
        // Compute min and max
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int quantity : quantities) {
            // It is odd because we count the first and last char 1 time (and the other 2 times as we count pairs)
            if (quantity % 2 == 1) quantity++;
            if (quantity != 0) {
                min = Math.min(min, quantity / 2);
                max = Math.max(max, quantity / 2);
            }
        }
        System.out.println(max - min);
    }

    private static void initPolymerTemplateAndPairInsertion() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day14.DATA_NAME));
            polymerTemplatePairs = new HashMap<>();
            final String line = reader.readLine();
            for (int i = 1; i < line.length(); i++) {
                polymerTemplatePairs.merge(new Pair(line.charAt(i - 1), line.charAt(i)), 1L, Long::sum);
            }
            reader.readLine(); // Skip blank
            pairInsertion = new HashMap<>();
            reader.lines().forEach(s -> {
                String[] split = s.split(" -> ");
                Pair pairLeft = new Pair(split[0].charAt(0), split[0].charAt(1));
                List<Pair> pairRight = List.of(new Pair(split[0].charAt(0), split[1].charAt(0)), new Pair(split[1].charAt(0), split[0].charAt(1)));
                pairInsertion.put(pairLeft, pairRight);
            });
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record Pair(char c1, char c2) {
    }
}