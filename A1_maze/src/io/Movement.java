package io;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import io.Auto_Navigation;
import io.FileLoader;
import io.Text_Mode;

public class Movement {
    private final char[][] maze;
    private final int row;
    private final int col;
    private final int[] player_position;
    private final char[][] maze_copy;
    private char[][] maze_solved;
    private final Map<String, Consumer<Void>> changes = new HashMap<>();

    /**
     * Constructor
     * @param maze_loaded The maze which has been loaded.
     * @param row_loaded The row of the maze.
     * @param col_loaded The column of the maze.
     * @param player_start_position The start position of the player.
     */
    public Movement(char[][] maze_loaded, int row_loaded, int col_loaded, int[] player_start_position) {
        this.player_position = player_start_position;
        this.maze = maze_loaded;
        this.row = row_loaded;
        this.col = col_loaded;
        this.maze_solved = new char[row][col];


        //use lambda expression to define the movement
        changes.put("W", unused -> move(-1, 0));
        changes.put("A", unused -> move(0, -1));
        changes.put("S", unused -> move(1, 0));
        changes.put("D", unused -> move(0, 1));

        // make a copy of the maze for tracking the path of player
        this.maze_copy = new char[maze.length][];
        for(int i = 0; i < maze.length; i++) {
            maze_copy[i] = maze[i].clone();
        }

    }

    /**
     * Set the maze to be solved.
     * @param maze The row of the maze.
     */
    public void set_maze_solved(char[][] maze){
        this.maze_solved = maze;
    }

    /**
     * @param movement The movement to be performed.
     */
    public void movement_function(String movement) {
        if (changes.containsKey(movement.toUpperCase())) {
            changes.get(movement.toUpperCase()).accept(null);
        } else {
            System.out.println("Invalid movement.");
        }
    }

    /**
     * @param row_Change The change in row.
     * @param col_Change The change in column.
     */
    private void move(int row_Change, int col_Change) {
        int old_Row = this.player_position[0];
        int old_Col = this.player_position[1];
        int new_Row = this.player_position[0] + row_Change;
        int new_Col = this.player_position[1] + col_Change;

        if (isTraversable(new_Row, new_Col)) {
            if (this.maze[new_Row][new_Col] == 'E') {
                System.out.println("You win!");
                System.out.println("Here is the path you have taken compared to the shortest path:");
                char[][] path = get_Path_Traceback(this.maze_solved);
                Text_Mode text_mode = new Text_Mode();
                System.out.println(text_mode.text_mode_function(path, this.col));
                System.exit(0);
            }
            if (this.maze[old_Row][old_Col] != 'S') {
                this.maze[old_Row][old_Col] = ' ';
            }
            this.maze[new_Row][new_Col] = 'P';
            this.maze_copy[new_Row][new_Col] ='T';
            this.player_position[0] = new_Row;
            this.player_position[1] = new_Col;
        } else {
            System.out.println("You can't move to this position.");
        }
    }

    /**
     * @param row The row to be checked.
     * @param col The column to be checked.
     * @return Whether the position is traversable.
     */
    public boolean isTraversable(int row, int col) {
        if (row < 0 || row >= this.row || col < 0 || col >= this.col) {
            return false;
        }
        return this.maze[row][col] != '#';
    }

    /**
     * @return The maze.
     */
    public char[][] getMaze() {
        return this.maze;
    }

    /**
     * @return The maze copy.
     */
    public char[][] getMazeCopy() {
        return this.maze_copy;
    }

    /**
     * @return The player's position.
     */
    public int[] getPlayerPosition() {
        return this.player_position;
    }

    /**
     * Sequence: up, right, down, left
     * @return The neighbours of the current position.
     */
    public int[][] getDirections(){
        int[][] directions = new int[][]{{-1, 0}, {0, 1} ,{1, 0},{0, -1}};
        for (int i = 0; i < directions.length; i++) {
            directions[i][0] += this.player_position[0];
            directions[i][1] += this.player_position[1];
        }
        return directions;
    }

    /**
     * @param solved_maze The solved maze.
     * @return The path of the player.
     */
    public char[][] get_Path_Traceback(char[][] solved_maze){
        char[][] path;
        path = maze_copy.clone();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col ; j++) {
                if (solved_maze[i][j] == 'A') {
                    path[i][j] = 'A';
                }
            }
        }
        return path;
    }
}
