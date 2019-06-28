/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.basic;

import muttlab.MuttLab;
import muttlab.matrix.SimpleMatrix;
import muttlab.operator.Command;

/**
 *
 * @author bens
 */
public class DUPoperator extends Command {

    static { NAME = "dup"; }

    public DUPoperator(String firstWord, String secondWord) {
        super(firstWord, secondWord);
    }

    /**
     * "dup" was entered. 
     * @param stack the MuttLab tool
     * @return false
     */
    @Override
    public boolean execute(MuttLab stack) {
        if(stack.isEmpty()) {
            stack.error("Nothing to dup");
        }
        else stack.push(new SimpleMatrix(stack.peek()));
        return false;
    }
}

