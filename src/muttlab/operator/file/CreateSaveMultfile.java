/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import muttlab.matrix.Matrix;
import muttlab.matrix.VectorMatrix;
import muttlab.operator.FileCommand;
import muttlab.operator.basic.MULTIPLYoperator;

/**
 *
 * @author bens
 */
public class CreateSaveMultfile extends FileCommand{
    static {NAME = "Create * N";}
    
    private final MULTIPLYoperator mult = new MULTIPLYoperator("","");
    
    @Override
    public Matrix execute(Deque<Matrix> vector, String toggle) {
        return null;
    }

    @Override
    public void CreateSavefile(File vectors, float n) {
        File newVectors = new File("matrices.csv");
        
        try {
            InputStream isRead = new FileInputStream(vectors);
            OutputStream isWrite = new FileOutputStream(newVectors);
            
            BufferedWriter bw;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(isRead))) {
                bw = new BufferedWriter(new OutputStreamWriter(isWrite));
                br.lines()
                        .forEach((String x) -> {
                            try {
                                VectorMatrix v = new VectorMatrix(x);
                                Matrix newV = mult.multFile(v, n);
                                bw.write(newV.toString());
                                bw.newLine();
                            } catch (IOException ex) {
                                Logger.getLogger(CreateSaveMultfile.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
            }
            
            bw.close();
            isRead.close();
            isWrite.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateSaveMultfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateSaveMultfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
