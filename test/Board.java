package test;

import java.util.ArrayList;

public class Board {
    private static Board boardSingelton = null;
    public Tile[][] board;
    boolean isEmpty;

    private Board() {
        this.board = new Tile[15][15];
        isEmpty = true;
    }

    public static Board getBoard() {
        if (boardSingelton == null) {
            boardSingelton = new Board();
        }
        return boardSingelton;
    }

    final byte dl = 2; // double letter
    final byte tl = 3; // triple letter
    final byte dw = 20; // double word
    final byte tw = 30; // triple word

    private byte[][] bonus = {
            { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw },
            { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
            { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
            { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
            { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
            { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
            { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
            { tw, 0, 0, dl, 0, 0, 0, dw, 0, 0, 0, dl, 0, 0, tw },
            { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
            { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
            { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
            { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
            { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
            { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
            { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw }
    };

    private boolean inBoard(int row, int col) {
        return (row >= 0 && row < board.length && col >= 0 && col < board[0].length);
    }

    public Tile[][] getTiles() {
        return board.clone();
    }

    private boolean onStar(Word w) {
        int i = w.getRow();
        int j = w.getCol();
        for (int k = 0; k < w.getTiles().length; k++) {
            if (i == 7 && j == 7) {
                return true;
            }
            if (w.getVertical()) {
                i++;
            } else
                j++;
        }
        return false;
    }

    private boolean crossTile(Word w) {
        if (w.vertical) {
            if (w.getRow() + w.tiles.length < board.length) {
                return true;
            }
        } else {
            if (w.getCol() + w.tiles.length < board.length) {
                return true;
            }
        }
        return false;
    }

    public boolean boardLegal(Word w) {
        if (!inBoard(w.row, w.col)) {
            return false;
        }
        if (isEmpty && !onStar(w)) {
            return false;
        }
        if (!crossTile(w)) {
            return false;
        }
        return true;
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    private ArrayList<Word> getAllWords(Tile[][] board) {
        ArrayList<Word> words = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            int j = 0;
            while (j < board[i].length) {
                ArrayList<Tile> tl = new ArrayList<>();
                int row = i, col = j;
                while (j < board[i].length && board[i][j] != null) {
                    tl.add(board[i][j]);
                    j++;
                }
                if (tl.size() > 1) {
                    Tile[] tempTiles = tl.toArray(new Tile[tl.size()]);
                    Word checkTiles = new Word(tempTiles, row, col, false);
                    if (dictionaryLegal(checkTiles)) {
                        words.add(checkTiles);
                    }
                }
                j++;
            }
        }
        for (int j = 0; j < board.length; j++) {
            int i = 0;
            while (i < board[j].length) {
                ArrayList<Tile> tl = new ArrayList<>();
                int row = i, col = j;
                while (i < board.length && board[i][j] != null) {
                    tl.add(board[i][j]);
                    i++;
                }
                if (tl.size() > 1) {
                    Tile[] tempTiles = tl.toArray(new Tile[tl.size()]);
                    Word checkTiles = new Word(tempTiles, row, col, true);
                    if (dictionaryLegal(checkTiles)) {
                        words.add(checkTiles);
                    }
                }
                i++;
            }
        }
        return words;
    }

    public ArrayList<Word> getWords(Word w) {
        Tile[][] board = getTiles();
        ArrayList<Word> before = getAllWords(board);
        int row = w.getRow(), col = w.getCol();
        for (Tile t : w.getTiles()) {
            board[row][col] = t;
            if (w.getVertical())
                row++;
            else
                col++;
        }
        ArrayList<Word> after = getAllWords(board);
        after.removeAll(before);
        return after;
    }

    public int getScore(Word w) {
        int row = w.getRow(), col = w.getCol();
        int sum = 0;
        int doubleWord = 0, tripleWord = 0;
        for (Tile t : w.getTiles()) {
            sum += t.score;
            if (bonus[row][col] == dl) {
                sum += t.score;
            }
            if (bonus[row][col] == tl) {
                sum += t.score * 2;
            }
            if (bonus[row][col] == dw) {
                doubleWord++;
            }
            if (bonus[row][col] == tw) {
                tripleWord++;
            }
            if (w.getVertical())
                row++;
            else
                col++;
        }
        if (doubleWord > 0) {
            sum *= (2 * doubleWord);
        }
        if (tripleWord > 0) {
            sum *= (3 * tripleWord);
        }
        return sum;
    }

    public int tryPlaceWord(Word w) {
        Tile[] tl = w.getTiles();
        int row = w.getRow(), col = w.getCol();
        for (int i = 0; i < tl.length; i++) {
            if (tl[i] == null) {
                tl[i] = board[row][col];
            }
            if (w.getVertical())
                row++;
            else
                col++;
        }
        int score = 0;
        if (boardLegal(w)) {
            ArrayList<Word> newWords = getWords(w);
            for (Word tw : newWords) {
                score += getScore(tw);
            }
        }
        row = w.getRow();
        col = w.getCol();
        for (Tile t : w.getTiles()) {
            board[row][col] = t;
            if (w.getVertical())
                row++;
            else
                col++;
        }
        if (isEmpty) {
            isEmpty = false;
            bonus[7][7] = 0;
        }
        return score;
    }
}
