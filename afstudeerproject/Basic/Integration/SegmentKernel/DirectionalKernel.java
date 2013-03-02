package afstudeerproject.Basic.Integration.SegmentKernel;

import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Models.SegmentSets.UsableWeighedSegmentSet;

public class DirectionalKernel{

    public double phi1; // angle within which segments are taken fully
    public double phi2; // angle outside which segments aren't taken at all

    public DirectionalKernel(double phi1, double phi2) {
        this.phi1 = phi1;
        this.phi2 = phi2;
    }

    public UsableWeighedSegmentSet applyToCopy(UsableWeighedSegmentSet segments, Vector direction){
        UsableWeighedSegmentSet u = new UsableWeighedSegmentSet();
        
        for (UsableWeighedSegment usableWeighedSegment : segments.segments) {
            u.segments.add(new UsableWeighedSegment(usableWeighedSegment.segment));
        }
        
        apply(u, direction);
        
        return u;
    }
      
    public void apply(UsableWeighedSegmentSet segments, Vector direction) {
        int i = 0;
        while(i < segments.segments.size()){
            Vector v = Vector.sub(segments.segments.get(i).segment.coords[1], segments.segments.get(i).segment.coords[0]);
            Vector vprime = v.sameDomain(direction);
            double angle = direction.angle(vprime);
            
            if (angle > phi1) {
                if (angle < phi2) {
                    
                    double weight = 1 - (angle - phi1) / (phi2 - phi1);
                    segments.segments.get(i).used *= weight;
                    i++;
                } else {
                    segments.segments.remove(i); 
                }
            }else{ 
                i++;
            }
        }
    }
}
