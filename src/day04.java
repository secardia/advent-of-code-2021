import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class day04 {

    private static final String DATA_NAME = "resources/data04.txt";
    private static List<Board> boards;
    private static List<Integer> moves;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initDataAndMoves();
        for (Integer move : moves) {
            for (Board data : boards) {
                if (data.play(move)) {
                    System.out.println(data.computeWinningScore());
                    System.exit(0);
                }
            }
        }
    }

    private static void two() {
        initDataAndMoves();
        boolean last = false;
        for (Integer move : moves) {
            Iterator<Board> iterator = boards.iterator();
            while (iterator.hasNext()) {
                Board b = iterator.next();
                if (b.play(move)) {
                    if (last) {
                        System.out.println(b.computeWinningScore());
                        System.exit(0);
                    }
                    iterator.remove();
                }
            }
            if (boards.size() == 1) {
                last = true;
            }
        }
    }

    private static void initDataAndMoves() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day04.DATA_NAME));
            moves = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
            // Parse boards
            List<String> lines = reader.lines().map(String::strip).collect(Collectors.toList());
            boards = new ArrayList<>();
            Board board = new Board();
            for (String line : lines) {
                if (line.isBlank()) {
                    board = new Board();
                    boards.add(board);
                } else {
                    board.getBoard().add(Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Board {
        private final List<List<Integer>> board;
        private final List<List<Boolean>> markedBoard;
        private Integer lastFound;

        public Board() {
            board = new ArrayList<>();
            markedBoard = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                markedBoard.add(new ArrayList<>(List.of(false, false, false, false, false)));
            }
        }

        public boolean play(int num) {
            for (int i = 0; i < board.size(); i++) {
                for (int j = 0; j < board.size(); j++) {
                    if (board.get(i).get(j) == num) {
                        lastFound = num;
                        markedBoard.get(i).set(j, true);
                        return hasWin(i, j);
                    }
                }
            }
            return false;
        }

        private boolean hasWin(int c, int r) {
            boolean win = true;
            for (Boolean b : markedBoard.get(c)) {
                if (!b) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
            win = true;
            for (List<Boolean> booleans : markedBoard) {
                if (!booleans.get(r)) {
                    win = false;
                    break;
                }
            }
            return win;
        }

        public int computeWinningScore() {
            int score = 0;
            for (int i = 0; i < board.size(); i++) {
                for (int j = 0; j < board.size(); j++) {
                    if (!markedBoard.get(i).get(j)) {
                        score += board.get(i).get(j);
                    }
                }
            }
            return score * lastFound;
        }

        public List<List<Integer>> getBoard() {
            return board;
        }
    }
}