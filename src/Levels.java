import java.util.ArrayList;
import java.util.List;

public class Levels {
    private List<int[][]> levels;

    public Levels() {
        levels = new ArrayList<>();

        // 1
        levels.add(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
        });

        // 2
        levels.add(new int[][]{
                {0, 0, 0, 1, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 0},
                {0, 0, 2, 0, 1, 0, 0},
                {0, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
        });

        // 3
        levels.add(new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 1, 0, 0, 0},
                {0, 0, 0, 2, 0, 1, 0},
                {0, 0, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
        });
    }

    public int[][] getLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.size()) {
            return levels.get(levelIndex);
        }
        return null;
    }
}
