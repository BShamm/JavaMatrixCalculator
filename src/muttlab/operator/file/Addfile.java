/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator.file;

import java.io.File;
import static java.util.Comparator.comparing;
import java.util.Deque;
import java.util.List;
import static java.util.stream.Collectors.toList;
import muttlab.matrix.Matrix;
import muttlab.operator.FileCommand;
import muttlab.operator.basic.ADDoperator;

/**
 *
 * @author bens
 */
public class Addfile extends FileCommand {
    
    static {NAME = "Add";}
    
    private final ADDoperator add = new ADDoperator("","");
            
    @Override
    public Matrix execute(Deque<Matrix> vector, String toggle) {
        switch (toggle) {
            case "firstMat":
            {
                int x = vector.getLast().getWidth();
                
                Matrix result = vector.stream()
                        .filter((Matrix v) -> v.isVector())
                        .filter((Matrix v) -> v.getWidth() == x)
                        .reduce((Matrix v, Matrix v1) -> add.addFile(v, v1))
                        .get();
                
                return result;
            }
            case "padLeft":
            {
                Matrix max = vector.stream()
                        .filter((Matrix v) -> v.isVector())
                        .sorted(comparing((Matrix v) -> v.getSum()).reversed())
                        .findFirst()
                        .get();
                        
                
                int x = max.getWidth();
                
                vector.stream()
                    .filter((Matrix v) -> v.isVector())
                    .forEach((Matrix m) -> {
                        if(m.getMax() != x){
                            m.padLeft(x);
                        }
                    });
                
                
                Matrix result = vector.stream()
                        .filter((Matrix v) -> v.isVector())
                        .filter((Matrix v) -> v.getWidth() == x)
                        .reduce((Matrix v, Matrix v1) -> add.addFile(v, v1))
                        .get();
                
                return result;
                
            }
            default:
            {
                Matrix max = vector.stream()
                        .filter((Matrix v) -> v.isVector())
                        .sorted(comparing((Matrix v) -> v.getSum()).reversed())
                        .findFirst()
                        .get();
                        
                
                int x = max.getWidth();
                
                vector.stream()
                    .filter((Matrix v) -> v.isVector())
                    .forEach((Matrix m) -> {
                        if(m.getMax() != x){
                            m.padRight(x);
                        }
                    });
                
                
                Matrix result = vector.stream()
                        .filter((Matrix v) -> v.isVector())
                        .filter((Matrix v) -> v.getWidth() == x)
                        .reduce((Matrix v, Matrix v1) -> add.addFile(v, v1))
                        .get();
                
                return result;
            }
        }
    }

    @Override
    public void CreateSavefile(File vectors, float n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
