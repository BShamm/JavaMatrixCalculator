package muttlab.io;

import java.util.Collection;

/**
 * A really simple class to handle output
 *
 * @author bens
 */
public class OutConsole implements OutputManager {

    /** Create an output mechanism for the console */
    public OutConsole() { }

    @Override
    public void prompt() { System.out.print("> "); }

    @Override
    public void write(String str) { System.out.println(str); }

    @Override
    public void write(Collection<String> messages) {
        messages.forEach((msg) -> { 
            System.out.println(msg);
        });
    }

    @Override
    public void write(String[] messages) {
        for (String msg : messages) 
            System.out.println(msg);
    }

    @Override
    public void error(String str) { System.err.println(str); }

    @Override
    public void error(String[] messages) {
        for (String msg : messages) 
            System.err.println(msg);
    }
    
    @Override
    public void fatal(String str) { System.err.println(str); }
}
