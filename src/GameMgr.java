
public class GameMgr {
    private IDisplay display_ ;                 // The display for displaying games
    private Board board_ ;                      // The data model for the game
    private Player x_ ;                         // The X player
    private Player o_ ;                         // The O Player
    private int xwon_ ;                         // The number of games won by the X player
    private int owon_ ;                         // The number of games won by the O player
    private int tied_ ;                         // The number of games tied

    //
    // Check the player argument given on the command line.  If the argument is valid, create the
    // player and assign to the right slot in the player array.
    //
    // Arguments:
    //   players - Array that will store the two players to play the game
    //   index - player 1 (value 0) or player 2 (value 1)
    //   piece - game piece type (X or O)
    //   type - player type (human, expert, intermediate, novice)
    //
    static private boolean checkPlayer(Player [] players, int index, GamePiece piece, String type) throws IllegalArgumentException{
        boolean ret = true ;

        if (index != 0 && index != 1) {
            throw new IllegalArgumentException("internal error - index value '" + index + "' is not valid, must be 0 or 1") ;
        }

        if (type.equals("human")) {
            players[index] = new HumanPlayer(piece);
        }
        else if (type.equals("novice")) {
            players[index] = new ComputerPlayer(piece, 0.3);
        }
        else if (type.equals("intermediate")) {
            players[index] = new ComputerPlayer(piece, 0.6);            
        }
        else if (type.equals("expert")) {
            players[index] = new ComputerPlayer(piece, 1.0);
        }
        else {
            System.err.println("'" + type + "' is not a valid player type");
            ret = false ;
        }

        return ret ;
    }

    //
    // Print help information
    //
    static private void help() {
        System.out.println("Tic-Tac-Toe - the classic game of tic-tac-toe") ;
        System.out.println("Arguments:") ;
        System.out.println("  --display displaytype      (console or fancy)") ;
        System.out.println("  --count integer            (the number of games to play)");
        System.out.println("  --1 playertype             player type for player one (human, expert, intermediate, novice)");
        System.out.println("  --2 playertype             player type for player two (human, expert, intermediate, novice)");
    }

    //
    // Main entry point for the program.  Processes command line arguments and if all
    // command line arguments are valid, creates a GameMgr object to play the games.
    //
    // Arguments:
    //   --display displaytype      ' console or fancy
    //   --count number             ' number of games to play
    //   --1 playertype             ' human, expert, intermediate, novice
    //   --2 playertype             ' human, expert, intermediate, novice
    //   --help                     ' print this information
    //
    //
    static public void main(String[] args) {
        IDisplay disp = null ;
        Player[] players = new Player[2];
        int count = 10 ;                            // The default value is --count is not specified
        boolean lines = true ;                      // The default if using the fance display is to use line art

        int i = 0 ;
        //
        // Process the command line arguments
        //
        while (i < args.length) {
            String arg = args[i] ;
            if (arg.equals("--display")) {
                if (i == args.length - 1) {
                    System.err.println("the --display argument must be followed by the display type");
                    System.exit(1);
                }

                if (disp != null) {
                    System.err.println("the --display argument has already been given, only one per program invocation is allowed");
                    System.exit(1);                    
                }

                i++ ;
                arg = args[i] ;
                if (arg.equals("fancy")) {
                    disp = new FancyDisplay(lines);
                }
                else if (arg.equals("console")) {
                    disp = new ConsoleDisplay() ;
                }
                else {
                    System.err.println("'" + arg + "' is not a valid display type");
                    System.exit(1);                     
                }
            }
            else if (arg.equals("--1")) {
                if (i == args.length - 1) {
                    System.err.println("the --1 argument must be followed by a player type");
                    System.exit(1);
                }
                i++ ;
                if (!checkPlayer(players, 0, GamePiece.X, args[i])) {
                    System.exit(1) ;
                }
            }
            else if (arg.equals("--2")) {
                if (i == args.length - 1) {
                    System.err.println("the --2 argument must be followed by a player type");
                    System.exit(1);
                }
                i++ ;
                if (!checkPlayer(players, 1, GamePiece.O, args[i])) {
                    System.exit(1) ;
                }
            }
            else if (arg.equals("--count")) {
                if (i == args.length - 1) {
                    System.err.println("the --count argument must be followed by an integer value (e.g. --count 200)");
                    System.exit(1);
                }
                i++ ;
                try {
                    count = Integer.parseInt(args[i]) ;
                }
                catch(NumberFormatException ex) {
                    System.err.println("the value '" + args[i] + "' following the --count argument is not a valid integer");
                    System.exit(1);                    
                }
            }
            else if (arg.equals("--no-lines")) {
                lines = false ;
            }
            else if (arg.equals("--help")) {
                help() ;
                System.exit(0);
            }
            else {
                    System.err.println("the argument '" + arg + "'is not a valid argument") ;
                    help() ;
                    System.exit(1);                
            }
            i++ ;
        }

        if (players[0] == null) {
            System.err.println("the --1 argument specifying the type of player for player one is required and was not given");
            System.exit(1);            
        }

        if (players[0] == null) {
            System.err.println("the --2 argument specifying the type of player for player two is required and was not given");
            System.exit(1);            
        }

        if (disp == null) {
            disp = new ConsoleDisplay() ;
            disp.displayMessage("The --disp argument was not given, assuming a 'console' display");
        }

        GameMgr mgr = new GameMgr(disp, players[0], players[1]) ;
        mgr.play(count) ;
    }

    //
    // Create a GameMgr object to play the tic-tac-toe game
    //
    // Arguments:
    //   count - the number of games to play
    //   x - the first player (using X game piece)
    //   o - the second player (using the O game piece)
    //
    private GameMgr(IDisplay display, Player x, Player o) {
        this.display_ = display ;
        this.x_ = x ;
        this.o_ = o ;
        this.board_ = new Board();

        this.x_.setDisplay(display) ;
        this.o_.setDisplay(display);
    }

    //
    // Play a single game of tic-tac-toe
    // We return information about who won the game
    //
    private Board.GameWonInfo playOneGame() {
        Board.GameWonInfo info = null ;
        Player who = x_ ;                       // The curret player, alternates between x_ and o_
        board_.clearBoard();                    // Clear the board for a new game

        while (true) {
            //
            // If the player needs to see the board (human player) display it
            //
            if (who.showBoard() || display_.alwaysUpdateBoard()) {
                display_.updateBoardDisplay(board_);
            }

            //
            // Ask the player for their next move.  Note, the
            // board is passed in because the player classes store no state information
            // about the game.  The player class is asked to look at the current state of the board
            // and decide what move to make.
            //
            BoardPosition pos = who.nextMove(board_) ;

            //
            // Set the square in the board.
            //
            board_.setPiece(pos, who.getMyGamePiece());

            info = board_.hasWon() ;
            if (info != null) {
                //
                // Someone won or the board is full and its a tie, break out of the game loop
                //
                display_.updateBoardDisplay(board_);
                break ;
            }

            //
            // Now one has won yet
            //
            if (who == x_) {
                who = o_ ;
            }
            else {
                who = x_ ;
            }
        }

        return info ;
    }

    //
    // Reset the number of games won and tied
    //
    private void resetScore() {
        xwon_ = 0 ;
        owon_ = 0 ;
        tied_ = 0 ;
    }

    //
    // Play the series of games requested
    //
    private void play(int count) {
        int which = 0 ;                 // This counts which game we are on

        //
        // Reset the number of games won and tied to zero
        //
        resetScore() ;

        while (which < count) {
            // 
            // Display the face we are starting a new game
            //
            display_.newGame(which + 1, count);

            //
            // Place the game
            //
            Board.GameWonInfo info = playOneGame() ;

            //
            // Increment the number of games we have played
            //
            which++ ;

            //
            // Keep track of the number won and tied 
            //
            if (info.who == GamePiece.X) {
                xwon_++ ;
            }
            else if (info.who == GamePiece.O) {
                owon_++ ;
            }
            else {
                tied_++ ;
            }

            //
            // Show who won the game
            //
            display_.showWon(info) ;

            //
            // Show total games won and tied
            //
            display_.showTotal(false, xwon_, owon_, tied_);            
        }

        //
        // Show total games won and tied
        //
        display_.showTotal(true, xwon_, owon_, tied_);
    }
}
