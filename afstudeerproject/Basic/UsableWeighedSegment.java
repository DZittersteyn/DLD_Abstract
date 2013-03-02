package afstudeerproject.Basic;

public class UsableWeighedSegment{
    public double used = 1;
    public Segment segment;
    
    public UsableWeighedSegment(Segment segment){
        this.segment = segment;
    }
    
    
    
    @Override
    public String toString(){
        return super.toString() + " used = " + used;
    }
    
}
