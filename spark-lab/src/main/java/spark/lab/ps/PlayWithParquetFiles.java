package spark.lab.ps;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import spark.lab.general.InitializeContext;

import static org.apache.spark.sql.functions.col;

public class PlayWithParquetFiles extends InitializeContext {

    private static final String SOURCE_FILE_NAME = "/home/afsimao/Downloads/s3-files";

    public PlayWithParquetFiles() {
        super();
    }

    public static void main(String[] args) {
        new PlayWithParquetFiles();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = readParquetFiles(ss, SOURCE_FILE_NAME);

            Column idtSafepayBillItemCol = col("idt_safepay_bill_item");
            Column numOperationValueCol = col("num_operation_value");

            dataset
                    .select(idtSafepayBillItemCol, numOperationValueCol)
                    .agg(functions.sum(numOperationValueCol)
                            .cast("decimal(20,2)")
                            .as("num_operation_value"))
                    .show();

            Dataset<Row> groupedDataset = dataset
                    .select(idtSafepayBillItemCol, numOperationValueCol)
                    .groupBy(idtSafepayBillItemCol)
                    .agg(functions.sum(numOperationValueCol)
                            .cast("decimal(20,2)")
                            .as("num_operation_value"));

            long totalGrouped = groupedDataset.count();
            System.out.printf("\nTotal grouped = %d\n", totalGrouped);
            groupedDataset
                    .orderBy(numOperationValueCol.desc())
                    .show((int) totalGrouped);
        }
    }

}