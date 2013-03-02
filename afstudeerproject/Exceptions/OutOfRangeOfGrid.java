/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Exceptions;

/**
 *
 * @author dirk
 */
public class OutOfRangeOfGrid extends Exception{
    public OutOfRangeOfGrid(int[] indices){
        super("out of range, tried to access " + indices[0] + ", " + indices[1] + ", " + indices[2]);
    }
}
