package spark.lab.ps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import spark.lab.general.InitializeContext;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;

public class AnaliseRecordsTransporter20220807 extends InitializeContext {

    private static final String SOURCE_FILE_NAME = "/home/afsimao/Downloads/records-transporter-2022-08-07/";
    private static final String SOURCE_CSV_FILE_NAME = "/home/afsimao/Downloads/rleoneti_movimetacao_nao_transportada_202212151703.csv";

    private static Column idtSafepayBillItemCol = col("idt_safepay_bill_item");
    private static Column numOperationValueCol = col("num_operation_value");
    private static Column idtTurnover = col("idt_turnover");
    private static Column numType = col("num_type");
    private static Column desAppOrigin = col("des_app_origin");

    public AnaliseRecordsTransporter20220807() {
        super();
    }

    /**
     * Colunas disponíveis
     * |idt_sap_batch|dat_processed|num_hour|idt_safepay_bill_item|idt_turnover|flg_use_real_table|num_type|dat_creation|des_product_origin| num_operation_value|des_app_origin|
     */
    public static void main(String[] args) {
        new AnaliseRecordsTransporter20220807();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> datasetCsv = readCSVFiles(ss, SOURCE_CSV_FILE_NAME, SEMICOLON_DELIMITER);
            datasetCsv = datasetCsv
                    .filter(col("dat_created_at").cast("String").contains("2022-08-07"));

            // Realiza a leitura de múltipos diretórios com arquivos parquet
            // Para que os dataset possam ser unidos é necessário que possuam o mesmo schema (tipo de dados, número e nome de colunas)
            // e as colunas precisam estar na mesma ordem
            Dataset<Row> dataset = Arrays.stream(RecordType.values())
                    .map(recordType -> {
                        String path = SOURCE_FILE_NAME.concat(recordType.name());
                        Dataset<Row> innerDataset = readParquetFiles(ss, path);
                        if (RecordType.ASSEMBLER != recordType) {
                            innerDataset = innerDataset
                                    .drop("des_app_origin")
                                    .withColumn("num_operation_value", lit(0))
                                    .withColumn("des_app_origin", lit(recordType.name()));
                        }
                        System.out.println("Type: " + recordType + ", sample: ");
                        innerDataset.show(3);
                        return innerDataset;
                    })
                    .reduce(Dataset::union)
                    .get();
            System.out.println("Total registros enviados para DataX: " + dataset.count());
            showCountGroupedBy(dataset, desAppOrigin);
            showCountGroupedBy(dataset, numType);

            Dataset<Row> rightJoined = getJoinedDataset(
                    dataset, datasetCsv, "idt_turnover", "idt_id","right"
            );
            showJoinedContentThroughAggregationFunctions(rightJoined);
            writeContentIntoCsvFile(rightJoined, "/home/afsimao/Downloads/analise1.csv", SEMICOLON_DELIMITER, true);

//            Dataset<Row> innerJoined = getJoinedDataset(
//                    dataset, datasetCsv, "idt_turnover", "idt_id", "inner"
//            );
//            showJoinedContentThroughAggregationFunctions(innerJoined);

//            Dataset<Row> leftJoined = getJoinedDataset(
//                    dataset, datasetCsv, "idt_turnover", "idt_id", "left"
//            );
//            showJoinedContentThroughAggregationFunctions(leftJoined);
        }
    }

    private static void showDateValues(Dataset<Row> datasetCsv) {
        datasetCsv.select(
                functions.to_date(col("dat_created_at")),
                functions.to_timestamp(col("dat_created_at")),
                functions.trunc(functions.to_timestamp(col("dat_created_at")), "yyyy-MM-dd"),
                functions.trunc(functions.to_date(col("dat_created_at")), "yyyy-MM-dd")
        ).show();
    }

    private static void showSampleDataByIdTurnover(Dataset<Row> datasetCsv) {
        Dataset<Row> idtsTurnover = datasetCsv.select(
                col("idt_id").as("idt_turnover")
        );
        System.out.println("Total de idtsTurnover: " + idtsTurnover.count());
        List<Row> idtTurnoverRows = idtsTurnover.select("idt_turnover").collectAsList();
        List<String> ids = new ArrayList<>();
        idtTurnoverRows.forEach(row -> ids.add(row.getAs("idt_turnover")));
        List<String> first10 = new ArrayList<>(ids.subList(0, 10));
        Dataset<Row> sampleFiltered = datasetCsv.filter((FilterFunction<Row>) r -> first10
                .contains(r.getAs("idt_turnover")));
        sampleFiltered.show();
    }

    private static void showJoinedContentThroughAggregationFunctions(Dataset<Row> dataset) {
        dataset.select(
                        col("dat_created_at"),
                        col("idt_turnover"),
                        col("num_amount"))
                .agg(functions.first(functions.to_date(col("dat_created_at"))),
                        functions.count(col("idt_turnover")),
                        functions.sum(col("num_amount"))
                                .divide(100).as("num_operation_value"))
                .show();
    }

}

enum RecordType {
    ASSEMBLER,
    PROCESSOR,
    PAYWARE,
    RECORDS;
}