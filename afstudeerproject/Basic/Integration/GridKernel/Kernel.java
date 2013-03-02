package afstudeerproject.Basic.Integration.GridKernel;

import afstudeerproject.Basic.Gridpoint;
import afstudeerproject.Basic.Integration.Integrator.Step;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public abstract class Kernel {
    
    public abstract Step direction(ArrayList<WeighedVector> g, Vector mu);
}
