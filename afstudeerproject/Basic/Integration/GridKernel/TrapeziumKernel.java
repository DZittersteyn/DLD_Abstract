package afstudeerproject.Basic.Integration.GridKernel;

import afstudeerproject.Basic.Gridpoint;
import afstudeerproject.Basic.Integration.Integrator.Step;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public class TrapeziumKernel extends Kernel {
    
    // size in radians of the part of the kernel that is 1
    public double lambda = Math.PI / 5;
    // size in radians of the taper of the kernel
    public double omega = Math.PI / 4 - lambda;
    
   @Override
    public Step direction(ArrayList<WeighedVector> g, Vector mu) {
       Step s = new Step();
        ArrayList<Vector> weighedVectors = new ArrayList<Vector>();
        for (WeighedVector v : g) {
            Vector vprime = Vector.mul(v.vector.sameDomain(mu),v.weight);
            double angle = mu.angle(vprime);
            double weight;
            if(angle < lambda){ // in the main part of the kernel
                weight = 1;
            }else if(angle < lambda + omega){ // in the taper
                weight = 1 - (angle-lambda)/omega;
            }else{ // outside the kernel
                weight = 0;
            }
            weighedVectors.add(Vector.mul(vprime, weight));
            //s.considered.add(new WeighedVector(v.vector, weight*v.weight));
        }

        for (Vector v : weighedVectors) {
            s.point = Vector.add(s.point, v);
        }
        return s;

    }
}
