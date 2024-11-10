import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    int width;
     int height;
    private int borderSize;
    private List<Piece> pieces;
    private List<int[]> targets;
    int[][] levelData;
    public List<int[]> getTargets() {
        return targets;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameBoard(int borderSize) {
        this.borderSize = borderSize;
        this.pieces = new ArrayList<>();
        this.targets = new ArrayList<>();
    }
    public void loadLevel(int[][] levelData) {
        pieces.clear();
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[y].length; x++) {
                switch (levelData[y][x]) {
                    case 1:
                        pieces.add(new Piece(Piece.PieceType.ATTRACTIVE, new int[]{x, y}));
                        break;
                    case 2:
                        pieces.add(new Piece(Piece.PieceType.REPULSIVE, new int[]{x, y}));
                        break;
                    case 3:
                        pieces.add(new Piece(Piece.PieceType.IRON, new int[]{x, y}));
                        break;
                    default:

                        break;
                }
            }
        }
    }
    public void initializeLevel(int[][] levelData) {
        this.levelData = levelData;
        pieces.clear();
        targets.clear();

        height = levelData.length;
        width = levelData[0].length;


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = levelData[y][x];
                switch (value) {
                    case 1:
                        pieces.add(new Piece(Piece.PieceType.ATTRACTIVE, new int[]{x, y}));
                        break;
                    case 2:
                        pieces.add(new Piece(Piece.PieceType.REPULSIVE, new int[]{x, y}));
                        break;
                    case 3:
                        pieces.add(new Piece(Piece.PieceType.IRON, new int[]{x, y}));
                        break;
                    case 4:
                        targets.add(new int[]{x, y});
                        System.out.println("Target added at: (" + x + ", " + y + ")");
                        break;
                    default:

                        break;
                }
            }
        }
    }


    public boolean isInBounds(int x, int y) {
        // Remove the borderSize adjustment since it's causing the blocking issue
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    public boolean checkForWin() {
        System.out.println("Checking win condition...");
        for (int[] target : targets) {
            Piece piece = getPieceAt(target[0], target[1]);

            if (piece == null) {
                System.out.println("No piece at target: (" + target[0] + ", " + target[1] + ")");
                return false;
            }
        }
        //System.out.println("All targets met!");
        return true;
    }



    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void applyAttractionOrRepulsion(Piece movedPiece) {
        int[] movedPosition = movedPiece.getPosition();

        for (Piece ironPiece : pieces) {
            if (ironPiece.getType() == Piece.PieceType.IRON) {
                int[] ironPos = ironPiece.getPosition();

                int dx = movedPosition[0] - ironPos[0];
                int dy = movedPosition[1] - ironPos[1];

                if (Math.abs(dx) + Math.abs(dy) == 1) {
                    int newX = ironPos[0];
                    int newY = ironPos[1];

                    if (movedPiece.getType() == Piece.PieceType.REPULSIVE) {

                        newX = ironPos[0] - dx;
                        newY = ironPos[1] - dy;
                    } else if (movedPiece.getType() == Piece.PieceType.ATTRACTIVE) {
                        newX = movedPosition[0];
                        newY = movedPosition[1];
                    }

                    if (isInBounds(newX, newY) && getPieceAt(newX, newY) == null) {
                        ironPiece.setPosition(new int[]{newX, newY});
                        System.out.println("Iron piece moved to (" + newX + "," + newY + ")");
                    }
                }
            }
        }
    }



    public List<int[]> getPossibleMoves(Piece piece) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[] position = piece.getPosition();

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] direction : directions) {
            int newX = position[0] + direction[0];
            int newY = position[1] + direction[1];

            if (isInBounds(newX, newY) && getPieceAt(newX, newY) == null) {
                possibleMoves.add(new int[]{newX, newY});
            }
        }
        return possibleMoves;
    }
    public List<Piece> clonePieces() {
        List<Piece> clonedPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            clonedPieces.add(piece.clone());
        }
        return clonedPieces;
    }
    public void undoMove(Piece piece) {
        if (piece.getPreviousPosition() != null) {
            piece.setPosition(piece.getPreviousPosition());
            System.out.println("Undo move: piece returned to previous position.");
        }
    }



    public void movePieceTo(Piece piece, int targetX, int targetY) {
        if (levelData[targetY][targetX] == -1) {
            System.out.println("Cannot move to blocked position (X)");
            return;
        }
        if (isInBounds(targetX, targetY) && getPieceAt(targetX, targetY) == null) {
            piece.saveCurrentPosition();
            piece.setPosition(new int[]{targetX, targetY});
            applyAttractionOrRepulsion(piece);
            System.out.println("Piece moved to (" + targetX + ", " + targetY + ").");
//
//            if (checkForWin()) {
//                System.out.println("You have completed the level!");
//
//            }
        } else {
            System.out.println("Move is not possible.");
        }
    }

    public void setPieces(List<Piece> newPieces) {
        this.pieces = new ArrayList<>(newPieces);
    }

    public void displayBoard() {
        System.out.println("Displaying game board:");


        for (int x = 0; x < width + 2; x++) {
            System.out.print("# ");
        }
        System.out.println();

        for (int y = 0; y < height; y++) {
            System.out.print("# ");
            for (int x = 0; x < width; x++) {
                if (levelData[y][x] == -1) {
                    System.out.print("X ");
                } else {
                    Piece piece = getPieceAt(x, y);
                    if (piece != null) {
                        System.out.print(getPieceSymbol(piece) + " ");
                    } else if (isTargetPosition(x, y)) {
                        System.out.print("O ");
                    } else {
                        System.out.print(". ");
                    }
                }
            }
            System.out.println("#");
        }


        for (int x = 0; x < width + 2; x++) {
            System.out.print("# ");
        }
        System.out.println();
    }


    private boolean isTargetPosition(int x, int y) {
        for (int[] target : targets) {
            if (target[0] == x && target[1] == y) {
                return true;
            }
        }
        return false;
    }
    public void displayTransition(int[][] nextLevelData) {

        displayBoard();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

        System.out.println("Loading next level...");

        initializeLevel(nextLevelData);
        displayBoard();
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
            case BLOCKED: return 'X';
            default:
                return '?';
        }
    }
}
