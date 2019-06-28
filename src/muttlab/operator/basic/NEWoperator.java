
package muttlab.operator.basic;

import muttlab.MuttLab;
import muttlab.matrix.SimpleMatrix;
import muttlab.operator.Command;

/**
 *
 * @author bens
 */
public class NEWoperator extends Command {

    static { NAME = "["; }

    public NEWoperator(String firstWord, String secondWord) {
        super(firstWord, secondWord);
    }

    /**
     * "[" was entered. Create a new matrix
     *
     * @param stack the MuttLab tool
     * @return false
     */
    @Override
    public boolean execute(MuttLab stack) {
        if (!hasSecondWord()) {
            // if there is no second word, we don't know what the matrix is...
            stack.error("what's the matrix?");
            stack.printHelp();
            return false;
        }
        stack.push(new SimpleMatrix(getSecondWord()));
        return false;
    }
}

