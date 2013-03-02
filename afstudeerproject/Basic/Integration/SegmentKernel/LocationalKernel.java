package afstudeerproject.Basic.Integration.SegmentKernel;

import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.Herpederp;
import afstudeerproject.Models.Dataset;
import afstudeerproject.Models.SegmentSets.UsableWeighedSegmentSet;
import afstudeerproject.Settings.IntegrationSettings;
import java.util.ArrayList;

public class LocationalKernel {

    double phi1; //radius of sphere within which a segment is taken fully
    double phi2; //radius of sphere outside which a segment is not taken at all

    public LocationalKernel(double phi1, double phi2) {
        if (phi1 > phi2) {
            throw new Herpederp("phi1 should be within phi2");
        }
        this.phi1 = phi1;
        this.phi2 = phi2;
    }

    public UsableWeighedSegmentSet apply(Dataset d, Vector center) {
        ArrayList<UsableWeighedSegment> inRange = d.segmentsInRange(phi2, center);
        UsableWeighedSegmentSet u = new UsableWeighedSegmentSet(inRange);
        apply(u, center);
        return u;
    }

    public void apply(UsableWeighedSegmentSet u, Vector center) {
        int i = 0;
        while (i < u.segments.size()) {
            Vector avgpoint = Vector.mul(Vector.add(u.segments.get(i).segment.coords[0], u.segments.get(i).segment.coords[1]), 0.5);
            double avgdist = Math.abs(Vector.sub(center, avgpoint).length());
            if (avgdist > phi1) {
                if (avgdist < phi2) {
                    // phi1 - phi1 < dist - phi1 < phi2 - phi1
                    avgdist -= phi1;
                    // 0 < dist < phi2 - phi1
                    avgdist /= phi2 - phi1;
                    // 0 < dist < 1
                    avgdist = 1 - avgdist;
                    u.segments.get(i).used *= avgdist;
                    i++;
                } else {
                    u.segments.remove(i);
                }
            } else {
                i++;
            }
        }
    }
}
