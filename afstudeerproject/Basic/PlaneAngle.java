package afstudeerproject.Basic;

public class PlaneAngle {
    
    /*
     * angles to planes xy, xz and yz, in radians.
     */
    public final double xy, xz, yz;
    
    public PlaneAngle(Vector v1, Vector v2) {
        if (v1.length() == 0 || v2.length() == 0) {
            throw new ArithmeticException("Argument " + (v1.length() == 0 ? "1" : "2") + " has length zero.");
        }
        
        
        //Degenerate cases have to be filtered out, so a comparison between
        //ex. (0,0) and (1,0) always returns 0
        boolean dxy = (v1.x != 0 || v1.y != 0) && (v2.x != 0 || v2.y != 0);
        boolean dxz = (v1.x != 0 || v1.z != 0) && (v2.x != 0 || v2.z != 0);
        boolean dyz = (v1.y != 0 || v1.z != 0) && (v2.y != 0 || v2.z != 0);
        
        //Note that atan2 takes arguments (y,x). 
        xy = (dxy)?Math.abs(Math.atan2(v1.y, v1.x) - Math.atan2(v2.y, v2.x)):0;
        xz = (dxz)?Math.abs(Math.atan2(v1.z, v1.x) - Math.atan2(v2.z, v2.x)):0;
        yz = (dyz)?Math.abs(Math.atan2(v1.z, v1.y) - Math.atan2(v2.z, v2.y)):0;
        
        
    }
    

    @Override
    public String toString() {
        return "(" + xy + " " + xz + " " + yz + ")";
    }
}
