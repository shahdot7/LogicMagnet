import java.util.*;

public class Solve {
    private GameBoard board;

    public Solve(GameBoard board) {
        this.board = board;
    }

    // دالة البحث بعرض الشجرة (BFS)
    public void solveWithBFS() {
        Queue<GameState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        GameState initialState = new GameState(board.getPieces(), null);
        queue.add(initialState);
        visited.add(initialState.getStateString());

        while (!queue.isEmpty()) {
            GameState currentState = queue.poll();
            board.setPieces(currentState.getPieces());

            if (board.checkForWin()) {
                System.out.println("تم العثور على الحل باستخدام BFS!");
                printSolutionPath(currentState);
                return;
            }

            for (Piece piece : board.getPieces()) {
                List<int[]> possibleMoves = board.getPossibleMoves(piece);

                for (int[] move : possibleMoves) {
                    board.movePieceTo(piece, move[0], move[1]);

                    List<Piece> newPieces = board.clonePieces();
                    GameState newState = new GameState(newPieces, currentState);

                    String stateString = newState.getStateString();
                    if (!visited.contains(stateString)) {
                        visited.add(stateString);
                        queue.add(newState);
                    }

                    board.undoMove(piece);
                }
            }
        }

        System.out.println("لم يتم العثور على حل باستخدام BFS.");
    }

    public void solveWithUCS(Map<String, Integer> costMap) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<String> visited = new HashSet<>();

        GameState initialState = new GameState(board.getPieces(), null);
        Node initialNode = new Node(initialState, 0, null);
        openList.add(initialNode);
        visited.add(initialState.getStateString());

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            GameState currentState = currentNode.state;
            board.setPieces(currentState.getPieces());

            if (board.checkForWin()) {
                System.out.println("تم العثور على الحل باستخدام UCS!");
                printSolutionPath(currentState);
                return;
            }

            for (Piece piece : board.getPieces()) {
                List<int[]> possibleMoves = board.getPossibleMoves(piece);

                for (int[] move : possibleMoves) {
                    board.movePieceTo(piece, move[0], move[1]);

                    List<Piece> newPieces = board.clonePieces();
                    GameState newState = new GameState(newPieces, currentState);

                    String stateString = newState.getStateString();
                    if (!visited.contains(stateString)) {
                        visited.add(stateString);
                        int stepCost = getMoveCost(piece, move, costMap);
                        int totalCost = currentNode.cost + stepCost;
                        Node newNode = new Node(newState, totalCost, currentNode);
                        openList.add(newNode);
                    }

                    board.undoMove(piece);
                }
            }
        }

        System.out.println("لم يتم العثور على حل باستخدام UCS.");
    }

    public void solveWithDFS() {
        Stack<GameState> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        GameState initialState = new GameState(board.getPieces(), null);
        stack.push(initialState);
        visited.add(initialState.getStateString());

        while (!stack.isEmpty()) {
            GameState currentState = stack.pop();
            board.setPieces(currentState.getPieces());

            if (board.checkForWin()) {
                System.out.println("تم العثور على الحل باستخدام DFS!");
                printSolutionPath(currentState);
                return;
            }

            for (Piece piece : board.getPieces()) {
                List<int[]> possibleMoves = board.getPossibleMoves(piece);

                for (int[] move : possibleMoves) {
                    board.movePieceTo(piece, move[0], move[1]);

                    List<Piece> newPieces = board.clonePieces();
                    GameState newState = new GameState(newPieces, currentState);

                    String stateString = newState.getStateString();
                    if (!visited.contains(stateString)) {
                        visited.add(stateString);
                        stack.push(newState);
                    }

                    board.undoMove(piece);
                }
            }
        }

        System.out.println("لم يتم العثور على حل باستخدام DFS.");
    }

    private int getMoveCost(Piece piece, int[] move, Map<String, Integer> costMap) {
        String key = piece.getType().name() + "_" + Arrays.toString(move);
        return costMap.getOrDefault(key, 1); // افتراض كلفة 1 إذا لم يتم تحديدها
    }


    private void printSolutionPath(GameState state) {
        Stack<GameState> path = new Stack<>();
        while (state != null) {
            path.push(state);
            state = state.getPreviousState();
        }

        while (!path.isEmpty()) {
            GameState step = path.pop();
            board.setPieces(step.getPieces());
            board.displayBoard();
            System.out.println();
        }
    }

    private class GameState {
        private List<Piece> pieces;
        private GameState previousState;

        public GameState(List<Piece> pieces, GameState previousState) {

            this.pieces = new ArrayList<>();
            for (Piece p : pieces) {
                this.pieces.add(p.clone());
            }
            this.previousState = previousState;
        }

        public List<Piece> getPieces() {
            return pieces;
        }

        public GameState getPreviousState() {
            return previousState;
        }

        public String getStateString() {
            StringBuilder state = new StringBuilder();
            for (Piece piece : pieces) {
                state.append(piece.getType()).append(":").append(piece.getX()).append(",").append(piece.getY()).append(";");
            }
            return state.toString();
        }
    }


    private class Node {
        GameState state;
        int cost;
        Node parent;

        public Node(GameState state, int cost, Node parent) {
            this.state = state;
            this.cost = cost;
            this.parent = parent;
        }

        public int getCost() {
            return cost;
        }
    }
}
