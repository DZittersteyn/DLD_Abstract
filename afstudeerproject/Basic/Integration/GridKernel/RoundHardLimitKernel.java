package afstudeerproject.Basic.Integration.GridKernel;

import afstudeerproject.Basic.Integration.Integrator.Step;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public class RoundHardLimitKernel extends Kernel {

    public double lambda = Math.PI / 4; // size in radians of the kernel

    @Override
    public Step direction(ArrayList<WeighedVector> g, Vector mu) {
        Step s = new Step();
        ArrayList<Vector> weighedVectors = new ArrayList<Vector>();
        for (WeighedVector v : g) {
            Vector vprime;
            if (Vector.dot(mu, v.vector) < 0) {
                vprime = Vector.mul(v.vector, -1 * v.weight);
            } else {
                vprime = Vector.mul(v.vector, 1 * v.weight);
            }

            double angle = mu.angle(vprime);

            if (angle <= lambda) {
                weighedVectors.add(vprime);
                //s.considered.add(new WeighedVector(v.vector, 1 * v.weight));
            }




        }
        for (Vector v : weighedVectors) {
            s.point = Vector.add(s.point, v);
        }
        return s;

    }
}
