package spark.lab.files.ps.parquet.main;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import spark.lab.general.InitializeContext;

import java.io.Serializable;

public class GroupAndGenerateCsv extends InitializeContext implements Serializable {

    private static final String PARQUET_SOURCE_FOLDER = "/home/afsimao/Documents/datax/assembler_20220317_fake_qall";
    private static final String CSV_GROUPED_FILE = "/home/afsimao/Downloads/grouped.csv";
    private static final String CSV_SOURCE_GROUPED_FILE = "/home/afsimao/Downloads/grouped.csv/grouped.csv";

    public GroupAndGenerateCsv() {
        super();
    }

    public static void main(String[] args) {
        new GroupAndGenerateCsv();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = readParquetFiles(ss);
            RelationalGroupedDataset groupedDataset = dataset.groupBy("idt_safepay_bill_item");
            saveCsvFile(groupedDataset);
            LOGGER.info("Total grouped by `idt_safepay_bill_item`: "
                    + groupedDataset.sum("idt_safepay_bill_item").count());

//            Dataset<Row> fromCsv = readCsvFiles(ss);
//            fromCsv.foreach(row -> {
//                System.out.println((String) row.getAs("idt_safepay_bill_item"));
//            });
        }
    }

    private static Dataset<Row> readParquetFiles(SparkSession ss) {
        return ss.read()
                .parquet(PARQUET_SOURCE_FOLDER);
    }

    private static Dataset<Row> readCsvFiles(SparkSession ss) {
        return ss.read()
                .option("header", true)
                .option("delimiter", ",")
                .csv(CSV_SOURCE_GROUPED_FILE);
    }

    private static void saveCsvFile(RelationalGroupedDataset groupedDataset) {
        groupedDataset
                .count()
                .coalesce(1)
                .write()
                .option("header", true)
                .mode(SaveMode.Overwrite)
                .csv(CSV_GROUPED_FILE);
    }

}
