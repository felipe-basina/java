package spark.lab.streams;

import java.util.concurrent.TimeoutException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQueryException;
import spark.lab.general.InitializeContext;

import static org.apache.spark.sql.functions.lit;

public class RateStream extends InitializeContext {

    private RateStream() {
        super();
    }

    public static void main(String[] args) {
        new RateStream();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = ss.readStream()
                    .format("rate")
                    .option("rowsPerSecond", 1)
                    .load();

            System.out.println("Streaming DataFrame : " + dataset.isStreaming());
            Dataset<Row> result = dataset.withColumn("result",
                    functions.col("value").plus(lit(1)));

            result.writeStream()
                    .outputMode("append")
                    .option("truncate", false)
                    .format("console")
                    .start()
                    .awaitTermination();
        } catch (StreamingQueryException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
