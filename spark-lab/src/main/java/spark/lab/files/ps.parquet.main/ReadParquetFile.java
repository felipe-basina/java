package spark.lab.files.ps.parquet.main;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import spark.lab.general.InitializeContext;

import java.io.Serializable;

import static org.apache.spark.sql.functions.col;

public class ReadParquetFile extends InitializeContext implements Serializable {

    private static final String PARQUET_SOURCE_FOLDER = "/home/afsimao/Documents/datax/assembler_20220317";
    //private static final String CSV_SOURCE_FILE = "/home/afsimao/Downloads/analise-transporter.csv";
    private static final String CSV_SOURCE_FILE = "/home/afsimao/Downloads/transporter.csv";

    public ReadParquetFile() {
        super();
    }

    public static void main(String[] args) {
        new ReadParquetFile();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> fromParquet = readParquetFiles(ss);
                    //.filter((FilterFunction<Row>) v1 -> v1.getAs("idt_turnover").equals("69975017050"));
            LOGGER.info("Total content from parquet = " + fromParquet.count());

            LOGGER.info("Total null ids from parquet = "
                    + fromParquet.toJavaRDD().filter(row -> row.getAs("idt_turnover") == null).count());

            Dataset<Row> fromCsv = readCsvFile(ss);
            LOGGER.info("Total content from csv = " + fromCsv.count());

            Dataset<Row> fromParquetIds = fromParquet.select(col("idt_turnover"));
            Dataset<Row> fromCsvIds = fromCsv.select(col("idt_id"));

            Dataset<Row> joinDataset = fromParquetIds.join(fromCsvIds,
                    fromParquetIds.col("idt_turnover").equalTo(fromCsvIds.col("idt_id")),
                    "inner");
            LOGGER.info("Total content from join = " + joinDataset.count());
            joinDataset.show();
        }
    }

    private static Dataset<Row> readParquetFiles(SparkSession ss) {
        return ss.read()
                .parquet(PARQUET_SOURCE_FOLDER);
    }

    private static Dataset<Row> readCsvFile(SparkSession ss) {
        return ss.read()
                .option("header", true)
                .option("delimiter", ";")
                .csv(CSV_SOURCE_FILE);
    }

}
