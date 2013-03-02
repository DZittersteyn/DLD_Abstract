package afstudeerproject.Models;

import afstudeerproject.Basic.Integration.SegmentKernel.DirectionalKernel;
import afstudeerproject.Basic.Integration.SegmentKernel.LocationalKernel;
import afstudeerproject.Basic.Segment;
import afstudeerproject.Basic.UsableWeighedSegment;
import afstudeerproject.Basic.Vector;
import afstudeerproject.Models.SegmentSets.UsableWeighedSegmentSet;
import afstudeerproject.PercPrettyPrinter;
import afstudeerproject.Settings.ModelSettings;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Dataset {

    public ArrayList<Segment> segments = new ArrayList<Segment>();

    public abstract ArrayList<UsableWeighedSegment> segmentsInRange(double range, Vector location);

    public Vector[] max() {
        PercPrettyPrinter p = new PercPrettyPrinter("Determining max density", ModelSettings.gridResolution[0]);
        Vector maxpos = null;
        double max = 0;
        UsableWeighedSegmentSet umax = null;

        for (int i = 0; i < ModelSettings.gridResolution[0]; i++) {
            p.print(i);
            for (int j = 0; j < ModelSettings.gridResolution[1]; j++) {
                for (int k = 0; k < ModelSettings.gridResolution[2]; k++) {
                    Vector t = new Vector(Grid.CoordinateFromIndex(i, j, k));
                    LocationalKernel kernel = new LocationalKernel(ModelSettings.gridCellRange[0], ModelSettings.gridCellRange[0]);
                    UsableWeighedSegmentSet u = kernel.apply(this,t);
                    double tempmax = u.weight();
                    if (tempmax > max) {
                        maxpos = t;
                        max = tempmax;
                        umax = u;
                    }
                }
            }
        }
        p.close();
        p = new PercPrettyPrinter("Determining max direction", 8);
        int done = 0;
        Vector maxdir = null;
        double wmax = 0;
        for (double theta = -Math.PI / 2; theta < Math.PI / 2; theta += Math.PI / 8) {
            for (double phi = 0; phi < 2 * Math.PI; phi += Math.PI / 8) {
                double sinphi = Math.sin(phi);
                double cosphi = Math.cos(phi);
                double sintheta = Math.sin(theta);
                double costheta = Math.cos(theta);
                Vector v = new Vector(sintheta * cosphi, sintheta * sinphi, costheta);

                DirectionalKernel k = new DirectionalKernel(Math.PI / 8, Math.PI / 8 * 1.1);
                double w = k.applyToCopy(umax,v).weight();
                if (w > wmax) {
                    wmax = w;
                    maxdir = v;
                }

            }
            p.print(++done);
        }
        p.close();
        return new Vector[]{maxpos, maxdir};
    }

    public Dataset(File f) throws FileNotFoundException {
        Scanner sc = new Scanner(f);


        int numPaths = sc.nextInt();
        int perc = 0;
        double[] max = {Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
        double[] min = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};

        
        int numseg = 0;
        PercPrettyPrinter p = new PercPrettyPrinter("Loading dataset " + ModelSettings.filename, numPaths);
        for (int i = 0; i < numPaths; i++) {
            p.print(i);
            ArrayList<Segment> path = new ArrayList<Segment>();
            int numSegments = sc.nextInt();
            Vector prev = null;
            for (int j = 0; j < numSegments; j++) {
                double x = sc.nextDouble();
                max[0] = Math.max(x, max[0]);
                min[0] = Math.min(x, min[0]);
                double y = sc.nextDouble();
                max[1] = Math.max(y, max[1]);
                min[1] = Math.min(y, min[1]);
                double z = sc.nextDouble();
                max[2] = Math.max(z, max[2]);
                min[2] = Math.min(z, min[2]);
                Vector v = new Vector(x, y, z);

                if (prev != null) {
                    path.add(new Segment(prev, v));
                    numseg ++;
                }
                prev = new Vector(v);

            }
            
            if(ModelSettings.isWeighed){
                sc.next(); //read weight:
                double weight = sc.nextDouble();
                for (Segment segment : path) {
                    segment.weight = weight;
                }
            }
            
            segments.addAll(path);
        }
        p.close();
        System.out.println("---------------------------------");
        for (int i = 0; i < min.length; i++) {
            System.out.println("min: " + min[i] + " max: " + max[i]);

        }
        System.out.println("#segments: " + numseg);
        System.out.println("---------------------------------");


    }
}
