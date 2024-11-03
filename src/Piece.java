public class Piece {
    public enum PieceType {
        IRON, ATTRACTIVE, REPULSIVE
    }

    private PieceType type;
    private int[] position;

    public Piece(PieceType type, int[] position) {
        this.type = type;
        this.position = position;
    }

    public PieceType getType() {
        return type;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] newPosition) {
        this.position = newPosition;
    }
}
