package afstudeerproject.Models;

import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.UsableWeighedSegment;
import java.util.ArrayList;
import java.util.HashSet;

public class SearchGridNode {
    HashSet<Segment> segments = new HashSet<Segment> ();
    
    public ArrayList<Segment> instance(){
        ArrayList<Segment> ret = new ArrayList<Segment>();
        for (Segment segment : segments) {
            ret.add(segment);
        }
        return ret;
    }
}
