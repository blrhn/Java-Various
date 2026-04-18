package impl.algs;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Analysis implements AnalysisService {
    protected int k;
    protected int maxIterations;
    protected DataSet dataSet;
    protected double[][] centroids;
    protected List<List<double[]>> clusters;

    private boolean isProcessing = false;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
        try {
            this.k = Integer.parseInt(options[0]);
            this.maxIterations = Integer.parseInt(options[1]);
        } catch (Exception e) {
            throw new AnalysisException(e.getMessage());
        }
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if (isProcessing) {
            throw new AnalysisException("submit in progress");
        }

        this.dataSet = ds;
        this.isProcessing = true;

        try {
            run();
        } catch (Exception e) {
            throw new AnalysisException(e.getMessage());
        } finally {
            this.isProcessing = false;
        }
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        if (isProcessing) {
            return null;
        }

        String[] originalHeaders =  dataSet.getHeader();
        String[][] originalData = dataSet.getData();

        String[] newHeaders = Arrays.copyOf(originalHeaders, originalHeaders.length + 1);
        newHeaders[newHeaders.length - 1] = "cluster";

        String[][] newData = new String[originalData.length][newHeaders.length];

        for (int i = 0; i < originalData.length; i++) {
            double[] d = Arrays.stream(originalData[i]).mapToDouble(Double::parseDouble).toArray();

            int cluster = getNearestCentroid(d);
            System.arraycopy(originalData[i], 0, newData[i], 0, originalData[i].length);
            newData[i][originalData[i].length] = String.valueOf(cluster);

        }

        DataSet result = new DataSet();
        result.setHeader(newHeaders);
        result.setData(newData);

        if (clear) {
            this.dataSet = null;
            this.centroids = null;
            this.clusters = null;
        }

        return result;
    }


    protected void centroidsInit() {
        Random rand = new Random();
        String[][] data = dataSet.getData();
        centroids = new double[k][data[0].length];

        for (int i = 0; i < k; i++) {
            int randomIdx = rand.nextInt(data.length);
            centroids[i] = Arrays.stream(data[randomIdx]).mapToDouble(Double::parseDouble).toArray();
        }
    }

    protected void assignClusters() {
        String[][] data = dataSet.getData();
        clusters = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }

        for (String[] point : data) {
            double[] doubles = Arrays.stream(point).mapToDouble(Double::parseDouble).toArray();

            int nearestCentroid = getNearestCentroid(doubles);
            clusters.get(nearestCentroid).add(doubles);
        }
    }

    protected int getNearestCentroid(double[] doubles) {
        int nearestIndex = 0;
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            double dist = calculateDistance(doubles, centroids[i]);

            if (dist < minDist) {
                minDist = dist;
                nearestIndex = i;
            }
        }

        return nearestIndex;
    }

    protected void run() {
        centroidsInit();

        for (int i = 0; i < maxIterations; i++) {
            assignClusters();
            updateCentroids();
        }
    }

    protected abstract void updateCentroids();
    protected abstract double calculateDistance(double[] point1, double[] point2);
}
