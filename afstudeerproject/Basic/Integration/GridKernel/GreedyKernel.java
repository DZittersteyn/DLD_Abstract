package afstudeerproject.Basic.Integration.GridKernel;

import afstudeerproject.Basic.Gridpoint;
import afstudeerproject.Basic.Integration.Integrator.Step;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.Herpederp;
import java.util.ArrayList;

public class GreedyKernel extends Kernel {

    @Override
    public Step direction(ArrayList<WeighedVector> g, Vector mu) {
        if (mu != null) {
            throw new Herpederp("Greedykernel does not accept a mu");
        }

        SquareHardLimitKernel hl = new SquareHardLimitKernel();
        hl.lambda = Math.PI / 4;
        Step max = new Step();
        Vector[] directions = new Vector[]{
            new Vector(new double[]{0, 0, 1}),
            new Vector(new double[]{0, 0, 1}),
            new Vector(new double[]{0, 1, 0}),
            new Vector(new double[]{0, 1, 0}),
            new Vector(new double[]{1, 0, 0}),
            new Vector(new double[]{1, 0, 0}),
        };

        for (Vector v : directions) {
            try {
                Step dir = hl.direction(g, v);
                if (dir.point.length() > max.point.length()) {
                    max = dir;
                }
            } catch (ArithmeticException e) {
                
            }
        }

        return max;
    }
}
