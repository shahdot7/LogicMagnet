public class Piece {
    public enum PieceType {
        IRON, ATTRACTIVE, REPULSIVE, BLOCKED
    }

    private PieceType type;
    private int[] position;
    private int[] previousPosition;

    public Piece(PieceType type, int[] position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public Piece clone() {
        return new Piece(this.getType(), this.getPosition().clone());
    }

    public int getX() {
        return this.position[0];
    }

    public int getY() {
        return this.position[1];
    }

    public void saveCurrentPosition() {
        this.previousPosition = position.clone();
    }

    public int[] getPreviousPosition() {
        return previousPosition;
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
