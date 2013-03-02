package afstudeerproject.Basic.Integration.Interpolation;

import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.OutOfRangeOfGrid;
import afstudeerproject.Models.Grid;
import java.util.ArrayList;

public abstract class Interpolator {
    
    public abstract ArrayList<WeighedVector> interpolatedVectors(Vector v, Grid g) throws OutOfRangeOfGrid;
    
}
