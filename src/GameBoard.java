import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int width;
    private int height;
    private int borderSize;
    private List<Piece> pieces;

    public GameBoard(int width, int height, int borderSize) {
        this.width = width;
        this.height = height;
        this.pieces = new ArrayList<>();
    }

    public boolean isInBounds(int x, int y) {
        return x >= borderSize && x < width + borderSize && y >= borderSize && y < height + borderSize;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }


    public void applyAttractionOrRepulsion(Piece movedPiece) {
        int[] movedPosition = movedPiece.getPosition();

        for (Piece piece : pieces) {
            if (piece != movedPiece && piece.getType() == Piece.PieceType.IRON) {
                int[] ironPosition = piece.getPosition();
                int distanceX = movedPosition[0] - ironPosition[0];
                int distanceY = movedPosition[1] - ironPosition[1];

                int newX = ironPosition[0];
                int newY = ironPosition[1];

                if (movedPiece.getType() == Piece.PieceType.ATTRACTIVE) {
                    newX += Integer.signum(distanceX);
                    newY += Integer.signum(distanceY);
                } else if (movedPiece.getType() == Piece.PieceType.REPULSIVE) {
                    newX -= Integer.signum(distanceX);
                    newY -= Integer.signum(distanceY);
                }

                if (isInBounds(newX, newY) && getPieceAt(newX, newY) == null) {
                    piece.setPosition(new int[]{newX, newY});
                }
            }
        }
    }


    public void movePieceTo(Piece piece, int targetX, int targetY) {
        if (isInBounds(targetX, targetY) && getPieceAt(targetX, targetY) == null) {
            piece.setPosition(new int[]{targetX, targetY});
            applyAttractionOrRepulsion(piece);
            System.out.println("Piece moved to (" + targetX + ", " + targetY + ").");
        } else {
            System.out.println("Move is not possible.");
        }
    }

    public void displayBoard() {
        for (int y = 0; y < height + 2 * borderSize; y++) {
            for (int x = 0; x < width + 2 * borderSize; x++) {
                if (isInBounds(x, y)) {
                    Piece piece = getPieceAt(x - borderSize, y - borderSize);
                    if (piece != null) {
                        System.out.print(getPieceSymbol(piece) + " ");
                    } else {
                        System.out.print(". ");
                    }
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }

    private Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            int[] pos = piece.getPosition();
            if (pos[0] == x && pos[1] == y) {
                return piece;
            }
        }
        return null;
    }

    private char getPieceSymbol(Piece piece) {
        switch (piece.getType()) {
            case IRON:
                return 'I';
            case ATTRACTIVE:
                return 'A';
            case REPULSIVE:
                return 'R';
            default:
                return '?';
        }
    }
}
