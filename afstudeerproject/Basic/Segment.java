package afstudeerproject.Basic;

public class Segment {

    public Vector[] coords = new Vector[2];
    
    public double weight = 1;
    
    
    
    public Segment(Vector start, Vector end) {
        coords[0] = new Vector(start);
        coords[1] = new Vector(end);
    }
     
    public Segment(Segment s){
        coords[0] = s.coords[0];
        coords[1] = s.coords[1];
    }

    @Override
    public String toString() {
        return "(" + coords[0] + ", " + coords[1] + ")";
    }
    
    public String toVTK(int idx){
        return coords[idx].x + " " + coords[idx].y + " " + coords[idx].z;
    }
}
