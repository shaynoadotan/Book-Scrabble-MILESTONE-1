package test;
import java.util.ArrayList;

public class Board {

    int BOARD_SIZE = 15;
    ArrayList<Word> wordsOnBoard= new ArrayList<>();
    Tile[][] board = new Tile[BOARD_SIZE][BOARD_SIZE];
    char[][] boardColor = new char[BOARD_SIZE][BOARD_SIZE];

    private static Board b = null;


    public static Board getBoard() {
        if (b == null)
            b = new Board();
        return b;

    }


    public Tile[][] getTiles() {
        return (Tile[][]) board.clone();
    }

    public int numOfTiles() {
        int count = 0;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                if (board[i][j] != null)
                    count++;
        return count;
    }


    public boolean isCongruent(Word word, Tile[][] board)//the function checks if tile is close/on another tile
    {
        int count = 0;

        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.isVertical()) {
                if (board[word.getRow() + i][word.getCol()] != null && board[word.getRow() + i][word.getCol()] != word.getTiles()[i])
                    return false;
                else if (board[word.getRow() + i][word.getCol()] == word.getTiles()[i])
                    count++;
            } else {
                if (board[word.getRow()][word.getCol() + i] != null && board[word.getRow()][word.getCol() + i] != word.getTiles()[i])
                    return false;
                else if (board[word.getRow()][word.getCol() + i] == word.getTiles()[i])
                    count++;
            }
        }

        if (count == 0)
            return false;
        return true;
    }

    public boolean isInMatrix(Word word) {
        if (word.isVertical()) {
            if (word.getRow() >= 0 && word.getRow() + word.getTiles().length < 15 && word.getCol() >= 0 && word.getCol() < 15)
                return true;
            else
                return false;
        } else {
            if (word.getCol() >= 0 && word.getCol() + word.getTiles().length < 15 && word.getRow() >= 0 && word.getRow() < 15)
                return true;
            else
                return false;
        }
    }

    public boolean isBoardEmpty() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    public boolean isStar(Word word) {
        if (isBoardEmpty()) {
            if (word.isVertical()) {
                int startletterindex = word.getRow();
                int lastletterindex = word.getRow() + word.getTiles().length;
                if (word.getCol() == 7 && startletterindex <= 7 && lastletterindex >= 7)
                    return true;
                else
                    return false;
            } else//not vertical
            {
                int startletterindex = word.getCol();
                int lastindexindex = word.getCol() + word.getTiles().length;
                if (word.getRow() == 7 && startletterindex <= 7 && lastindexindex >= 7)
                    return true;
                else
                    return false;
            }

        } else {
            if (board[7][7] == null)
                return false;
        }
        return true;
    }

    public boolean boardLegal(Word word) {
        if (isInMatrix(word) && !isCongruent(word, board) && isStar(word))
            return true;
        else
            return false;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public void addedVertical(Word word, ArrayList<Word> addedWords, Word fullWord) {
        int m=0;
        for (int i = word.getRow(); i < word.getRow() + word.getTiles().length; i++) {
            if (word.getTiles()[m] == null) {
                m++;
                continue;
            }
            if (word.getCol() == 0)// The left column
            {
                if (board[i][1] == null)
                {
                    m++;
                    continue;
                }
                else {
                    int j = word.getCol();//0
                    Tile tile = board[i][j];
                    int size = 0;
                    while (tile != null) {
                        size++;
                        j++;
                        tile = board[i][j];
                    }
                    Tile[] addedWordTiles = new Tile[size];
                    j = word.getCol();//0
                    for (int k = 0; k < size; k++) {
                        addedWordTiles[k] = board[i][j];
                        j++;
                    }
                    Word addedWord = new Word(addedWordTiles, i, 0, false);
                    if(!isWordExisting(addedWord))
                    {
                        addedWords.add(addedWord);
                    }
                }

            } else if (word.getCol() == 14)//the right column
            {
                if (board[i][13] == null)
                {
                    m++;
                    continue;
                }
                else {
                    int j = word.getCol();//14
                    Tile tile = board[i][j];
                    int size = 0;
                    while (tile != null) {
                        size++;
                        j--;
                        tile = board[i][j];
                    }
                    Tile[] addedWordTiles = new Tile[size];
                    j = word.getCol();//0
                    for (int k = size - 1; k >= 0; k--) {
                        addedWordTiles[k] = board[i][j];
                        j--;
                    }
                    Word addedWord = new Word(addedWordTiles, i, j, false);
                    if(!isWordExisting(addedWord))
                    {
                        addedWords.add(addedWord);
                    }
                }
            }//any another column
            else {

                int j = word.getCol();
                int left = j - 1;
                int right = j + 1;
                if (board[i][left] == null && board[i][right] == null)
                {
                    m++;
                    continue;
                }
                while (board[i][left] != null || board[i][right] != null) {
                    if (board[i][left] != null && left != 0)
                        left--;
                    if (board[i][right] != null && right != 14)
                        right++;
                }
                int size = right - left - 1;
                left++;
                int startCol = left;
                Tile[] addedWordTiles = new Tile[size];
                for (int k = 0; k < size; k++)
                {
                    addedWordTiles[k] = board[i][left];
                    left++;
                }
                Word addedWord = new Word(addedWordTiles, i, startCol, false);
                if(!isWordExisting(addedWord))
                {
                    addedWords.add(addedWord);
                }

            }
            m++;
        }
    }

    public void addedNotVertical(Word word, ArrayList<Word> addedWords,Word fullWord)
    {
        int m=0;
        for (int j = word.getCol(); j < word.getCol() + word.getTiles().length; j++)
        {
            if (word.getTiles()[m] == null) {
                m++;
                continue;
            }
            if (word.getRow() == 0)// The first row
            {
                if (board[1][j] == null)
                {
                    m++;
                    continue;
                }

                else {
                    int i = word.getRow();//0
                    Tile tile = board[i][j];
                    int size = 0;
                    while (tile != null) {
                        size++;
                        i++;
                        tile = board[i][j];
                    }
                    Tile[] addedWordTiles = new Tile[size];
                    i = word.getRow();//0
                    for (int k = 0; k < size; k++) {
                        addedWordTiles[k] = board[i][j];
                        i++;
                    }
                    Word addedWord = new Word(addedWordTiles, 0, j, true);
                    if(!isWordExisting(addedWord))
                    {
                        addedWords.add(addedWord);
                    }
                }

            } else if (word.getRow() == 14)//the last row
            {
                if (board[13][j] == null)
                {
                    m++;
                    continue;
                }
                else {
                    int i = word.getRow();//14
                    Tile tile = board[i][j];
                    int size = 0;
                    while (tile != null) {
                        size++;
                        i--;
                        tile = board[i][j];
                    }
                    Tile[] addedWordTiles = new Tile[size];
                    i = word.getRow();//14
                    for (int k = size - 1; k >= 0; k++)
                    {
                        if(board[i][j] != null)
                        {
                            addedWordTiles[k] = board[i][j];
                        }
                        else
                        {
                            addedWordTiles[size-1-k]=fullWord.getTiles()[j-fullWord.getCol()];
                        }
                        i--;
                    }
                    Word addedWord = new Word(addedWordTiles, 14, j, true);
                    if(!isWordExisting(addedWord))
                    {
                        addedWords.add(addedWord);
                    }
                }
            }//any other row
            else {
                int i = word.getRow();
                int up = i - 1;
                int down = i + 1;
                if (board[up][j] == null && board[down][j] == null)
                {
                    m++;
                    continue;

                }
                while (board[up][j] != null || board[down][j] != null) {
                    if (board[up][j] != null && up != 0)
                        up--;
                    if (board[down][j] != null && down != 14)
                        down++;
                }
                int size = down - up - 1;
                up++;
                int startRow = up;
                Tile[] addedWordTiles = new Tile[size];
                for (int k = 0; k < size; k++)
                {
                    if(board[up][j]!=null)
                    {
                        addedWordTiles[k] = board[up][j];
                    }
                    else
                    {

                        addedWordTiles[k]=fullWord.getTiles()[j-fullWord.getCol()];
                    }
                    up++;
                }

                Word addedWord = new Word(addedWordTiles, startRow, j, true);
                if(!isWordExisting(addedWord))
                {
                    addedWords.add(addedWord);
                }

            }
            m++;
        }
    }


    public Word makeFullWord(Word word)
    {
     /** Function to complete the whole word**/
        Word newWord;
        Tile[] wordParamaterToadd=new Tile[word.getTiles().length];
        if(word.isVertical())
        {
            int firstindex=word.getRow();
            int lastindex=word.getRow()+word.getTiles().length;
            int k=firstindex;
            for(int i=0; i<word.getTiles().length;i++)
            {
                if(word.getTiles()[i] == null)
                {
                    wordParamaterToadd[i]=board[k][word.getCol()];
                    k++;
                }
                else
                {
                    wordParamaterToadd[i]=word.getTiles()[i];
                    k++;
                }
            }
            newWord=new Word(wordParamaterToadd,word.getRow(),word.getCol(),true);
            return newWord;
        }
        else //not-vertical
        {
            int firstindex = word.getCol();
            int lastindex = word.getCol() + word.getTiles().length;
            int k = firstindex;
            for (int i = 0; i < word.getTiles().length; i++) {
                if (word.getTiles()[i] == null) {
                    wordParamaterToadd[i] = board[word.getRow()][k];
                    k++;
                } else {
                    wordParamaterToadd[i] = word.getTiles()[i];
                    k++;
                }
            }
            newWord = new Word(wordParamaterToadd, word.getRow(), word.getCol(), true);
            return newWord;
        }
    }
    public ArrayList<Word> getWords(Word word) {

        Word newWord;
        ArrayList<Word> addedWords = new ArrayList<>();
        //Word newWord=makeFullWord(word);
       // addedWords.add(newWord);

        Tile[] wordParamaterToadd=new Tile[word.getTiles().length];
        if(word.isVertical())
        {
            int firstindex=word.getRow();
            int lastindex=word.getRow()+word.getTiles().length;
            int k=firstindex;
            for(int i=0; i<word.getTiles().length;i++)
            {
                if(word.getTiles()[i] == null)
                {
                    wordParamaterToadd[i]=board[k][word.getCol()];
                    k++;
                }
                else
                {
                    wordParamaterToadd[i]=word.getTiles()[i];
                    k++;
                }
            }
           newWord=new Word(wordParamaterToadd,word.getRow(),word.getCol(),true);
            addedWords.add(newWord);
        }
        else //not-vertical
        {
            int firstindex=word.getCol();
            int lastindex=word.getCol()+word.getTiles().length;
            int k=firstindex;
            for(int i=0; i<word.getTiles().length;i++)
            {
                if(word.getTiles()[i]==null)
                {
                    wordParamaterToadd[i]=board[word.getRow()][k];
                    k++;
                }
                else
                {
                    wordParamaterToadd[i]=word.getTiles()[i];
                    k++;
                }
            }
           newWord=new  Word(wordParamaterToadd,word.getRow(),word.getCol(),false);
            addedWords.add(newWord);

        }

        if (word.isVertical())
            addedVertical(word, addedWords,newWord);
        else
            addedNotVertical(word, addedWords,newWord);


        return addedWords;
    }

    public void SetBoardColor() {
        boardColor[7][7] = 'c';

        boardColor[0][0] = boardColor[0][7] = boardColor[0][14] = boardColor[7][0] =
                boardColor[7][14] = boardColor[14][0] = boardColor[14][7] = boardColor[14][14] = 'r';

        boardColor[1][1] = boardColor[2][2] = boardColor[3][3] = boardColor[4][4] =
                boardColor[13][1] = boardColor[12][2] = boardColor[11][3] = boardColor[10][4] =
                        boardColor[4][10] = boardColor[3][11] = boardColor[2][12] = boardColor[1][13] =
                                boardColor[10][10] = boardColor[11][11] = boardColor[12][12] = boardColor[13][13] = 'y';

        boardColor[0][3] = boardColor[0][11] = boardColor[2][6] = boardColor[2][8] = boardColor[3][0] = boardColor[3][7] = boardColor[3][14]
                = boardColor[6][2] = boardColor[6][6] = boardColor[6][8] = boardColor[6][12] = boardColor[7][3] = boardColor[7][11] =
                boardColor[8][2] = boardColor[8][6] = boardColor[8][8] = boardColor[8][12] = boardColor[11][0] = boardColor[11][7] = boardColor[11][14]
                        = boardColor[12][6] = boardColor[12][8] = boardColor[14][3] = boardColor[14][11] = 'w';

        boardColor[1][5] = boardColor[1][9] = boardColor[5][1] = boardColor[5][5] = boardColor[5][9] = boardColor[5][13] =
                boardColor[9][1] = boardColor[9][5] = boardColor[9][9] = boardColor[9][13] = boardColor[13][5] = boardColor[13][9] = 'b';
    }

    public int wordScore(Word word) {
        int score = 0;
        if (word.isVertical()) {
            int m = 0;
            for (int i = word.getRow(); i < word.getRow() + word.getTiles().length; i++) {
                if (word.getTiles()[m] == null) {
                    score += board[i][word.getCol()].score;
                    m++;
                } else {
                    score += word.getTiles()[m].score;
                    m++;
                }
            }
        } else //not-vertical
        {
            int m = 0;
            for (int j = word.getCol(); j < word.getCol() + word.getTiles().length; j++) {
                if (word.getTiles()[m] == null) {
                    score += board[word.getRow()][j].score;
                    m++;
                } else {
                    score += word.getTiles()[m].score;
                    m++;
                }
            }
        }

        return score;
    }

    public int getScore(Word word) {

        SetBoardColor();
        int score = wordScore(word);
        int TW = 1;
        int DW = 1;
        int CW = 1;
        if (word.isVertical()) {

            for (int i = word.getRow(); i < word.getRow() + word.getTiles().length; i++) {
                if (boardColor[i][word.getCol()] == 'w') {
                    score +=  board[i][word.getCol()].score;
                } else if (boardColor[i][word.getCol()] == 'b') {
                    score += board[i][word.getCol()].score + board[i][word.getCol()].score;
                }

            }
            for (int i = word.getRow(); i < word.getRow() + word.getTiles().length; i++) {
                if (boardColor[i][word.getCol()] == 'r') {
                    TW = 3;
                } else if (boardColor[i][word.getCol()] == 'y') {
                    DW = 2;
                }
                if (boardColor[i][word.getCol()] == 'c' && (word.getTiles().length == b.numOfTiles())) {
                    CW = 2;
                }
            }


        } else //not-vertical
        {
            for (int j = word.getCol(); j < word.getCol() + word.getTiles().length; j++) {

                if (boardColor[word.getRow()][j] == 'w') {
                    score +=  board[word.getRow()][j].score;
                } else if (boardColor[word.getRow()][j] == 'b') {
                    score += board[word.getRow()][j].score + board[word.getRow()][j].score;
                }
            }
            for (int j = word.getCol(); j < word.getCol() + word.getTiles().length; j++) {
                if (boardColor[word.getRow()][j] == 'r') {
                    TW = 3;
                } else if (boardColor[word.getRow()][j] == 'y') {
                    DW = 2;
                }
                if (boardColor[word.getRow()][j] == 'c' && (word.getTiles().length == b.numOfTiles())) {
                    CW = 2;
                }
            }
        }

        return score * TW * DW * CW;

    }

    public boolean isWordExisting(Word word)
    {
        int i=0;

        for (Word word1 : wordsOnBoard)
        {
            if (word1.getTiles().length == word.getTiles().length)
            {
                for (i = 0; i < word.getTiles().length; i++)
                {
                    if (word.getTiles()[i] == word1.getTiles()[i])
                        continue;
                    else
                        break;
                }
            }
            if (i == word.getTiles().length)
                return true;
        }
        return false;
    }

    public int tryPlaceWord(Word word)
    {
        if (boardLegal(word) && dictionaryLegal(word))
        {
            Word newWord1 = makeFullWord(word);
            wordsOnBoard.add(newWord1);
            int addedScore=0;
            ArrayList<Word> addedWords=getWords(word);
            for(Word newWord : addedWords)
            {

                if(dictionaryLegal(newWord))
                {
                    for(int i=0; i<word.getTiles().length;i++)
                    {
                        if(word.isVertical())
                        {
                            if(word.getTiles()[i]!=null)
                            {
                                board[word.getRow() + i][word.getCol()] = word.getTiles()[i];
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            if(word.getTiles()[i] != null)
                            {
                                board[word.getRow()][word.getCol()+i]=word.getTiles()[i];
                            }
                            else
                            {
                                continue;
                            }
                        }

                    }

                }
                else //At least one word isn't legal
                {
                    return 0;
                }
                addedScore+=getScore(newWord);
            }
            return addedScore;
        }
        return 0;
    }

}