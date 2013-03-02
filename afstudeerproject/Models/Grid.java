package afstudeerproject.Models;

import afstudeerproject.Basic.Gridpoint;
import afstudeerproject.Basic.Integration.Integrator.IntegrationResult;
import afstudeerproject.Basic.Integration.Integrator.Integrator;
import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Exceptions.Herpederp;
import afstudeerproject.Exceptions.NoIntersectionFound;
import afstudeerproject.Exceptions.OutOfRangeOfGrid;
import afstudeerproject.Exceptions.inBoxException;
import afstudeerproject.PercPrettyPrinter;
import afstudeerproject.Settings.ModelSettings;
import java.util.ArrayList;
import java.util.HashMap;

public class Grid {

    public Gridpoint[][][] grid = new Gridpoint[ModelSettings.gridResolution[0]][ModelSettings.gridResolution[1]][ModelSettings.gridResolution[2]];

    public Grid() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int k = 0; k < grid[i][j].length; k++) {
                    grid[i][j][k] = new Gridpoint();
                }
            }
        }
    }

    public void compensate(Integrator i) {
        HashMap<Vector, Double> comp = new HashMap<Vector, Double>();
        for (IntegrationResult integrationResult : i.points) {
            /*for (WeighedVector weighedVector : integrationResult.considered) {
                if (comp.containsKey(weighedVector.vector)) {
                    comp.put(weighedVector.vector, comp.get(weighedVector.vector) + weighedVector.weight);
                } else {
                    comp.put(weighedVector.vector, weighedVector.weight);
                }

            }*/
        }
        for (Vector key : comp.keySet()) {
            key.mulInPlace(Math.max(0, 1 - comp.get(key)));
        }

        for (IntegrationResult integrationResult : i.points) {
            integrationResult.considered = null;
        }

    }

    public void cluster() {
        int done = 0;
        //PercPrettyPrinter p = new PercPrettyPrinter("Clustering", grid.length * grid[0].length * grid[0][0].length);
        for (Gridpoint[][] layer : grid) {
            for (Gridpoint[] row : layer) {
                for (Gridpoint g : row) {

                    g.cluster();
                    done++;
                    //p.print(done);
                }
            }
        }
        //p.close();
    }

    public void addSegment(Segment s) {
        int[] indicesBegin = indexFromCoordinate(s.coords[0]);
        int[] indicesEnd = indexFromCoordinate(s.coords[1]);

        int[] indicesX = {
            Math.max(Math.min(indicesBegin[0], indicesEnd[0]), 0),
            Math.min(Math.max(indicesBegin[0], indicesEnd[0]), ModelSettings.gridResolution[0] - 1),};
        int[] indicesY = {
            Math.max(Math.min(indicesBegin[1], indicesEnd[1]), 0),
            Math.min(Math.max(indicesBegin[1], indicesEnd[1]), ModelSettings.gridResolution[1] - 1),};
        int[] indicesZ = {
            Math.max(Math.min(indicesBegin[2], indicesEnd[2]), 0),
            Math.min(Math.max(indicesBegin[2], indicesEnd[2]), ModelSettings.gridResolution[2] - 1),};




        for (int i = indicesX[0]; i <= indicesX[1]; i++) {
            for (int j = indicesY[0]; j <= indicesY[1]; j++) {
                for (int k = indicesZ[0]; k <= indicesZ[1]; k++) {
                    try {
                        Vector position = new Vector(CoordinateFromIndex(i, j, k));
                        ArrayList<Vector> intersections = boxIntersections(s, position, ModelSettings.gridCellRange);

                        switch (intersections.size()) {
                            case 0: // no intersections, no inBoxException -> not in range
                                // do nothing
                                break;
                            case 1: // 1 intersection -> starts OR ends in range

                                boolean begin = Grid.isIn(s.coords[0], position, ModelSettings.gridCellRange);
                                boolean end = Grid.isIn(s.coords[1], position, ModelSettings.gridCellRange);

                                if ((begin && end)) {
                                    throw new Herpederp("one intersection while begin and end are both in range");
                                }
                                if (begin) {
                                    grid[i][j][k].inRange.add(Vector.sub(intersections.get(0), s.coords[0]));

                                }
                                if (end) {
                                    grid[i][j][k].inRange.add(Vector.sub(s.coords[1], intersections.get(0)));

                                }
                                // else this is a degenerate case, where the line intersects a corner, ignore!
                                break;
                            case 2: // 2 intersections -> runs through range
                                grid[i][j][k].inRange.add(Vector.sub(intersections.get(1), intersections.get(0)));

                                break;
                            default:
                                System.out.println(intersections.size());

                                throw new Herpederp("WRONG");
                        }
                    } catch (inBoxException e) {
                        grid[i][j][k].inRange.add(Vector.sub(s.coords[0], s.coords[1]));

                    }
                }
            }
        }

    }

    public Gridpoint[][][] nearestGridpoints(Vector v) throws OutOfRangeOfGrid {
        return nearestGridpoints(v.x, v.y, v.z);
    }

    /*
     * Returns the gridpoints around the coordinate (x,y,z)
     */
    public Gridpoint[][][] nearestGridpoints(double x, double y, double z) throws OutOfRangeOfGrid {
        int[] floor = {
            (int) Math.floor((x - ModelSettings.gridValue[0][0]) / ModelSettings.gridCellSize[0]),
            (int) Math.floor((y - ModelSettings.gridValue[1][0]) / ModelSettings.gridCellSize[1]),
            (int) Math.floor((z - ModelSettings.gridValue[2][0]) / ModelSettings.gridCellSize[2]),};

        int[] ceili = {
            floor[0] + 1,
            floor[1] + 1,
            floor[2] + 1};
        if (floor[0] < 0 || floor[0] >= ModelSettings.gridResolution[0]
                || floor[1] < 0 || floor[1] >= ModelSettings.gridResolution[1]
                || floor[2] < 0 || floor[2] >= ModelSettings.gridResolution[2]) {
            throw new OutOfRangeOfGrid(floor);
        }

        if (ceili[0] < 0 || ceili[0] >= ModelSettings.gridResolution[0]
                || ceili[1] < 0 || ceili[1] >= ModelSettings.gridResolution[1]
                || ceili[2] < 0 || ceili[2] >= ModelSettings.gridResolution[2]) {
            throw new OutOfRangeOfGrid(ceili);
        }




        return new Gridpoint[][][]{
                    {
                        {
                            grid[floor[0]][floor[1]][floor[2]],
                            grid[floor[0]][floor[1]][ceili[2]],},
                        {
                            grid[floor[0]][ceili[1]][floor[2]],
                            grid[floor[0]][ceili[1]][ceili[2]],},},
                    {
                        {
                            grid[ceili[0]][floor[1]][floor[2]],
                            grid[ceili[0]][floor[1]][ceili[2]],},
                        {
                            grid[ceili[0]][ceili[1]][floor[2]],
                            grid[ceili[0]][ceili[1]][ceili[2]],},},};
    }

    public static int[] indexFromCoordinate(double x, double y, double z) {
        int[] index = {
            (int) Math.round((x - ModelSettings.gridValue[0][0]) / ModelSettings.gridCellSize[0]),
            (int) Math.round((y - ModelSettings.gridValue[1][0]) / ModelSettings.gridCellSize[1]),
            (int) Math.round((z - ModelSettings.gridValue[2][0]) / ModelSettings.gridCellSize[2]),};
        return index;
    }

    public static double[] CoordinateFromIndex(int x, int y, int z) {
        return new double[]{
                    ModelSettings.gridValue[0][0] + ModelSettings.gridCellSize[0] * x,
                    ModelSettings.gridValue[1][0] + ModelSettings.gridCellSize[1] * y,
                    ModelSettings.gridValue[2][0] + ModelSettings.gridCellSize[2] * z,};
    }

    public static int[] indexFromCoordinate(Vector v) {
        return indexFromCoordinate(v.x, v.y, v.z);
    }

    public static boolean isIn(Vector v, Vector position, double[] range) {
        return Math.abs(v.x - position.x) < range[0]
                && Math.abs(v.y - position.y) < range[1]
                && Math.abs(v.z - position.z) < range[2];
    }

    public static ArrayList<Vector> boxIntersections(Segment s, Vector position, double[] range) throws inBoxException {

        if (Grid.isIn(position, s.coords[0], range) && Grid.isIn(position, s.coords[1], range)) {
            throw new inBoxException();
        }

        ArrayList<Vector> normals = new ArrayList<Vector>();

        normals.add(new Vector(range[0], 0, 0));
        normals.add(new Vector(-range[0], 0, 0));
        normals.add(new Vector(0, range[1], 0));
        normals.add(new Vector(0, -range[1], 0));
        normals.add(new Vector(0, 0, range[2]));
        normals.add(new Vector(0, 0, -range[2]));

        ArrayList<Vector> intersections = new ArrayList<Vector>();
        for (Vector n : normals) {
            try {
                Vector center = Vector.add(position, n);
                Vector toAdd = planeIntersection(s, center, n);

                if (Math.abs(center.x - toAdd.x) <= range[0]
                        && Math.abs(center.y - toAdd.y) <= range[1]
                        && Math.abs(center.z - toAdd.z) <= range[2]) {
                    if (!intersections.contains(toAdd)) {
                        intersections.add(toAdd);
                    }
                }



            } catch (NoIntersectionFound e) {
                // this is okay, as some planes will not be intersected.
            }
        }
        return intersections;
    }

    public static Vector planeIntersection(Segment s, Vector point, Vector normal) throws NoIntersectionFound {


        if (normal.length() == 0) {
            throw new ArithmeticException("Normal has length zero");
        }

        if (Vector.dot(
                new Vector(s.coords[1].x - s.coords[0].x, s.coords[1].y - s.coords[0].y, s.coords[1].z - s.coords[0].z),
                normal) == 0) {
            throw new NoIntersectionFound("Parallel line has no intersection");
        }

        double d = Vector.dot(Vector.sub(point, s.coords[0]), normal) / Vector.dot(Vector.sub(s.coords[1], s.coords[0]), normal);
        if (d < 0 || d > 1) {
            throw new NoIntersectionFound("Intersection lies outside segment, 0 < d < 1 violated. d = " + d);
        } else {
            Vector inters = Vector.add(s.coords[0], Vector.mul(Vector.sub(s.coords[1], s.coords[0]), d));
            return inters;
        }
    }
}
