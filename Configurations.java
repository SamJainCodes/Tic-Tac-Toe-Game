public class Configurations {

    private char[][] gameBoard; // declare instance variables 
    private int lengthToWin;
    private int maxLevels;

    public Configurations (int boardSize, int lengthToWin, int maxLevels){ // constructer which creates the grid and initialzes the instance variables
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        this.gameBoard = new char[boardSize][boardSize]; // its a nxn grid so same rows and col

        for(int i = 0; i < boardSize; i++){ // i and j rep row and col, iterate through each and make it empty
            for(int j = 0; j < boardSize; j++){
                gameBoard[i][j] = ' ';
            }
        }
    }

    public HashDictionary createDictionary(){ // this method creates a Dictonary of size 10000 and returns it while keeping it empty
        HashDictionary Dict = new HashDictionary(10000);
        
        return Dict;
    }

    public int repeatedConfiguration(HashDictionary hashTable){ // creates a string rep of the board game and returns value associated with it (-1 if dup)

        String config = ""; // empty string

        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[0].length; j++){ // loop through and add the char to end of strip
                config = config + gameBoard[i][j];
            }
        }

        return hashTable.get(config); // use the get method to get int score
    }

    public void addConfiguration(HashDictionary hashDictionary, int score){ // adds a configuration to the hash dict

        String config = ""; // same as previous just get a string rep of the board game 

        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[0].length; j++){
                config = config + gameBoard[i][j];
            }
        }

        Data record = new Data(config, score); // put method only takes Data as a parameter so create data object first 
        
        hashDictionary.put(record); // this method adds it in the dict and handles the case of it already being there by throwing exception

    }

    public void savePlay(int row, int col, char symbol){
        gameBoard[row][col] = symbol; // this method changes the symbol of a given block 
    }

    public boolean squareIsEmpty (int row, int col){ // this method checks whether a given square is empty by seeing if it has no char and is just = to space 
        if(gameBoard[row][col] == ' '){
            return true;
        }
        
        return false;
    }

    public boolean wins (char symbol){ // this method checks whether a given symbol won the game 

        return winDiagonalRight(symbol) || winHorizontal(symbol) || winVertical(symbol) || winDiagonalLeft(symbol); // created private methods for each case, using or as if any of them are true it will return true if all are false it returns false 
    }

    private boolean winHorizontal(char symbol){ // this method checks if we won horizontally (ooo)
        
        for(int i = 0; i < gameBoard.length; i++){ // we loop through all rows first 
            int count = 0; // set count to 0 each new row 
            for(int j = 0; j < gameBoard[0].length; j++){ // for each row we go through every col
                if(gameBoard[i][j] == symbol){
                    count++; // if any square in a row is equal to symbol we increment count
                }
            }
            if(count >= lengthToWin){
                return true; // return true if count is the required length 
            }
        }

        return false;

    }

    private boolean winVertical(char symbol){ // checks if we won vertically 

        for(int i = 0; i < gameBoard[0].length; i++){ // loops through each col first 
            int count = 0;
            for(int j = 0; j < gameBoard.length; j++){ // then for each col goes through each row hence vertical 
                if(gameBoard[j][i] == symbol){
                    count++;
                }
            }
            if(count >= lengthToWin){ // returns true if count is the required amount
                return true;
            }
        }

        return false;


    }

    private boolean winDiagonalRight(char symbol){ // checks if we won diagonally going one row down and one column right (i+1,j+1)

        for(int i = 0; i < gameBoard.length; i++){ // goes through each row first 
            for(int j = 0; j < gameBoard[0].length; j++){ // then each col for one row 
                int counter = 0;
                if(gameBoard[i][j] == symbol){ // if any of the square is the symbol we check its diagnol going down and right
                    counter = 1;

                    int i2 = i+1; // when going down and right, our row gets incremented by one and so does col
                    int j2 = j+1;

                    while(i2 < gameBoard.length && j2 < gameBoard[0].length){ // we keep incrementing by one til end of array 
                        if(gameBoard[i2][j2] == symbol){
                            counter++; // if it equals symbol keep incrementing 
                        }
                        else{
                            
                            break;
                        }

                        i2++;
                        j2++;
                    }

                    if(counter >= lengthToWin){
                        return true;
                    }
                }
            }
        }

        
        return false;
    }

    private boolean winDiagonalLeft(char symbol){ // checks if we won diagnolly going down one row and one colum to the left (i+1,j-1)

        for(int i = 0; i < gameBoard.length; i++){ // we start row first again but we start from top right instead of top left 
            for(int j = gameBoard[0].length -1; j >= 0; j--){
                int counter = 0;
                if(gameBoard[i][j] == symbol){
                    counter=1;

                    int i2 = i+1; // this time we are checking the diagonal going down and left so col keeps decreasing but row increases
                    int j2 = j-1;

                    while(j2 >= 0 && i2 < gameBoard.length){
                        if(gameBoard[i2][j2] == symbol){
                            counter++;
                        }
                        else{
                            
                            break;
                        }

                        i2++;
                        j2--;
                    }

                    if(counter >= lengthToWin){ // greater than or equal to since you can find something like "xxxx" when length to win is only 3 
                        return true;
                    }
                }
            }
        }
        return false;


    }

    public boolean isDraw(){ // checks whether the gameboard is at a draw 
        if(wins('X') || wins('O')){ // if someone won obvioisly not a draw 
            return false;
        }
        else{
            for(int i = 0; i < gameBoard.length; i++){ // loops through each square and if one is empty game still goes on hence not a draw 
                for(int j = 0; j < gameBoard[0].length; j++){
                    if(squareIsEmpty(i, j)){
                        return false;
                    }
                }
            }

            return true; // else draw 
        }
    }

    public int evalBoard(){ // gives back a number based on the game outcome 
        if(wins('O')){
            return 3; // returns 3 if comp won
        }

        else if(wins('X')){
            return 0; // 0 if human won
        }

        else if(isDraw()){
            return 2; // 2 if draw 
        }

        else{
            return 1; // 1 if undecided, no one won and not a draw 
        }
    }

    
}
