package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class GUI_Mode extends JFrame {

    private MazePanel mazePanel;
    private final char[][] mazeData;
    private final int[] start_position;
    private final int[] end_position;
    private final int row;
    private final int col;
    private Movement movement;

    /**
     * Constructor
     * @param mazeData The maze data.
     * @param start_position The start position of the maze.
     * @param end_position The end position of the maze.
     * @param row The row of the maze.
     * @param col The column of the maze.
     * @param movement The movement of the player.
     */
    public GUI_Mode(char[][] mazeData,int[] start_position,int[] end_position,int row,int col,Movement movement) {

        this.movement = movement;
        this.row = row;
        this.col = col;
        this.start_position = start_position;
        this.end_position = end_position;
        this.mazeData = mazeData;
        this.setTitle("GUI_Mode");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Choose Maze");
        JMenuItem item1 = new JMenuItem("Maze 1");
        JMenuItem item2 = new JMenuItem("Maze 2");
        JMenuItem item3 = new JMenuItem("Maze 3");

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);

        menuBar.add(menu);

        // add action listener
        item1.addActionListener(new MazeActionListener(1));
        item2.addActionListener(new MazeActionListener(2));
        item3.addActionListener(new MazeActionListener(3));

        //put the maze panel in the SOUTH of the frame
        add(menuBar, BorderLayout.SOUTH);

        //set the size of the frame
        setSize(600, 600);
        setVisible(true);

        //load the maze and display it in the maze panel
        mazePanel = new MazePanel(mazeData, this.movement);
        add(mazePanel); // add the maze panel to the frame

        // catch the key event
        addKeyListener(new KeyAdapter() {
            private Movement movement;

            public void keyPressed(KeyEvent e) {
                char direction;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> direction = 'w';
                    case KeyEvent.VK_A -> direction = 'a';
                    case KeyEvent.VK_S -> direction = 's';
                    case KeyEvent.VK_D -> direction = 'd';
                    default -> {
                        return;
                    }
                }
                // Update and repaint the maze
                mazePanel.updatePlayerPosition(direction);
                mazePanel.repaint();
            }
        });

    }

    //load the maze and display it in the maze panel
    private void loadAndDisplayMaze(int mazeNumber) throws FileNotFoundException,
            MazeSizeMissmatchException, MazeMalformedException {
        MazeInfo mazeInfo = loadMazeData(mazeNumber);
        //get the new start position and the new maze size
        char[][] newMazeData = mazeInfo.getMazeData();
        int new_row = mazeInfo.getRow();
        int new_col = mazeInfo.getCol();
        int[] new_start_position = mazeInfo.getStartPosition();
        movement = new Movement(newMazeData, new_row,new_col, new_start_position);
        // Remove old mazePanel if exists
        if(mazePanel != null) {
            remove(mazePanel);
        }
        // Load the maze and display it in the panel
        mazePanel = new MazePanel(newMazeData,this.movement);
        add(mazePanel);
        //repaint the panel
        revalidate();
        repaint();
    }

    //the listener for the menu items
    private class MazeActionListener implements ActionListener {
        private final int mazeNumber;

        public MazeActionListener(int mazeNumber) {
            this.mazeNumber = mazeNumber;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Menu item clicked, maze number: " + mazeNumber);
            try {
                loadAndDisplayMaze(mazeNumber);
            } catch (FileNotFoundException | MazeSizeMissmatchException | MazeMalformedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class MazePanel extends JPanel {
        private char[][] maze;
        private final Movement movement;

        public MazePanel(char[][] mazeData, Movement movement) {
            this.movement = movement;
            this.maze = mazeData;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (mazeData == null) return; //if maze is empty, do nothing

            int rowHeight = this.getHeight() / this.maze.length;
            int colWidth = this.getWidth() / this.maze[0].length;

            for (int row = 0; row < this.maze.length; row++) {
                for (int col = 0; col < this.maze[row].length; col++) {
                    switch (this.maze[row][col]) {
                        case '#' -> {
                            g.setColor(Color.BLACK);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                        case '.' -> {
                            g.setColor(Color.WHITE);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                        case 'P' -> {
                            g.setColor(Color.BLUE);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                        case 'S' -> {
                            g.setColor(Color.GREEN);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                        case 'E' -> {
                            g.setColor(Color.RED);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                        case 'T' -> {
                            g.setColor(Color.YELLOW);
                            g.fillRect(col * colWidth, row * rowHeight, colWidth, rowHeight);
                        }
                    }
                }
            }
        }

        /**
         * Update the player position
         *
         * @param direction the direction of the player
         */
        public void updatePlayerPosition(char direction) {
            String move_step;
            switch (direction) {
                case 'w' -> move_step = "W";
                case 'a' -> move_step = "A";
                case 's' -> move_step= "S";
                case 'd' -> move_step= "D";
                case ' ' -> {
                    return;
                }
                default -> throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            movement.movement_function(move_step);
            this.maze = movement.getMaze();
        }


        /**
         * Get the maze data
         *
         * @return the maze data
         */
        public char[][] getMazeData() {
            return this.maze;
        }
    }

    /**
     * Load the maze data from the file and put it into a MazeInfo object
     *
     * @param mazeNumber the number of the maze
     * @return the maze data
     * @throws FileNotFoundException
     * @throws MazeSizeMissmatchException
     * @throws MazeMalformedException
     */
    private MazeInfo loadMazeData(int mazeNumber)
            throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        String fileAddress = "D:\\study\\java_restudy\\maze00" + mazeNumber + ".txt";
        FileLoader fileLoader = new FileLoader();
        char[][] mazeData = fileLoader.load(fileAddress);
        int[] startPosition = fileLoader.getStartPosition();
        int row = fileLoader.getRow();
        int col = fileLoader.getCol();
        return new MazeInfo(mazeData, startPosition, row, col);
    }

    /**
     * The main method
     *
     * @param args the command line arguments
     * @throws FileNotFoundException
     * @throws MazeSizeMissmatchException
     * @throws MazeMalformedException
     */
    public static void main(String[] args) throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        FileLoader fileLoader = new FileLoader();
        char[][] mazeData = new char[0][];
        mazeData = fileLoader.load("D:\\study\\java_restudy\\maze001.txt");
        int[] start_position = fileLoader.getStartPosition();
        int[] end_position = fileLoader.getEndPosition();
        int col = fileLoader.getCol();
        int row = fileLoader.getRow();
        final char[][] finalMazeData = mazeData;
        Movement movement = new Movement(mazeData,row,col,start_position);
        new GUI_Mode(finalMazeData, start_position, end_position,col,row,movement);
    }
}
