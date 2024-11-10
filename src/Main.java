import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Levels levels = new Levels();
        GameBoard board = new GameBoard(1);
        Scanner scanner = new Scanner(System.in);

        int currentLevel = 0;
        boolean gameRunning = true;

        while (gameRunning) {
            int[][] levelData = levels.getLevel(currentLevel);

            if (levelData == null) {
                System.out.println("Congratulations! You've completed all levels.");
                break;
            }

            board.initializeLevel(levelData);

            while (true) {
                board.displayBoard();
                System.out.println("Enter the piece to move (1 for ATTRACTIVE, 2 for REPULSIVE), 'bfs' to solve with BFS, 'dfs' to solve with DFS, or 'exit' to quit:");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    gameRunning = false;
                    break;
                }

                if (input.equalsIgnoreCase("bfs")) {
                    Solve solver = new Solve(board);
                    solver.solveWithBFS();
                    continue;
                }


                if (input.equalsIgnoreCase("dfs")) {
                    Solve solver = new Solve(board);
                    solver.solveWithDFS();
                    continue;
                }

                int pieceChoice;
                try {
                    pieceChoice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                Piece pieceToMove = null;
                if (pieceChoice == 1) {
                    pieceToMove = board.getPieces().stream()
                            .filter(p -> p.getType() == Piece.PieceType.ATTRACTIVE)
                            .findFirst()
                            .orElse(null);
                } else if (pieceChoice == 2) {
                    pieceToMove = board.getPieces().stream()
                            .filter(p -> p.getType() == Piece.PieceType.REPULSIVE)
                            .findFirst()
                            .orElse(null);
                }

                if (pieceToMove == null) {
                    System.out.println("Piece not found. Try again.");
                    continue;
                }

                System.out.println("Enter target X coordinate:");
                int targetX = scanner.nextInt();
                System.out.println("Enter target Y coordinate:");
                int targetY = scanner.nextInt();
                scanner.nextLine();

                board.movePieceTo(pieceToMove, targetX, targetY);

                if (board.checkForWin()) {
                    currentLevel++;
                    int[][] nextLevel = levels.getLevel(currentLevel);
                    if (nextLevel != null) {
                        board.displayTransition(nextLevel);
                        break;
                    }
                }

            }
        }
        scanner.close();
    }
}
