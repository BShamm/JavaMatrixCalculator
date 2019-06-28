package muttlab.io;

import java.util.Collection;

/**
 * A really simple class to handle output
 *
 * @author bens
 */
public interface OutputManager {

    /**
     * Print a prompt message
     */
    public void prompt();

    /**
     * Print a message
     * @param str the message
     */
    public void write(String str);

    /**
     * Print a list of messages
     * @param messages the messages
     */
    public void write(Collection<String> messages);

    /**
     * Print a list of messages
     * @param messages the messages
     */
    public void write(String[] messages);

    /**
     * Print an error message
     * @param str the message
     */
    public void error(String str);

    /**
     * Print a list of error messages
     * @param messages the messages
     */
    public void error(String[] messages);

    /**
     * A fatal error
     * @param message the error
     */
    public void fatal(String message);
}
