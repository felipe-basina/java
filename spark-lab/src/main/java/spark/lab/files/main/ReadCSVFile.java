package spark.lab.files.main;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import spark.lab.files.models.BioStats;
import spark.lab.general.InitializeContext;

import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

public class ReadCSVFile extends InitializeContext {

    private static final String CSV_FILE_NAME = "/files/biostats.csv";

    public ReadCSVFile() {
        super();
    }

    public static void main(String[] args) {
        new ReadCSVFile();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = getCSVFileContent(ss);
            dataset.show();
            String[] availableColumns = dataset.columns();
            Stream.of(availableColumns).forEach(System.out::println);
            showByGroupBy(dataset, availableColumns[1]);
            showAverageOf(dataset, availableColumns[2]);
            showAverageOf(dataset, availableColumns[3]);
            showAverageOf(dataset, availableColumns[4]);
            filterBy(dataset, availableColumns[2], 35);
        }
    }

    private static Dataset<Row> getCSVFileContent(SparkSession ss) {
        URL csvFile = getFileAsResource();
        return ss.read()
                .option("header", true)
                .csv(csvFile.getPath());
    }

    private static void showByGroupBy(Dataset<Row> dataset, final String columnName) {
        dataset
                .groupBy(col(columnName))
                .count()
                .show();
    }

    private static void showAverageOf(Dataset<Row> dataset, final String columnName) {
        dataset
                .agg(round(avg(columnName), 2))
                .show();
    }

    private static void filterBy(Dataset<Row> dataset, final String columnName, final Integer columnValue) {
        String[] columns = header(dataset);
        Dataset<BioStats> bioStatsDataset = dataset
                .filter((FilterFunction<Row>) value
                        -> Long.parseLong(value.getAs(columnName).toString().trim()) > columnValue)
                .select(col(columns[0]).as("name"),
                        trim(col(columns[1])).as("sex"),
                        col(columns[2]).as("age"),
                        col(columns[3]).as("height").cast(DataTypes.IntegerType),
                        col(columns[4]).as("weight").cast(DataTypes.IntegerType))
                .as(BioStats.encoder());
        bioStatsDataset.collectAsList().forEach(System.out::println);
        JavaRDD<BioStats> bioStatsJavaRDD = bioStatsDataset.toJavaRDD();
        List<BioStats> bioStats = bioStatsJavaRDD.collect();
        System.out.println("Total filtered elements = " + bioStats.size());
    }

    private static String[] header(Dataset<Row> dataset) {
        return dataset.columns();
    }

    private static URL getFileAsResource() {
        return ReadCSVFile.class.getResource(CSV_FILE_NAME);
    }

}
