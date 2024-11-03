import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(5, 5, 1);

        // إضافة القطع
        board.addPiece(new Piece(Piece.PieceType.ATTRACTIVE, new int[]{2, 2}));
        board.addPiece(new Piece(Piece.PieceType.REPULSIVE, new int[]{2, 3}));
        board.addPiece(new Piece(Piece.PieceType.IRON, new int[]{4, 2}));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            board.displayBoard();
            System.out.println("Enter the piece to move (1 for ATTRACTIVE, 2 for REPULSIVE) or 'exit' to quit:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
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
            scanner.nextLine(); // Clear newline

            board.movePieceTo(pieceToMove, targetX, targetY);
        }

        scanner.close();
    }
}
