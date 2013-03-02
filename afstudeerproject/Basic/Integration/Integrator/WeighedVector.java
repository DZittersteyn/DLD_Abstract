/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Basic.Integration.Integrator;

import afstudeerproject.Basic.Vector;

/**
 *
 * @author dirk
 */
public class WeighedVector {

    public WeighedVector(Vector vector, double weight) {
        this.vector = vector;
        this.weight = weight;
    }
    public Vector vector;
    public double weight;
    
    @Override
    public String toString(){
        return vector.toString() + " With weight " + weight;
    }
}
