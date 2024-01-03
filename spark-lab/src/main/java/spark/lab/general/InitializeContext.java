package spark.lab.general;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.storage.StorageLevel;

public abstract class InitializeContext {

    protected static Logger LOGGER = Logger.getLogger(InitializeContext.class);

    protected static SparkConf conf;

    protected static final String SEMICOLON_DELIMITER = ";";

    /**
     * SparkUI http://localhost:4050/jobs/
     */
    public InitializeContext() {
        conf = new SparkConf()
                .setAppName("Java API demo")
                .setMaster("local[*]")
                .set("spark.driver.bindAddress", "localhost")
                .set("spark.ui.port", "4050")
                .set("spark.cassandra.connection.host", "127.0.0.1");
    }

    protected static SparkSession.Builder sparkSessionBuilder() {
        return SparkSession.builder().config(conf);
    }

    protected static Dataset<Row> readCSVFiles(SparkSession ss, String filePath, String delimiter) {
        return ss.read()
                .option("delimiter", delimiter)
                .option("header", true)
                .csv(filePath);
    }

    protected static Dataset<Row> readParquetFiles(SparkSession ss, String filePath) {
        return ss.read().parquet(filePath);
    }

    protected static Dataset<Row> getJoinedDataset(
            Dataset<Row> leftDataset,
            Dataset<Row> rightDataset,
            String leftDatasetColumnName,
            String rightDatasetColumnName,
            String join
    ) {
        Dataset<Row> joinedDataset = leftDataset.join(rightDataset, rightDataset.col(rightDatasetColumnName)
                        .equalTo(leftDataset.col(leftDatasetColumnName)), join.toLowerCase())
                .persist(StorageLevel.MEMORY_ONLY());
        System.out.println("Total (" + join + "Join): " + joinedDataset.count());
        return joinedDataset;
    }

    protected static void writeContentIntoCsvFile(
            Dataset<Row> dataset, String path, String delimiter, Boolean useHeader
    ) {
        dataset.repartition(1)
                .write()
                .option("delimiter", delimiter)
                .option("header", useHeader)
                .mode(SaveMode.Overwrite)
                .csv(path);
    }

    protected static void writeContentIntoTextFile(Dataset<Row> dataset, String path) {
        dataset.repartition(1)
                .write()
                .option("header", true)
                .text(path);
    }

    protected static void showCountGroupedBy(Dataset<Row> dataset, Column column) {
        System.out.println("Count by " + column.logName());
        dataset.select(column)
                .groupBy(column)
                .agg(functions.count(column))
                .orderBy(column.asc())
                .show();
    }

    protected static void showSparkUI() {
        System.out.println("SparkUI available on http://localhost:4050/");
        while (true) {}
    }

}
