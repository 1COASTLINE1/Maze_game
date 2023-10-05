import io.Text_Mode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class Text_Mode_Test {

    private Text_Mode text_mode_original;

    @Before
    public void setUp() {
        text_mode_original = new Text_Mode();
    }

    @Test
    public void test_getMaze() {
        char[][] maze ={
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', 'S', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', '#', '#', ' ', '#'},
                {'#', ' ', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', '#', 'E', '#'},
                {'#', '#', '#', '#', '#', '#', '#'}
        };
        String maze1 ="⬛⬛⬛⬛⬛⬛⬛/n" +
                     "⬛🚩⬛⬜⬜⬜⬛/n" +
                        "⬛⬜⬛⬛⬛⬜⬛/n" +
                        "⬛⬜⬛⬜⬜⬜⬛/n" +
                        "⬛⬜⬛⬜⬛⬜⬛/n" +
                        "⬛⬜⬜⬜⬛🏁⬛/n" +
                        "⬛⬛⬛⬛⬛⬛⬛";
        String maze2 = text_mode_original.text_mode_function(maze,7);
    }




}
