package spark.lab.db.cassandra.main;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import spark.lab.general.InitializeContext;

import java.net.URL;

public class ReadCSVFile extends InitializeContext {

    private static final String CSV_FILE_NAME = "/files/biostats.csv";

    public ReadCSVFile() {
        super();
    }

    public static void main(String[] args) {
        new ReadCSVFile();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            URL resource = ReadCSVFile.class.getResource(CSV_FILE_NAME);
            Dataset<Row> dataset = ss.read()
                    .option("header", true)
                    .csv(resource.getPath());
            dataset.show();
        }
    }

}
