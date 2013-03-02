/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Basic.Integration.Integrator;

import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

/**
 *
 * @author dirk
 */
public class Step {

    public ArrayList<UsableWeighedSegment> considered;

    public Step() {
        this.point = new Vector(new double[]{0, 0, 0});
        considered = new ArrayList<UsableWeighedSegment>();

    }

    public Step(Vector point) {
        this.point = point;
        this.considered = new ArrayList<UsableWeighedSegment>();
    }

    public Step(Vector point, ArrayList<UsableWeighedSegment> considered) {
        this.point = point;
        this.considered = considered;
    }
    public Vector point;
}
