package afstudeerproject.Basic;

import afstudeerproject.Basic.Integration.Integrator.WeighedVector;
import afstudeerproject.Clustering.EfficientSingleLinkage;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Gridpoint {

    public ArrayList<Vector> inRange = new ArrayList<Vector>();
    public ArrayList<Vector> clustered = new ArrayList<Vector>();
    
    boolean isDirty = true;

    
    
    public void cluster() {
        if(!isDirty){
            return;
        }
        ArrayList<ArrayList<Vector>> clusters = new EfficientSingleLinkage().clustering(inRange);
        clustered = new ArrayList<Vector>();
        for (ArrayList<Vector> cluster : clusters) {
            Vector result = new Vector(new double[]{0,0,0});
            for (Vector v : cluster) {
                result = Vector.add(result, v);
            }
            clustered.add(result);
        }
        isDirty = false;
    }

    public ArrayList<WeighedVector> getRangeScaled(double scale) {
        this.isDirty = true;
        ArrayList<WeighedVector> scaled = new ArrayList<WeighedVector>();
        for (Vector v : inRange) {
            scaled.add(new WeighedVector(v, scale));
        }
        return scaled;
    }

    public void draw(Graphics2D graphics, Point p, double scale) {
        ArrayList<Vector>draw = afstudeerproject.Afstudeerproject.drawclustertoggle ? clustered : inRange;
        if(!afstudeerproject.Afstudeerproject.drawclustertoggle){
            scale *=20;
        }
        for (Vector vector : draw) {
            graphics.drawLine(p.x, p.y, p.x + Math.round(Math.round(vector.x * scale)), p.y + Math.round(Math.round(vector.y * scale)));
            graphics.drawLine(p.x, p.y, p.x + Math.round(Math.round(-vector.x * scale)), p.y + Math.round(Math.round(-vector.y * scale)));
        }
    }
}
