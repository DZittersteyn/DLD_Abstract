package afstudeerproject.Basic.Integration.Interpolation;

import afstudeerproject.Basic.Gridpoint;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.OutOfRangeOfGrid;
import afstudeerproject.Models.Grid;
import afstudeerproject.Settings.ModelSettings;
import java.util.ArrayList;

public class BilinearInterpolator extends Interpolator {

    @Override
    public ArrayList<WeighedVector> interpolatedVectors(Vector v, Grid g) throws OutOfRangeOfGrid {


        /*
         * Trilinear interpolation done by:
         *
         * - two bilinear interpolations on 2x4 points that have equal z-value
         *
         * - one linear interpolation on the resulting two points
         */


        Gridpoint[][][] gridpoints = g.nearestGridpoints(v);


        Vector modDist = new Vector(
                (v.x - ModelSettings.gridValue[0][0]) % ModelSettings.gridCellSize[0],
                (v.y - ModelSettings.gridValue[1][0]) % ModelSettings.gridCellSize[1],
                (v.z - ModelSettings.gridValue[2][0]) % ModelSettings.gridCellSize[2]);

        
        //bilinear interpolation *2
        ArrayList<WeighedVector> bilin1 = new ArrayList<WeighedVector>();
        ArrayList<WeighedVector> bilin2 = new ArrayList<WeighedVector>();


        double x = modDist.x;
        double x1 = 0;
        double x2 = ModelSettings.gridCellSize[0];
        
        double y = modDist.y;
        double y1 = 0;
        double y2 = ModelSettings.gridCellSize[1];

        double[][] w = new double[2][2];

        w[0][0] = ((x2 - x) * (y2 - y)) / ((x2 - x1) * (y2 - y1));
        w[1][0] = ((x - x1) * (y2 - y)) / ((x2 - x1) * (y2 - y1));
        w[0][1] = ((x2 - x) * (y - y1)) / ((x2 - x1) * (y2 - y1));
        w[1][1] = ((x - x1) * (y - y1)) / ((x2 - x1) * (y2 - y1));

        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                bilin1.addAll(gridpoints[i][j][0].getRangeScaled(w[i][j]));
                bilin2.addAll(gridpoints[i][j][1].getRangeScaled(w[i][j]));
            }

        }


        //linear interpolation
        ArrayList<WeighedVector> lin = new ArrayList<WeighedVector>();

        double z = modDist.z;
        double z1 = 0;
        double z2 = ModelSettings.gridCellSize[2];

        
        double v0 = (z2 - z) / (z2 - z1);
        double v1 = (z - z1) / (z2 - z1);
        
        if (v0 != 0) {
            for (WeighedVector weighedVector : bilin1) {
                weighedVector.weight *= v0;
                lin.add(weighedVector);
            }
        }
        if (v1 != 0) {
            for (WeighedVector weighedVector : bilin2) {
                weighedVector.weight *= v1;
                lin.add(weighedVector);
            }
        }


        return lin;

    }
}
