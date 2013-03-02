package afstudeerproject.Basic.Integration.Integrator;

import afstudeerproject.Afstudeerproject;
import afstudeerproject.Basic.Integration.SegmentKernel.DirectionalKernel;
import afstudeerproject.Basic.Integration.SegmentKernel.LocationalKernel;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Models.SegmentSets.UsableWeighedSegmentSet;
import afstudeerproject.Settings.IntegrationSettings;

public class EulerIntegrator extends Integrator {

    public Double startlen = null;
    public int steps = 0;
    public EulerIntegrator(Vector start, Vector direction, LocationalKernel locational, DirectionalKernel directional) {
        super(start, direction, locational, directional);
        System.out.println("new integrator @ " + start);
    }

    @Override
    public void step() {
        steps ++;
        if (!this.alive) {
            return;
        }


        UsableWeighedSegmentSet result = locational.apply(Afstudeerproject.dataset, this.points.get(this.points.size() - 1).point);

        Vector curdir;
        if (this.points.size() == 1) {
            curdir = startdirection;
        } else {
            curdir = Vector.sub(this.points.get(this.points.size() - 1).point, this.points.get(this.points.size() - 2).point);
        }
        directional.apply(result, curdir);
        
        if(startlen == null){
            startlen = result.weight();
        }
        
        if (result.weight() <= 0.3 * startlen || steps > IntegrationSettings.maxsteps) {
            this.alive = false;
            return;
        }

        Vector stepdir = result.avgDirection(curdir);
        stepdir = stepdir.normalized();

        stepdir = Vector.mul(stepdir, IntegrationSettings.stepsize);

        IntegrationResult i = new IntegrationResult(result.weight(), Vector.add(this.points.get(this.points.size() - 1).point, stepdir), result.segments);

        this.points.add(i);


    }
}
