/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.file;

import java.io.File;
import java.util.Deque;
import muttlab.matrix.Matrix;
import muttlab.operator.FileCommand;

/**
 *
 * @author bens
 */
public class Unknownfile extends FileCommand {

    static { NAME = "unknown"; }

    public Unknownfile() {
    }

    /**
     * We got bad input. 
     *
     * @param toggle
     * @return null
     */
    @Override
    public Matrix execute(Deque<Matrix> vector, String toggle) {
        return null;
    }

    @Override
    public void CreateSavefile(File vectors, float n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
