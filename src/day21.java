import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day21 {

    private static final String DATA_NAME = "resources/data21.txt";
    private static final Map<Game, Long[]> gameToWins = new HashMap<>();
    private static int[] pawns;
    private static int[] scores;
    private static int rolls;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initPawns();
        int p = 0;
        while (true) {
            for (int j = 0; j < 3; j++) {
                rolls++;
                pawns[p] += (rolls - 1) % 100 + 1;
            }
            scores[p] += (pawns[p] - 1) % 10 + 1;
            if (scores[p] >= 1000) break;
            p = 1 - p;
        }
        int min = Math.min(scores[0], scores[1]);
        System.out.println(min * rolls);
    }

    private static void two() {
        initPawns();
        Game game = new Game(pawns[0], pawns[1], scores[0], scores[1]);
        Long[] win = worker(game);
        System.out.println(Math.max(win[0], win[1]));
    }

    private static void initPawns() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day21.DATA_NAME));
            scores = new int[2];
            pawns = new int[2];
            rolls = 0;
            pawns[0] = reader.readLine().charAt(28) - '0';
            pawns[1] = reader.readLine().charAt(28) - '0';
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Long[] worker(Game game) {
        if (gameToWins.containsKey(game)) return gameToWins.get(game);
        Long[] wins = new Long[]{0L, 0L};
        for (int diceScore : possibleDice()) {
            int pawn = game.pawn1 + diceScore;
            pawn = (pawn - 1) % 10 + 1;
            int score = game.score1 + pawn;
            if (score >= 21) {
                wins[0]++;
            } else {
                Long[] newWins = worker(new Game(game.pawn2, pawn, game.score2, score));
                wins[0] += newWins[1];
                wins[1] += newWins[0];
            }
        }
        gameToWins.put(game, wins);
        return wins;
    }

    private static List<Integer> possibleDice() {
        List<Integer> possibleDice = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    possibleDice.add(i + j + k + 3);
                }
            }
        }
        return possibleDice;
    }

    private static record Game(int pawn1, int pawn2, int score1, int score2) {
    }
}