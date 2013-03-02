package afstudeerproject.Models;

import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DatasetGrid extends Dataset {

    SearchGrid searchgrid = new SearchGrid();

    public DatasetGrid(File f) throws FileNotFoundException {
        super(f);
        for (Segment segment : segments) {
            searchgrid.add(segment);
        }

    }

    @Override
    public ArrayList<UsableWeighedSegment> segmentsInRange(double range, Vector location) {
        return new ArrayList<UsableWeighedSegment>(searchgrid.getInRange(location, new double[]{range,range,range}));
    }
}
