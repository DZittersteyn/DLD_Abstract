package afstudeerproject.Models;

import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Settings.ModelSettings;
import java.util.HashSet;

public class SearchGrid {
    
    SearchGridNode[][][] searchGrid = new SearchGridNode[ModelSettings.gridResolution[0]][ModelSettings.gridResolution[1]][ModelSettings.gridResolution[2]];

    
    
    
    public SearchGrid() {
        for (int i = 0; i < searchGrid.length; i++) {
            for (int j = 0; j < searchGrid[i].length; j++) {
                for (int k = 0; k < searchGrid[i][j].length; k++) {
                    searchGrid[i][j][k] = new SearchGridNode();
                }
            }
        }
    }

    public SearchGridNode getNode(int x, int y, int z){
        if(
           x < 0 || x >= searchGrid.length || 
           y < 0 || y >= searchGrid[0].length || 
           z < 0 || z >= searchGrid[0][0].length){
            return new SearchGridNode();
        }
        
        return searchGrid[x][y][z];
    }
    
    
    public void add(Segment s) {

        int[] index1 = indexFromCoordinate(s.coords[0].x, s.coords[0].y, s.coords[0].z);
        searchGrid[index1[0]][index1[1]][index1[2]].segments.add(s);
        int[] index2 = indexFromCoordinate(s.coords[1].x, s.coords[1].y, s.coords[1].z);
        searchGrid[index2[0]][index2[1]][index2[2]].segments.add(s);



    }

    public HashSet<UsableWeighedSegment> getInRange(Vector center, double[] range) { // returns: at least all items that lie within the range.
        int[] max = indexFromCoordinate(center.x + range[0] + 1, center.y + range[1] + 1, center.z + range[2] + 1);
        int[] min = indexFromCoordinate(center.x - range[0], center.y - range[1], center.z - range[2]);

        HashSet<Segment> inRange = new HashSet<Segment>();
        for (int x = min[0]; x <= max[0]; x++) {
            for (int y = min[1]; y <= max[1]; y++) {
                for (int z = min[2]; z <= max[2]; z++) {
                    if (isIn(center, new Vector(coordinateFromIndex(x, y, z)), range)) {
                        inRange.addAll(getNode(x, y, z).instance());
                    }
                }
            }
        }
        HashSet<UsableWeighedSegment> ret = new HashSet<UsableWeighedSegment>();
        for (Segment segment : inRange) {
            ret.add(new UsableWeighedSegment(segment));
        }
        return ret;
    }

    public static int[] indexFromCoordinate(double x, double y, double z) {
        int[] index = {
            (int) Math.round((x - ModelSettings.gridValue[0][0]) / ModelSettings.gridCellSize[0]),
            (int) Math.round((y - ModelSettings.gridValue[1][0]) / ModelSettings.gridCellSize[1]),
            (int) Math.round((z - ModelSettings.gridValue[2][0]) / ModelSettings.gridCellSize[2]),};
        return index;
    }

    public static double[] coordinateFromIndex(int x, int y, int z) {
        return new double[]{
                    ModelSettings.gridValue[0][0] + ModelSettings.gridCellSize[0] * x,
                    ModelSettings.gridValue[1][0] + ModelSettings.gridCellSize[1] * y,
                    ModelSettings.gridValue[2][0] + ModelSettings.gridCellSize[2] * z,};
    }

    public static boolean isIn(Vector v, Vector position, double[] range) {
        return Math.abs(v.x - position.x) < range[0]
                && Math.abs(v.y - position.y) < range[1]
                && Math.abs(v.z - position.z) < range[2];
    }
}
