package io;
/**
 * Change the mode into text mode
 */
public class Text_Mode {

    /**
     * Constructor
     */
    public Text_Mode() {

    }

    /**
     * @param maze The maze to be changed into text mode.
     * @return A 2D character array in string representing the loaded maze.
     */
    public String text_mode_function(char[][] maze,int col) throws IllegalArgumentException {
            int count = 1;
            StringBuilder maze_string = new StringBuilder();
            for(char[] row : maze) {
                for (char cell : row) {
                    if (cell == '#') {
                        maze_string.append("⬛");
                    }
                    if(cell == ' ') {
                        maze_string.append("⬜");
                    }
                    if(cell == '.') {
                        maze_string.append("⬜");
                    }
                    if(cell == 'S') {
                        maze_string.append("🚩");
                    }
                    if(cell == 'E') {
                        maze_string.append("🏁");
                    }
                    if(cell == 'P') {
                        maze_string.append("👨");
                    }
                    //For auto_navigation
                    if (cell == 'A') {
                        maze_string.append("🟦");
                    }
                    //For trace the player's path
                    if (cell == 'T') {
                        maze_string.append("\uD83D\uDFE8");
                    }
                    if(count % (col) == 0){
                        maze_string.append("\n");
                    }
                    count++;

                }
            }
            return maze_string.toString();

    }
}
