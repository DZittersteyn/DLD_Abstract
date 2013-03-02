package afstudeerproject.Basic.Integration.Integrator;

import afstudeerproject.Basic.Integration.SegmentKernel.DirectionalKernel;
import afstudeerproject.Basic.Integration.SegmentKernel.LocationalKernel;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Settings.IntegrationSettings;
import java.util.ArrayList;

public abstract class Integrator {

    public LocationalKernel locational;
    public DirectionalKernel directional;
    
    public ArrayList<IntegrationResult> points = new ArrayList<IntegrationResult>();
    public Vector startdirection = null;
    public boolean alive = true;
    public String status = "Running";
    public boolean hasBeenCompensated = false;

    public Integrator(Vector start, Vector startdirection, LocationalKernel locational, DirectionalKernel directional) {
        points.add(new IntegrationResult
                (start));
        this.locational = locational;
        this.directional = directional;
        this.startdirection = startdirection;
    }

    public abstract void step();

    public Integrator mirror() {
        this.startdirection = Vector.mul(startdirection, -1);
        return this;
    }

    public double weight(){
        double weight = 0;
        for (IntegrationResult point : points) {
            double surf = Math.PI * point.realAmp * point.realAmp;
            weight += surf * IntegrationSettings.stepsize;
        }
        return weight;
    }
}
