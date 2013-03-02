/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Basic;

import afstudeerproject.Basic.Integration.Integrator.IntegrationResult;
import afstudeerproject.Basic.Integration.Integrator.Integrator;
import afstudeerproject.Basic.Integration.Integrator.Step;
import afstudeerproject.Settings.IntegrationSettings;
import afstudeerproject.Settings.ModelSettings;
import afstudeerproject.Settings.OutputSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author dirk
 */
public class Curve {

    public Integrator[] integrators;
    double D1, D2, D3;

    public void calcDisplayAmp() {
        int e1 = integrators[0].points.size() - 1;
        int p1 = integrators[0].points.size() * 2 / 3;
        int p2 = integrators[0].points.size() * 1 / 3; //switched, 'cause they both start at the center!
        int p3 = integrators[1].points.size() * 1 / 3;
        int p4 = integrators[1].points.size() * 2 / 3;
        int e2 = integrators[1].points.size() - 1;

        D1 = 0;
        for (int i = p1; i <= e1; i++) {
            D1 += integrators[0].points.get(i).realAmp;
        }
        D1 /= (e1 + 1) - p1;

        D2 = 0;
        for (int i = 0; i <= p2; i++) {
            D2 += integrators[0].points.get(i).realAmp;
        }
        for (int i = 0; i <= p3; i++) {
            D2 += integrators[1].points.get(i).realAmp;
        }
        D2 /= p2 + p3 + 2;

        D3 = 0;
        for (int i = p4; i <= e2; i++) {
            D3 += integrators[1].points.get(i).realAmp;
        }
        D3 /= (e2 + 1) - p4;
        for (int i = 0; i < integrators[0].points.size() + integrators[1].points.size(); i++) {
            calcDisplayAmp(i);
        }
    }

    public void calcDisplayAmp(int index) {
        if (index < integrators[0].points.size()) {
            index = integrators[0].points.size() - (index + 1);
            if (index < integrators[0].points.size() * 1 / 3) {
                integrators[0].points.get(index).displayAmp = D2;
            } else if (index < integrators[0].points.size() * 2 / 3) {

                double weight = index - integrators[0].points.size() / 3.0;
                weight /= integrators[0].points.size() * 1 / 3.0;
                integrators[0].points.get(index).displayAmp = (D2 * (1 - weight) + D1 * (weight));
            } else {
                integrators[0].points.get(index).displayAmp = D1;
            }

        } else {
            index = index - integrators[0].points.size();
            if (index < integrators[1].points.size() * 1 / 3) {
                integrators[1].points.get(index).displayAmp = D2;
            } else if (index < integrators[1].points.size() * 2 / 3) {
                double weight = index - integrators[1].points.size() / 3.0;
                weight /= integrators[1].points.size() * 1 / 3.0;
                integrators[1].points.get(index).displayAmp = (D2 * (1 - weight) + D3 * (weight));
            } else {
                integrators[1].points.get(index).displayAmp = D3;
            }
        }



    }

    public Curve(Integrator a, Integrator b) {
        integrators = new Integrator[2];
        integrators[0] = a;
        integrators[1] = b;
    }

    public double weight() {
        return integrators[0].weight() + integrators[1].weight();
    }

    public void compensate() {

        if (OutputSettings.fixstart) {
            double startWeight = (this.integrators[0].points.get(1).realAmp + this.integrators[1].points.get(1).realAmp) / 2;

            this.integrators[0].points.get(0).realAmp = startWeight;
            this.integrators[1].points.get(0).realAmp = startWeight;
        }

        double avgcomp = 2 * IntegrationSettings.locationphi1 + IntegrationSettings.locationphi2;
        avgcomp /= 2 * IntegrationSettings.locationphi1 + 2 * IntegrationSettings.directionphi2;

        HashMap<Segment, MutableDouble> compensated = new HashMap<Segment, MutableDouble>();

        for (Integrator integrator : integrators) {
            for (IntegrationResult step : integrator.points) {

                for (UsableWeighedSegment stepcomponent : step.considered) {
                    MutableDouble newWeight = compensated.get(stepcomponent.segment);
                    if (newWeight == null) {
                        newWeight = new MutableDouble(stepcomponent.segment.weight);
                        compensated.put(stepcomponent.segment, newWeight);
                    }

                    double segmentlength = Vector.sub(
                            new Vector(stepcomponent.segment.coords[0]),
                            new Vector(stepcomponent.segment.coords[1])).length();


                    double fac = stepcomponent.used / ((segmentlength + 2 * IntegrationSettings.locationphi2) / IntegrationSettings.stepsize) / avgcomp;
                    newWeight.d *= 1 - fac;

                    stepcomponent.used = 0;
                }
                step.considered = null;
            }
        }

        for (Segment s : compensated.keySet()) {
            if (compensated.get(s).d < -1e-7) {
                System.out.println(compensated.get(s).d);
            }
            s.weight = compensated.get(s).d;
        }
    }
}

class MutableDouble {

    public Double d;

    public MutableDouble(Double d) {
        this.d = d;
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof MutableDouble) {
            return ((MutableDouble) t).d == this.d;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.d != null ? this.d.hashCode() : 0);
        return hash;
    }
}
