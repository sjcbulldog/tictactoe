import java.util.ArrayList;
import java.util.List;

//
// This class represents a tic tac toe board with pieces placed on the
// board.  This class includes methods to get and set pices on the board.  It also
// contains methods to determine if either X or O has won or the board is full and it
// is a tie.  Finally, this class includes methods to help player classes by providing
// search methods to find empty spots on the board, or rows, columns, or diagnols that
// have two squares occupied and the third empty.
//
public class Board {
    public class GameWonInfo
    {
        public final GamePiece who ;                                    // Who won the game X or O
        public final BoardPosition [] where ;                           // The location where the game was won

        public GameWonInfo(GamePiece who, BoardPosition [] where) {
            this.who = who ;
            this.where = where ;
        }
    }

    //
    // The board is stored as a one dimensional array.  This makes it a little easier to
    // deal with the creation.
    //
    private GamePiece [] board_ ;

    //
    // Create a new board
    public Board() {
        board_ = new GamePiece[9] ;
        clearBoard() ;
    }

    //
    // Return the total number of squares played so far
    //
    public int totalPlayed() {
        int total = 0 ;
        for(int row = 0 ; row < 3 ; row++) {
            for(int col = 0 ; col < 3 ; col++) {
                if (getPiece(new BoardPosition(row, col)) != GamePiece.EMPTY) {
                    total++ ;
                }
            }
        }

        return total ;
    }

    //
    // Reset the board to all empty
    //
    public void clearBoard() {
        for(int i = 0 ; i < board_.length ; i++) {
            board_[i] = GamePiece.EMPTY ;
        }
    }

    //
    // Get the game piece type at the given row and column
    //
    public GamePiece getPiece(BoardPosition pos) {
        return board_[computeBoardIndex(pos.Row, pos.Col)] ;
    }

    //
    // Set the game piece type at the given row and column
    //
    public void setPiece(BoardPosition pos, GamePiece gp) {
        assert getPiece(pos) == GamePiece.EMPTY ;
        board_[computeBoardIndex(pos.Row, pos.Col)] = gp ;
    }

    //
    // Return the list of empty locations on the board.  This is used by
    // clients that want to know what spots are empty.  With this method clients
    // don't have to iterate over the board and create a list of empty spots.
    //
    public List<BoardPosition> findEmpty() {
        List<BoardPosition> ret = new ArrayList<BoardPosition>() ;
         
        for(int row = 0 ; row < 3 ; row++) {
            for(int col = 0 ; col < 3 ; col++) {
                if (getPiece(new BoardPosition(row, col)) == GamePiece.EMPTY) {
                    ret.add(new BoardPosition(row, col)) ;
                }
            }
        }

        return ret;
    }

    //
    // Find any of the rows, columns, or diagnols where there are two of the game piece
    // given and an empty spot.  This is a row, column, or diagnol where either we could win
    // or we need to block.
    //
    public BoardPosition hasTwo(GamePiece p) {
        BoardPosition pos = null ;

        for(int set = 0 ; set < WinningSets.length ; set++) {
            //
            // We expect all winning sets to be three entries long.
            //
            assert WinningSets[set].length == 3 ;

            int total = 0 ;

            BoardPosition spos = null ;
            for(int entry = 0 ; entry < WinningSets[set].length ; entry++) {
                GamePiece gp = getPiece(WinningSets[set][entry]) ;
                total += gp.value ;

                if (gp == GamePiece.EMPTY) {
                    spos = WinningSets[set][entry] ;
                }
            }

            if (total == 2.0 * p.value) {
                pos = spos ;
                break ;
            }
        }

        return pos ;
    }

    //
    // Determine if anyone has won the game.  If not one has won
    // null is returned.  If someone has won, the GameWonInfo is returned
    // providing information about who won and where they won.
    //
    public GameWonInfo hasWon() {
        GameWonInfo ret = null ;

        for(int set = 0 ; set < WinningSets.length ; set++) {
            //
            // We expect all winning sets to be three entries long.
            //
            assert WinningSets[set].length == 3 ;

            int total = 0 ;

            for(int entry = 0 ; entry < WinningSets[set].length ; entry++) {
                GamePiece gp = getPiece(WinningSets[set][entry]) ;
                total += gp.value ;
            }

            if (total == 3 || total == -3) {
                GamePiece who = getPiece(WinningSets[set][0]);
                BoardPosition [] where  = WinningSets[set] ;
                ret = new GameWonInfo(who, where) ;
            }
        }

        if (ret == null && findEmpty().size() == 0) {
            //
            // There are no emptry squared
            //
            ret = new GameWonInfo(GamePiece.EMPTY, null);
        }

        return ret ;
    }

    //
    // Compute the index into the board_ array given a row and column.  The
    // row and column values are zero based.
    //
    private int computeBoardIndex(int row, int col) {
        return row * 3 + col ;
    }

    //
    // The combinations of squares where you can win the game.
    //
    // This could be expressed as an algorithm as well.  This is really just a personal choice
    //
    private BoardPosition [][] WinningSets = new BoardPosition[][] {
        new BoardPosition[] { new BoardPosition(0, 0) , new BoardPosition(0, 1), new BoardPosition(0, 2) },     // First row
        new BoardPosition[] { new BoardPosition(1, 0) , new BoardPosition(1, 1), new BoardPosition(1, 2) },     // Second row
        new BoardPosition[] { new BoardPosition(2, 0) , new BoardPosition(2, 1), new BoardPosition(2, 2) },     // Third row
        new BoardPosition[] { new BoardPosition(0, 0) , new BoardPosition(1, 0), new BoardPosition(2, 0) },     // First column
        new BoardPosition[] { new BoardPosition(0, 1) , new BoardPosition(1, 1), new BoardPosition(2, 1) },     // Second column
        new BoardPosition[] { new BoardPosition(0, 2) , new BoardPosition(1, 2), new BoardPosition(2, 2) },     // Third column
        new BoardPosition[] { new BoardPosition(0, 0) , new BoardPosition(1, 1), new BoardPosition(2, 2) },     // First diagonal
        new BoardPosition[] { new BoardPosition(0, 2) , new BoardPosition(1, 1), new BoardPosition(2, 0) },     // Second diagonal
    } ;
}
