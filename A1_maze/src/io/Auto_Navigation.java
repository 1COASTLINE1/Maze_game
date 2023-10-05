package io;

import io.FileLoader;
import java.util.LinkedList;
import io.Movement;

public class Auto_Navigation {

    private final char[][] maze;
    private final int row;
    private final int col;
    private int[] start_position = new int[2];
    private int[] end_position = new int[2];

    /**
     * Constructor
     * @param fileLoader The file loader which has been loaded.
     *                   It contains the maze, the row, the column, the start position and the end position.
     */
    public Auto_Navigation(FileLoader fileLoader,char[][] maze) {
        this.maze = maze;
        this.row = fileLoader.getRow();
        this.col = fileLoader.getCol();
        this.start_position = fileLoader.getStartPosition();
        this.end_position = fileLoader.getEndPosition();
    }

    /**
     * use BFS to auto navigate the maze
     *
     * @return A container which contains the predecessors of each position.
     */
    public int[][][]  auto_navigation_function() {
        LinkedList<int[]> list = new LinkedList<>();
        boolean[][] has_visited = new boolean[row][col];
        int[][][] predecessors = new int[row][col][2];

        // add the start position to the list
        list.add(start_position);
        // mark the start position as visited
        has_visited[start_position[0]][start_position[1]] = true;
        // initialize the predecessors and set them to be -1
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                predecessors[i][j][0] = -1;
                predecessors[i][j][1] = -1;
            }
        }

        // use BFS to find the path
        while (!list.isEmpty()) {
            int[] player_position = list.poll();
            // if the player has reached the end position, then break
            if (player_position[0] == end_position[0] && player_position[1] == end_position[1]) {
                break;
            }
            //if the player hasn't got to the end point then check the four directions
            Movement movement = new Movement(maze, row, col, player_position);
            //use the movement class to check the four directions
            for (int[] direction : movement.getDirections()) {
                // if the direction is valid and hasn't been visited
                if (!has_visited[direction[0]][direction[1]]&&movement.isTraversable(direction[0], direction[1])) {
                    // add the direction to the list
                    list.add(direction);
                    // mark the direction as visited
                    has_visited[direction[0]][direction[1]] = true;
                    // set the predecessor of the direction to be the player position
                    predecessors[direction[0]][direction[1]][0] = player_position[0];
                    predecessors[direction[0]][direction[1]][1] = player_position[1];
                }
            }
        }
        return predecessors;
    }

    public char[][] Solve_maze(int[][][] predecessor) {
        StringBuilder sb = new StringBuilder();
        if(predecessor[end_position[0]][end_position[1]][0] == -1){
            sb.append("The maze has no solution.");
            System.out.println(sb.toString());
            return null;
        } // check if the end position is reachable

        // copy the maze
        char[][] maze_copy = new char[maze.length][];
        for(int i = 0; i < maze.length; i++)
            maze_copy[i] = maze[i].clone();

        // use the predecessor to find the path
        int[] current_position = end_position;
        while(!(current_position[0] == start_position[0] && current_position[1] == start_position[1])) {
            maze_copy[current_position[0]][current_position[1]] = 'A'; // mark the path
            int previous_row = predecessor[current_position[0]][current_position[1]][0];
            int previous_col = predecessor[current_position[0]][current_position[1]][1];

            if(previous_row == -1 & previous_col == -1) {
                break;
            }
            current_position = new int[] {previous_row, previous_col}; // move to the previous position

        }
        maze_copy[start_position[0]][start_position[1]] = 'A'; // mark the start position
        return maze_copy;
    }






}
