package io;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Load the maze from the file
 */
public class FileLoader implements FileInterface {
    private int row;
    private int col;
    private char[][] maze;
    private final int[] start_position;
    private final int[] end_position;

    /**
     * Constructor
     */
    public FileLoader() {
        this.row = 0;
        this.col = 0;
        this.maze = new char[0][0];
        this.start_position = new int[2];
        this.end_position = new int[2];
    }

    /**
     * @param filename The path to the maze file to be loaded.
     * @return A 2D character array representing the loaded maze.
     * @throws MazeMalformedException     If the maze data is not correctly formatted.
     * @throws MazeSizeMissmatchException If the maze dimensions do not match the provided size.
     * @throws IllegalArgumentException   For other validation errors.
     * @throws FileNotFoundException      If the maze file is not found.
     */
    @Override
    public char[][] load(String filename) throws MazeMalformedException, MazeSizeMissmatchException,
            IllegalArgumentException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            //read the first line and get the width and height of the maze
            String size = reader.readLine();
            if (size == null) {
                throw new MazeMalformedException("Maze file is empty or has no size number.");
            }

            String[] maze_size = size.split(" ");
            if (maze_size.length != 2) {
                throw new MazeMalformedException("Invalid format.");
            }

            // get the width and height of the maze
            this.row = Integer.parseInt(maze_size[0]);
            this.col = Integer.parseInt(maze_size[1]);

            if (row % 2 == 0 || col % 2 == 0) {
                throw new MazeMalformedException("Wrong format.");
            }

            // create a 2D array to store the maze
            this.maze = new char[row][col];
            String line;
            int number = 0;

            // read the maze line by line and store it in the 2D array
            while ((line = reader.readLine()) != null) {
                if (line.length() != col) {
                    throw new MazeSizeMissmatchException("Maze dimensions do not match the col size of the maze.");
                }
                for(char symbol :line.toCharArray()){
                    if(symbol == '#'){
                        continue;
                    }
                    if(symbol == ' '){
                        continue;
                    }
                    if(symbol == 'S'){
                        continue;
                    }
                    if(symbol == 'E'){
                        continue;
                    }
                    if (symbol == '.') {
                        continue;
                    }
                    else {
                        throw new IllegalArgumentException("Invalid characters found in the maze.");
                    }
                }
                this.maze[number] = line.toCharArray();
                number++;
            }
            if (number != row) {
                throw new MazeSizeMissmatchException("Maze dimensions do not match the row size.");
            }
            return this.maze;
        } catch (FileNotFoundException e) {
            //Triggered when the file is not found.
             System.out.println("Maze file " + filename + " not found");
             throw e;
        } catch (IOException e) {
            //Triggered when there is an error reading the file.
            throw new MazeMalformedException("Error reading the maze file: " + filename);
        } catch (NumberFormatException e) {
            //Triggered when the maze dimensions are not integers.
            throw new MazeMalformedException("Invalid maze dimensions.");
        } catch (IllegalArgumentException e) {
            //Triggered when the maze have invalid characters.
            throw new MazeMalformedException("Invalid characters in the maze file.");
        }
    }

    /**
     * @return The row of the maze.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return The col of the maze.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * @return The maze.
     */
    public char[][] getMaze() {
        return this.maze;
    }

    /**
     * @return The start position of the maze.
     */
    public int[] getStartPosition() {
       for (int i = 0; i < this.row; i++) {
           for (int j = 0; j < this.col; j++) {
               if (this.maze[i][j] == 'S') {
                   this.start_position[0] = i;
                   this.start_position[1] = j;
               }
           }
       }
         return this.start_position;
    }

    /**
     * @return The end position of the maze.
     */
    public int[] getEndPosition() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.maze[i][j] == 'E') {
                    this.end_position[0] = i;
                    this.end_position[1] = j;
                }
            }
        }
        return this.end_position;
    }

}



