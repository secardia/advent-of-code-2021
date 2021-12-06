import java.util.ArrayList;
import java.util.List;

public class day01 {

    private static final String DATA_NAME = "resources/data01.txt";
    private static final List<Integer> datas = Utils.parseInts(DATA_NAME);

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        int lastDepth = Integer.MAX_VALUE;
        int increasingDepth = 0;
        for (Integer depth : datas) {
            if (depth > lastDepth) {
                increasingDepth += 1;
            }
            lastDepth = depth;
        }
        System.out.println(increasingDepth);
    }

    private static void two() {
        List<Integer> threeSum = new ArrayList<>();
        for (int i = 0; i < datas.size() - 2; i++) {
            threeSum.add(datas.get(i) + datas.get(i + 1) + datas.get(i + 2));
        }
        int lastDepth = Integer.MAX_VALUE;
        int increasingDepth = 0;
        for (Integer depth : threeSum) {
            if (depth > lastDepth) {
                increasingDepth += 1;
            }
            lastDepth = depth;
        }
        System.out.println(increasingDepth);
    }
}
