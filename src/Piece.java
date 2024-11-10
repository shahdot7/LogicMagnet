public class Piece {
    public enum PieceType {
        IRON, ATTRACTIVE, REPULSIVE, BLOCKED
    }
    @Override
    public Piece clone() {
        return new Piece(this.getType(), this.getPosition().clone());
    }
    public int getX() {
        return this.getPosition()[0];
    }

    public int getY() {
        return this.getPosition()[1];
    }
    private int[] previousPosition;

    public void saveCurrentPosition() {
        this.previousPosition = position.clone();
    }

    public int[] getPreviousPosition() {
        return previousPosition;
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
