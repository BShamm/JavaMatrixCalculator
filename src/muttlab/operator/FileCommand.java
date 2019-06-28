/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.operator;

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
import muttlab.operator.basic.MULTIPLYoperator;
import muttlab.operator.file.CreateSaveMultfile;

/**
 *
 * @author bens
 */
public abstract class FileCommand {
    public static String NAME;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     */
    public FileCommand()
    {
    }

    /**
     * Execute this command
     * @param vector
     * @param toggle
     * @return false unless this is the quit command
     */
    public abstract Matrix execute(Deque<Matrix> vector, String toggle);

    public abstract void CreateSavefile(File vectors, float n);
    
}
