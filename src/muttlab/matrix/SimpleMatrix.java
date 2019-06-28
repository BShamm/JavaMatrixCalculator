package muttlab.matrix;

import java.util.Arrays;

/**
 * A simple SimpleMatrix class
 * @author bens
 */
public class SimpleMatrix extends Matrix {
    
    private final float [][] impl; 
    
    /**
     * Create a matrix
     * @param height of the matrix
     * @param width  of the matrix
     */
    public SimpleMatrix(int height, int width) {
        impl = new float[height][width];
    }
    
    /**
     * Create a matrix
     * @param rep flattened representation of the matrix
     */
    public SimpleMatrix(String rep) {
        impl = parseMatrix(rep);
    }
    
    /**
     * Deep copy of a Matrix
     * @param old the matrix to copy
     */
    public SimpleMatrix(Matrix old) {
        int height = old.getHeight();
        impl = new float[height][];
        for (int r = 0; r < height; r++)
            impl[r] = Arrays.copyOf(((SimpleMatrix)old).getRow(r), old.getWidth());  
    }
    
    /** 
     * Get a row of a matrix
     * @param row
     * @return the row
     */
    private float[] getRow(int row) { return impl[row]; }
    
    /**
     * Parse a flattened representation of a matrix
     * @param rep the flattened representation
     * @return 
     */
    private float[][] parseMatrix(String rep) {        
        // Remove any trailing ]
        int close = rep.indexOf(']');
        if (close != -1) rep = rep.substring(0, close);
        String[] rows = rep.split(";");
        
        float[][] mat = new float[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            String[] elements = rows[r].trim().split(" ");
            mat[r] = new float[elements.length];
            for(int c = 0; c < elements.length; c++){ 
                mat[r][c] = Float.parseFloat(elements[c].trim());
            }
        }
        return mat;
    }
    
    @Override
    public int getHeight() { return impl.length; }
    
    @Override
    public int getWidth() { 
        return (impl.length == 0 || impl[0] == null)
            ? 0
            : impl[0].length; 
    }
    
    @Override
    public float get(int r, int c) { return impl[r][c]; }
    
    @Override
    public void set(int r, int c, float v) { impl[r][c] = v; }

    @Override
    public double getSum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void padLeft(int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void padRight(int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
