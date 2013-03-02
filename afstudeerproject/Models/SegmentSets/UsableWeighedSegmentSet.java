/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afstudeerproject.Models.SegmentSets;

import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public class UsableWeighedSegmentSet {

    public ArrayList<UsableWeighedSegment> segments = new ArrayList<UsableWeighedSegment>();

    public double weight() {
        double weight = 0;
        for (UsableWeighedSegment s : segments) {
            weight += s.segment.weight;
        }
        return weight;
    }

    public UsableWeighedSegmentSet() {
    }

    public UsableWeighedSegmentSet(ArrayList<UsableWeighedSegment> uws) {
        for (UsableWeighedSegment usableWeighedSegment : uws) {
            segments.add(usableWeighedSegment);
        }
    }

    public Vector avgDirection(Vector domain) {
        if (domain == null) {
            domain = new Vector(0, 0, 1); // random init, doesn't matter which domain.
        }

        Vector direction = new Vector(0, 0, 0);
        for (UsableWeighedSegment s : segments) {
            direction = Vector.add(direction, Vector.mul(Vector.sub(s.segment.coords[1], s.segment.coords[0]).sameDomain(domain), s.segment.weight));
        }
        return direction;
    }
}
