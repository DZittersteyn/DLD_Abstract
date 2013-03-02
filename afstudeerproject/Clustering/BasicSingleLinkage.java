package afstudeerproject.Clustering;

import afstudeerproject.Basic.Vector;
import java.util.ArrayList;

public class BasicSingleLinkage extends Clustering {

    public final double MINSIMIL = 0.9;

    @Override
    public ArrayList<ArrayList<Vector>> clustering(ArrayList<Vector> vectors) {

        ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < vectors.size(); i++) {
            ArrayList<Integer> cluster = new ArrayList<Integer>();
            cluster.add(i);
            clusters.add(cluster);
        }


        while (true) {
            
            double maxsimil = 0;
            int min1 = -1, min2 = -1;

            if (clusters.size() <= 1) {
                break;
            }
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {

                    double simil = 0;
                    for (Integer vec : clusters.get(i)) {
                        for (Integer vec2 : clusters.get(j)) {
                            double newSimil = vectors.get(vec).cosSimilarity(vectors.get(vec2));
                            if (newSimil > simil) {
                                simil = newSimil;
                            }
                        }
                    }

                    if (simil > maxsimil) {
                        maxsimil = simil;
                        min1 = i;
                        min2 = j;
                    }
                }
            }
            if (maxsimil < MINSIMIL) {
                break;
            }
            clusters.get(min1).addAll(clusters.get(min2));
            clusters.remove(min2);
            

        }
        ArrayList<ArrayList<Vector>> clusteredVectors = new ArrayList<ArrayList<Vector>>();
        for (ArrayList<Integer> cluster : clusters) {
            ArrayList<Vector> c = new ArrayList<Vector>();
            for (Integer vec : cluster) {
                c.add(vectors.get(vec));
            }
            clusteredVectors.add(c);
        }

        return clusteredVectors;

    }
}
