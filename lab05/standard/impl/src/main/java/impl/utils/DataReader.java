package impl.utils;

import ex.api.DataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private DataReader() {}

    public static DataSet readDataSet(String filePath) {
        DataSet dataSet = new DataSet();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String[] headers = br.readLine().split(",");
            dataSet.setHeader(headers);

            String line;
            List<String[]> dataList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataList.add(values);
            }

            String[][] data = new String[dataList.size()][dataList.getFirst().length];

            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            dataSet.setData(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataSet;
    }
}
