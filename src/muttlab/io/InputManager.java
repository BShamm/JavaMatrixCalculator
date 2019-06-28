package muttlab.io;

import java.io.InputStream;

/**
 * A really simple class to handle input
 *
 * @author bens
 */
public interface InputManager {

    /**
     * Return the input stream for this input
     *
     * @return the stream
     */
    public InputStream getInputStream();

}
