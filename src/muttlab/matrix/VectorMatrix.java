/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muttlab.matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 *
 * @author bens
 */
public class VectorMatrix extends Matrix {
    
    private final float[][] impl;
    private double sum;
    private double min;
    private double max;
    
    public VectorMatrix(int width) {
        this.impl = new float[1][width];
    }
    
    public VectorMatrix(String rep) {
        this.impl = parseMatrix(rep);
        this.setSum();
        this.setMax();
        this.setMin();
    }
    
    private float[][] parseMatrix(String rep) {
        String[] elem = rep.split(",");
        
        float[][] mat = new float[1][elem.length];
        for(int c = 0; c < elem.length; c++){
            String[] elements = elem[c].trim().split(" ");
            mat[0][c] = Float.parseFloat(elements[0]);
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
    
    public float[] getRow(int row) { return impl[row]; }
    
    @Override
    public float get(int r, int c) { return impl[r][c]; }
    
    @Override
    public void set(int r, int c, float v) { impl[r][c] = v; }
    
    @Override
    public double getSum() {
        return sum;
    }

    private void setSum() {
        float[] vector = this.getRow(0);
        double[] vectorArray = IntStream.range(0, vector.length).mapToDouble(i -> vector[i]).toArray();
        
        this.sum = DoubleStream.of(vectorArray).sum();
    }

    @Override
    public double getMin() {
        return min;
    }

    private void setMin() {
        float[] vector = this.getRow(0);
        double[] vectorArray = IntStream.range(0, vector.length).mapToDouble(i -> vector[i]).toArray();
        
        Arrays.stream(vectorArray)
                .boxed()
                .sorted()
                .limit(1)
                .forEach(d -> this.min = d);
    }

    @Override
    public double getMax() {
        return max;
    }

    private void setMax() {
        float[] vector = this.getRow(0);
        double[] vectorArray = IntStream.range(0, vector.length).mapToDouble(i -> vector[i]).toArray();
        
        Arrays.stream(vectorArray)
                .boxed()
                .sorted(Collections.reverseOrder())
                .limit(1)
                .forEach(d -> this.max = d);
    }

    @Override
    public void padLeft(int x) {
        int numOfZeros = x - this.getWidth();
        
        if(numOfZeros != 0){
            float[] vector = this.getRow(0);
            float[] newVect = new float[x];
            
            System.arraycopy(vector, 0, newVect, numOfZeros, this.getWidth());
            
            this.impl[0] = newVect;
        }
    }

    @Override
    public void padRight(int x) {
        int numOfZeros = x - this.getWidth();
        
        if(numOfZeros != 0){
            float[] vector = this.getRow(0);
            float[] newVect = new float[x];
            newVect = Arrays.copyOf(vector, x);
            this.impl[0] = newVect;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int c = 0; c < getWidth(); c++) {
            str.append(get(0, c));
            if (c < getWidth() - 1) {
                str.append(", ");
            }
        }
        return str.toString();
    }

    @Override
    public boolean isVector() {
        return true;
    }
    
}
