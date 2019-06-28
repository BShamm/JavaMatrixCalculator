/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.basic;

import java.util.Deque;
import muttlab.MuttLab;
import muttlab.matrix.Matrix;
import muttlab.operator.Command;

/**
 *
 * @author bens
 */
public class ADDoperator extends Command {
    
    static { NAME = "+"; }

    public ADDoperator(String firstWord, String secondWord) {
        super(firstWord, secondWord);
    }

    /**
     * "+" was entered. current and old must be compatible, i.e. the same shape
     *
     * @param vector1
     * @param vector2
     * @return 
     */
    
    public Matrix addFile(Matrix vector1, Matrix vector2){

        Matrix current = vector1;
        Matrix old = vector2;

        for (int r = 0; r < current.getHeight(); r++) {
            for (int c = 0; c < current.getWidth(); c++) {
                current.set(r, c, old.get(r, c) + current.get(r, c));
            }
        }
        return current;
    }
    
    @Override
    public boolean execute(MuttLab stack) {

        if (stack.size() < 2) {
            stack.error("Need two matrices to add");
            return false;
        }

        Matrix current = stack.pop();
        Matrix old = stack.pop();
        if (current.getHeight() != old.getHeight()
                || current.getWidth() != old.getWidth()) {
            stack.error("Matrices must be the same shape to add");
            // replace the matrices
            stack.push(old);
            stack.push(current);
            return false;
        }

        for (int r = 0; r < current.getHeight(); r++) {
            for (int c = 0; c < current.getWidth(); c++) {
                current.set(r, c, old.get(r, c) + current.get(r, c));
            }
        }
        stack.push(current);
        return false;
    }
}

