package spark.lab.kafka;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;
import spark.lab.general.InitializeContext;

import java.util.concurrent.TimeoutException;

import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.StringType;

public class SampleStream extends InitializeContext {

    private SampleStream() {
        super();
    }

    public static void main(String[] args) {
        new SampleStream();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = ss.readStream()
                    .format("kafka")
                    .option("kafka.bootstrap.servers", "localhost:9092")
                    .option("subscribe", "json_topic")
                    .option("startingOffsets", "earliest") // From starting | earliest - latest
                    .load()
                    .selectExpr("CAST(value AS STRING)");

            StructType schema = new StructType()
                    .add("id", IntegerType)
                    .add("firstname", StringType)
                    .add("middlename", StringType)
                    .add("lastname", StringType)
//                    .add("dob_year",IntegerType)
                    .add("dob_year", StringType)
                    .add("dob_month", StringType)
                    .add("gender", StringType)
                    .add("salary", IntegerType);

            Dataset<Row> rowDataset = dataset
                    .select(functions.from_json(functions.col("value"), schema).as("data"))
                    .select("data.*")
                    .withColumn("DESC_GENDER",
                            functions.when(functions.col("gender").equalTo("M"), "MALE")
                                    .otherwise("FEMALE")
                    );

            rowDataset.writeStream()
                    .format("console")
                    .outputMode("append")
                    .option("numRows", 1000)
                    /*.format("csv")
                    .option("checkpointLocation", "checkpoint")
                    .option("delimiter", ";")
                    .option("header", true)
                    .option("path", "/home/afsimao/Downloads/analise1.csv")*/
                    .start()
                    .awaitTermination(10 * 1000);
        } catch (StreamingQueryException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
