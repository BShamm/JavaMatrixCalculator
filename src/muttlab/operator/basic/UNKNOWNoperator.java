/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.basic;

import muttlab.MuttLab;
import muttlab.operator.Command;

/**
 *
 * @author bens
 */
public class UNKNOWNoperator extends Command {

    static { NAME = "unknown"; }

    public UNKNOWNoperator(String firstWord, String secondWord) {
        super(firstWord, secondWord);
    }

    /**
     * We got bad input. 
     *
     * @param stack the MuttLab tool
     * @return false
     */
    @Override
    public boolean execute(MuttLab stack) {
        stack.error("I don't know what you mean...");
        return false;
    }
}

