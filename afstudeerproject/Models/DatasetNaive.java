package afstudeerproject.Models;

import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DatasetNaive extends Dataset {

    public DatasetNaive(File f) throws FileNotFoundException {
        super(f);
    }

    
    
    @Override
    public ArrayList<UsableWeighedSegment> segmentsInRange(double range, Vector center) {
        ArrayList<UsableWeighedSegment> ret = new ArrayList<UsableWeighedSegment>();

        for (Segment segment : segments) {
            if (Vector.sub(segment.coords[0], center).length() < range
                    || Vector.sub(segment.coords[1], center).length() < range) {
                ret.add(new UsableWeighedSegment(segment));
            }
        }

        return ret;
    }

}
