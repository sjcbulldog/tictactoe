public class BoardPosition {
    public final int Row ;
    public final int Col ;

    public BoardPosition(int row, int col) {
        this.Row = row ;
        this.Col = col ;
    }

    public boolean isValid() {
        return Row >= 0 && Row <= 2 && Col >= 0 && Col <=2 ;
    }
}
