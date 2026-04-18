package impl.algs;

import com.google.auto.service.AutoService;
import ex.api.AnalysisService;

import java.util.Arrays;
import java.util.List;

@AutoService(AnalysisService.class)
public class KMedianAnalysis extends Analysis {
    @Override
    public String getName() {
        return "k-median";
    }

    @Override
    protected void updateCentroids() {
        for (int i = 0; i < k; i++) {
            List<double[]> cluster = clusters.get(i);

            if (cluster.isEmpty()) {
                continue;
            }

            double[] newCentroid = new double[centroids[i].length];

            for (int dim = 0; dim < newCentroid.length; dim++) {
                double[] dimValues = new double[cluster.size()];

                for (int p = 0; p < cluster.size(); p++) {
                    dimValues[p] = cluster.get(p)[dim];
                }

                Arrays.sort(dimValues);

                int mid = dimValues.length / 2;
                if (dimValues.length % 2 == 0) {
                    newCentroid[dim] = (dimValues[mid - 1] + dimValues[mid]) / 2.0;
                } else {
                    newCentroid[dim] = dimValues[mid];
                }
            }

            centroids[i] = newCentroid;
        }
    }

    @Override
    protected double calculateDistance(double[] point1, double[] point2) {
        double sum = 0.0;

        for (int i = 0; i < point1.length; i++) {
            sum += Math.abs(point1[i] - point2[i]);
        }

        return sum;
    }
}
