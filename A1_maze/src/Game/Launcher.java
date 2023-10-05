package Game;

import io.*;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import javax.swing.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
/**
 * The launcher of the game.For choosing the mode of the game and launch the game.
 */
public class Launcher {

    public void launch() {
        FileLoader gameLoader = new FileLoader();
        Text_Mode text_mode = new Text_Mode();
        System.out.println("Please input the file address of the maze you want to play:");
        Scanner extra_scanner = new Scanner(System.in);
        String file_address;
        file_address = extra_scanner.nextLine();

        Scanner scanner2 = new Scanner(System.in);
        String user_input_mode;
        try {
            while (true) {
                System.out.println("Please input 1 or 2 to choose the mode: 1 for text mode, 2 for GUI mode");
                user_input_mode = scanner2.nextLine();
                //text mode
                if (user_input_mode.equals("1")) {
                    try {
                        runTextMode(file_address, gameLoader, text_mode);
                    } catch (Exception e) {
                        System.out.println("There is something wrong with the maze.");
                    }
                }
                //GUI mode
                else if (user_input_mode.equals("2")) {
                    FileLoader fileLoader = new FileLoader();
                    char[][] mazeData = new char[0][0];
                    mazeData = fileLoader.load(file_address);
                    int[] start_position = fileLoader.getStartPosition();
                    int[] end_position = fileLoader.getEndPosition();
                    int col = fileLoader.getCol();
                    int row = fileLoader.getRow();
                    final char[][] finalMazeData = mazeData;
                    Movement movement = new Movement(mazeData, row, col, start_position);
                    new GUI_Mode(finalMazeData, start_position, end_position, col,
                            row, movement);
                    break;
                } else {
                    System.out.println("Invalid input, please enter 1 or 2.");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } catch (MazeSizeMissmatchException | MazeMalformedException e) {
            throw new RuntimeException(e);
        }
    }



    public void runTextMode(String fileAddress, FileLoader gameLoader, Text_Mode textMode) throws Exception {
        try {
            //load the maze from the file
            char[][] maze = gameLoader.load(fileAddress);
            //get the initial maze and the trace back maze
            String container = textMode.text_mode_function(maze, gameLoader.getCol());
            //INITIALIZE THE MOVEMENT
            Movement movement = new Movement(maze, gameLoader.getRow(), gameLoader.getCol(), gameLoader.getStartPosition());
            System.out.println("Initial Maze:\nRed flag represents the start position.Player is at the start position" +
                    "\nGray flag represents the end position." +
                    "\nChild represents the player.");
            System.out.println(container);
            //Use Auto_Navigation to auto navigate the maze
            Auto_Navigation auto_navigation = new Auto_Navigation(gameLoader, maze);
            int[][][] auto_navigated_maze = auto_navigation.auto_navigation_function();
            char[][] auto_navigated_maze_char = auto_navigation.Solve_maze(auto_navigated_maze);
            movement.set_maze_solved(auto_navigated_maze_char);
            //Provide the user with the option to auto navigate the maze or play by themselves
            System.out.println("Choose to auto navigate the maze or not: 1 for yes, 2 for play by yourself.");
            Scanner scanner3 = new Scanner(System.in);
            String user_choose_navigate_mode = scanner3.nextLine();
            //Use while loop to make sure the user input is valid
            while(true){
                if(user_choose_navigate_mode.equals("1")){
                    System.out.println("Auto navigated maze:");
                    String navigation = textMode.text_mode_function(auto_navigated_maze_char, gameLoader.getCol());
                    System.out.println(navigation);
                    System.exit(0);
                    break;
                }
                else if(user_choose_navigate_mode.equals("2")){
                    System.out.println("You choose to play by yourself.");
                    while (true) {
                        Scanner scanner4 = new Scanner(System.in);
                        String user_input;
                        System.out.println("Please enter your movement: w for up, a for left, s for down, d for right." +
                                "Enter exit to exit the game.");
                        user_input = scanner4.nextLine();
                        if (user_input.equalsIgnoreCase("exit")) {
                            System.exit(0);
                            break;
                        }
                        if (user_input.equals("w") || user_input.equals("a") || user_input.equals("s")
                                || user_input.equals("d") || user_input.equals("W") || user_input.equals("A")
                                || user_input.equals("S") || user_input.equals("D")) {
                            movement.movement_function(user_input);
                            //get the updated maze
                            container = textMode.text_mode_function(movement.getMaze(), gameLoader.getCol());
                            //Print the updated maze
                            System.out.println("Updated Maze:");
                            System.out.println(container);
                            //Print the current position of the player
                            System.out.println("Current position of the player: " +
                                    Arrays.toString(movement.getPlayerPosition()));
                        } else {
                            System.out.println("Invalid input, please enter again.");
                            break;
                        }

                    }
                }
                else{
                    System.out.println("Invalid input, please enter 1 or 2.");
                    break;
                }
            }
        } catch (IllegalArgumentException | MazeMalformedException | MazeSizeMissmatchException | FileNotFoundException e) {
            //
            System.err.println("Error loading maze: " + e.getMessage());
        }
    }



}
