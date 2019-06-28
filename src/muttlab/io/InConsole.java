package muttlab.io;

import java.io.InputStream;

/**
 * A really simple class to handle input from the console
 * @author bens
 */
public class InConsole implements InputManager {
    private final InputStream in;
    
    /** Create an input mechanism */
    public InConsole () { in = System.in; }

    @Override
    public InputStream getInputStream() { return in; }
}
