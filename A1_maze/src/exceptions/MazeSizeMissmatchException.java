package exceptions;

public class MazeSizeMissmatchException extends Throwable{
    /**
     * Constructs a MazeSizeMissmatchException with no detailed message.
     */
    public MazeSizeMissmatchException() {
        super();
    }

    /**
     * Constructs a MazeSizeMissmatchException with  specified detail message.
     *
     * @param s The detailed message for alarming.
     */
    public MazeSizeMissmatchException(String s) {
        super(s);
    }
}
