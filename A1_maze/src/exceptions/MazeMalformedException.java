package exceptions;

public class MazeMalformedException extends Throwable{
    /**
     * Constructs a basic MazeMalformedException with no detailed message.
     */
    public MazeMalformedException() {
        super();
    }

    /**
     * Constructs a MazeMalformedException with  specified detail message.
     *
     * @param s The detailed message for alarming.
     */
    public MazeMalformedException(String s) {
        super(s);
    }
}
