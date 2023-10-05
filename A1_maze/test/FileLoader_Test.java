import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class FileLoader_Test {
    private FileLoader fileLoader_original;
    private final String testFilePath = "D:\\study\\java_restudy\\maze001.txt";

    @Before
    public void Start() {
        fileLoader_original = new FileLoader();
    }

    @Test
    public void test_loadFile() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
            char[][] maze = {
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', 'S', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', '#', '#', ' ', '#'},
                {'#', ' ', '#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', '#', 'E', '#'},
                {'#', '#', '#', '#', '#', '#', '#'}
        };
        char[][] maze2 = fileLoader_original.load(testFilePath);
        Assert.assertArrayEquals(maze, maze2);
    }

    @Test(expected = MazeMalformedException.class)
    public void test_loadFile_MazeMalformedException() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze with no size number
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false1.txt");

    }

    @Test(expected = MazeMalformedException.class)
    public void test_loadFile_MazeSizeMissmatchException() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze with only one size number
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false2.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void test_loadFile_FileNotFoundException() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze file is not found
        fileLoader_original.load("D:\\study\\java_restudy\\maze004.txt");
    }

    @Test(expected = MazeMalformedException.class)
    public void test_loadFile_MazeMalformedException2() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze with even size number
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false3.txt");
    }

    @Test(expected = MazeSizeMissmatchException.class)
    public void test_loadFile_MazeSizeMissmatchException2() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze with wrong size number
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false4.txt");
    }

    @Test(expected = MazeSizeMissmatchException.class)
    public void test_loadFile_MissmatchException3() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze whose line's length is not equal to the size number
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false5.txt");
    }

    @Test(expected = MazeMalformedException.class)
    public void test_loadFile_MazeMalformedException3() throws FileNotFoundException, MazeSizeMissmatchException, MazeMalformedException {
        //The maze with wrong character
        fileLoader_original.load("D:\\study\\java_restudy\\maze001false6.txt");
    }

















}
