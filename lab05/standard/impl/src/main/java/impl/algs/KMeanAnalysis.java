package impl.algs;

import com.google.auto.service.AutoService;
import ex.api.AnalysisService;

import java.util.List;

@AutoService(AnalysisService.class)
public class KMeanAnalysis extends Analysis {

    @Override
    public String getName() {
        return "k-mean";
    }

    @Override
    protected void updateCentroids() {
        for (int i = 0; i < k; i++) {
            List<double[]> cluster = clusters.get(i);

            if (cluster.isEmpty()) {
                continue;
            }

            double[] newCentroid = new double[centroids[i].length];

            for (double[] point : cluster) {
                for (int j = 0; j < point.length; j++) {
                    newCentroid[j] += point[j];
                }
            }

            for (int j = 0; j < newCentroid.length; j++) {
                newCentroid[j] /= cluster.size();
            }

            centroids[i] = newCentroid;
        }
    }

    @Override
    protected double calculateDistance(double[] point1, double[] point2) {
        double sum = 0.0;

        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }

        return Math.sqrt(sum);
    }
}
