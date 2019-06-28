package muttlab.matrix;

/**
 * Abstract class to represent all implementations of matrices
 * @author bens
 */
public abstract class Matrix {    

    /** @return a flattened representation of the matrix */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ ");
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                str.append(get(r, c));
                if (c < getWidth() - 1) {
                    str.append(' ');
                }
            }
            if (r < getHeight() - 1) {
                str.append("; ");
            }
        }
        str.append(" ]");
        return str.toString();
    }
    
    /** @return height of the matrix */
    public abstract int getHeight();
    
    /** @return width of the matrix */
    public abstract int getWidth();
    
    /**
     * Get an element of the matrix
     * @param row the row
     * @param col the column
     * @return the value at (r,c)
     */
    public abstract float get(int row, int col);
    
    /**
     * Set an element of the matrix
     * @param r the row
     * @param c the column
     * @param v the value
     */
    public abstract void set(int r, int c, float v);
    
    
    public abstract double getSum();
    public abstract double getMin();
    public abstract double getMax();
    
    
    public abstract void padLeft(int x);
    public abstract void padRight(int x);
    
    public abstract boolean isVector();
    
    
}
