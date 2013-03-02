package afstudeerproject.Clustering;

import afstudeerproject.Basic.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class EfficientSingleLinkage extends Clustering {

    public final double MINSIMIL = 0.95;

    @Override
    public ArrayList<ArrayList<Vector>> clustering(ArrayList<Vector> vectors) {
        // the result of this method is the clustering at level MINSIMIL of the dendrogram

        Cluster[] clusters = new Cluster[vectors.size()];


        for (int i = 0; i < vectors.size(); i++) {
            if (clusters[i] == null) {
                clusters[i] = new Cluster();
                clusters[i].belongs.add(i);
            }
            for (int j = i + 1; j < vectors.size(); j++) {
                double similarity = vectors.get(i).cosSimilarity(vectors.get(j));
                if (similarity > MINSIMIL) {
                    clusters[j] = clusters[i];
                    clusters[j].belongs.add(j);
                    break;
                }

            }
        }
        
        HashSet<Cluster> unique = new HashSet<Cluster>();
        
        unique.addAll(Arrays.asList(clusters));
        
        ArrayList<ArrayList<Vector>> result = new ArrayList<ArrayList<Vector>>();
        for (Cluster cluster : unique) {
            ArrayList<Vector> c = new ArrayList<Vector>();
            for (Integer vec : cluster.belongs) {
                c.add(vectors.get(vec));
            }
            result.add(c);
        }
        return result;
    }
}

class Cluster {

    ArrayList<Integer> belongs = new ArrayList<Integer>();
}
