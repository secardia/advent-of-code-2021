import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class day03 {

    private static final String DATA_NAME = "resources/data03.txt";
    private static List<String> report;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initReport();
        int gamma = 0b0;
        int epsilon = 0b0;
        for (int i = 0; i < report.get(0).length(); i++) {
            gamma <<= 1;
            epsilon <<= 1;
            char c = mostCommonBitAt(i, report);
            if (c == '1') {
                gamma = gamma | 0b1;
            } else {
                epsilon = epsilon | 0b1;
            }
        }
        System.out.println(gamma * epsilon);
    }

    private static void two() {
        initReport();
        List<String> oxy = new ArrayList<>(report);
        List<String> coo = new ArrayList<>(report);
        int i = 0;
        while (oxy.size() > 1) {
            char c = mostCommonBitAt(i, oxy);
            Iterator<String> iterator = oxy.iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (s.charAt(i) != c) {
                    iterator.remove();
                }
            }
            i++;
        }
        i = 0;
        while (coo.size() > 1) {
            char c = mostCommonBitAt(i, coo);
            Iterator<String> iterator = coo.iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (s.charAt(i) == c) {
                    iterator.remove();
                }
            }
            i++;
        }
        System.out.println(Integer.parseInt(oxy.get(0), 2) * Integer.parseInt(coo.get(0), 2));
    }

    private static char mostCommonBitAt(int i, List<String> strs) {
        int nbOne = 0;
        for (String str : strs) {
            if (str.charAt(i) == '1') {
                nbOne++;
            }
        }
        return nbOne >= strs.size() / 2. ? '1' : '0';
    }

    private static void initReport() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day03.DATA_NAME));
            report = reader.lines().collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
