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
import muttlab.MuttLab;
import muttlab.matrix.Matrix;
import muttlab.operator.Command;
import muttlab.operator.FileCommand;

/**
 *
 * @author bens
 */
public class Minimalfile extends FileCommand {

    static{ NAME = "Minimal"; }
    
    public Minimalfile() {
    }

    @Override
    public Matrix execute(Deque<Matrix> vector, String toggle) {
        if(toggle.equals("maxRad")){
            List<Matrix> collect = vector.stream()
                    .filter((Matrix v) -> v.isVector())
                    .sorted(comparing((Matrix v) -> v.getMin()).reversed())
                    .limit(1)
                    .collect(toList());
            
            return collect.get(0);
        } else {
            List<Matrix> collect = vector.stream()
                    .filter((Matrix v) -> v.isVector())
                    .sorted(comparing((Matrix v) -> v.getMin()))
                    .limit(1)
                    .collect(toList());
            
            return collect.get(0);
        }
    }

    @Override
    public void CreateSavefile(File vectors, float n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
