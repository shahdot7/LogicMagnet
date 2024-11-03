import java.util.ArrayList;
import java.util.List;

public class MoveRules {
    private GameBoard gameBoard;

    public MoveRules(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public List<int[]> getPossibleMoves(Piece piece) {
        List<int[]> moves = new ArrayList<>();
        int[] pos = piece.getPosition();
        int x = pos[0];
        int y = pos[1];

        if (gameBoard.isInBounds(x, y - 1)) moves.add(new int[]{x, y - 1}); // Up
        if (gameBoard.isInBounds(x, y + 1)) moves.add(new int[]{x, y + 1}); // Down
        if (gameBoard.isInBounds(x + 1, y)) moves.add(new int[]{x + 1, y}); // Right
        if (gameBoard.isInBounds(x - 1, y)) moves.add(new int[]{x - 1, y}); // Left

        return moves;
    }
}
