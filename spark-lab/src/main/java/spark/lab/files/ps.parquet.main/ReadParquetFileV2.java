package spark.lab.files.ps.parquet.main;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import spark.lab.general.InitializeContext;

import java.io.Serializable;

import static org.apache.spark.sql.functions.lit;

public class ReadParquetFileV2 extends InitializeContext implements Serializable {

//    private static final String PARQUET_SOURCE_FOLDER = "/home/afsimao/Documents/datax/assembler_20220317_fake";
    private static final String PARQUET_SOURCE_FOLDER = "/home/afsimao/Documents/datax/assembler_20220317_fake_qall";
    private static final String CSV_SOURCE_FILE = "/home/afsimao/Downloads/transporter.csv";

    public ReadParquetFileV2() {
        super();
    }

    public static void main(String[] args) {
        new ReadParquetFileV2();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> fromParquet = readParquetFiles(ss)
                    //.filter((FilterFunction<Row>) v1 -> !v1.getAs("idt_turnover").equals("1"));
                    .filter((FilterFunction<Row>) v1 -> v1.getAs("idt_turnover").equals("69935386001"))
                    .withColumn("idt_turnover", lit("10001"));
            LOGGER.info("Total content from parquet = " + fromParquet.count());
            fromParquet.distinct().show(4);

//            Dataset<Row> fromCsv = readCsvFile(ss);
//                //.filter((FilterFunction<Row>) v1 -> !v1.getAs("idt_id").equals("1"));
//            LOGGER.info("Total content from csv = " + fromCsv.count());
//
//            Dataset<Row> fromParquetIds = fromParquet.select(col("idt_turnover"));
//            Dataset<Row> fromCsvIds = fromCsv.select(col("idt_id"));
//
//            Dataset<Row> joinDataset = fromParquetIds.join(fromCsvIds,
//                    fromParquetIds.col("idt_turnover").equalTo(fromCsvIds.col("idt_id")),
//                    "leftouter");
//            LOGGER.info("Total content from join = " + joinDataset.count());
//            joinDataset.show(111);
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
