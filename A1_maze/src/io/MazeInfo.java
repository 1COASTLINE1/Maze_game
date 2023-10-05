package io;

/**
 * This class is used to store the information of the maze.(which is used for GUI mode)
 */
public class MazeInfo {
    private final char[][] mazeData;
    private final int[] startPosition;
    private final int row;
    private final int col;

    /**
     * Constructor
     * @param mazeData The maze data.
     * @param startPosition The start position of the maze.
     * @param row The row of the maze.
     * @param col The column of the maze.
     */
    public MazeInfo(char[][] mazeData, int[] startPosition, int row, int col) {
        this.mazeData = mazeData;
        this.startPosition = startPosition;
        this.row = row;
        this.col = col;
    }

    /**
     * @return The maze data.
     */
    public char[][] getMazeData() {
        return mazeData;
    }

    /**
     * @return The start position of the maze.
     */
    public int[] getStartPosition() {
        return startPosition;
    }

    /**
     * @return The row of the maze.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The column of the maze.
     */
    public int getCol() {
        return col;
    }
}

