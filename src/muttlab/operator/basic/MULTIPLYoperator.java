/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.basic;

import muttlab.MuttLab;
import muttlab.matrix.Matrix;
import muttlab.matrix.SimpleMatrix;
import muttlab.operator.Command;

/**
 *
 * @author bens
 */
public class MULTIPLYoperator extends Command {

    static { NAME = "*"; }

    public MULTIPLYoperator(String firstWord, String secondWord) {
        super(firstWord, secondWord);
    }

    /**
     * "*" was entered. 
     * If no argument, then multiply old * current
     * If there is an argument, it must be a scalar:
     * @param stack the MuttLab tool
     * @return false
     */
    @Override
    public boolean execute(MuttLab stack) {
        return hasSecondWord()
                ? multiplyScalar(stack)
                : multiplyMatrices(stack);
    }
    
    /**
     * "*" was entered. 
     * current and old must be compatible shapes
     * @param stack the MuttLab tool
     * @return false
     */
    public boolean multiplyMatrices(MuttLab stack) {
        if (stack.size() < 2) {
            stack.error("Need two matrices to multiply");
            return false;
        }
        
        Matrix current = stack.pop();
        Matrix old = stack.pop();
        if (old.getWidth() != current.getHeight()) {
            stack.error("Matrices must be of compatible shapes for *");
            // replace the matrices
            stack.push(old);
            stack.push(current);
            return false;
        }
        
        Matrix result = new SimpleMatrix(old.getHeight(),current.getWidth());
        for (int r = 0; r < old.getHeight(); r++) {
            for (int c = 0; c < current.getWidth(); c++) {
                float sum = 0.0f;
                for (int x = 0; x < old.getWidth(); x++) {
                    sum += old.get(r,x) * current.get(x,c);
                }
                result.set(r, c, sum);
            }
        }
        stack.push(result);
        return false;
    }
    
    /**
     * "*" was entered with an argument. 
     * The argument must be a scalar
     * current = current * scalar, elementwise;
     * old = old
     */
    private boolean multiplyScalar(MuttLab stack) {
        if (stack.isEmpty()) {
            stack.error("Need a matrix for * scalar");
            return false;
        }
        Matrix current = stack.pop();
        float scalar = Float.parseFloat(getSecondWord());
        for (int r = 0; r < current.getHeight(); r ++) {
            for (int c = 0; c < current.getWidth(); c++) {
                current.set(r,c, current.get(r,c) * scalar);
            }
        }
        stack.push(current);
        return false;
    }
    
    public Matrix multFile(Matrix vector1, float scalar){

        Matrix current = vector1;

        for (int r = 0; r < current.getHeight(); r ++) {
            for (int c = 0; c < current.getWidth(); c++) {
                current.set(r,c, current.get(r,c) * scalar);
            }
        }
        return current;
    }
}

