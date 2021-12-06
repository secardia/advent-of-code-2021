import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<List<Integer>> get2dListOf0(int row, int col) {
        List<List<Integer>> lists = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            List<Integer> list = new ArrayList<>(col);
            for (int j = 0; j < col; j++) {
                list.add(0);
            }
            lists.add(list);
        }
        return lists;
    }
}
