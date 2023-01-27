package spark.lab.streams;

import java.util.concurrent.TimeoutException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQueryException;
import spark.lab.general.InitializeContext;

public class SocketStream extends InitializeContext {

    private SocketStream() {
        super();
    }

    public static void main(String[] args) {
        new SocketStream();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> dataset = ss.readStream()
                    .format("socket")
                    .option("host", "127.0.0.1")
                    .option("port", "9999")
                    .load();

            System.out.println("Streaming DataFrame : " + dataset.isStreaming());
            Dataset<Row> result = dataset.select(
                    functions.explode(functions.split(
                            functions.col("value"), " "
                    )).alias("words")
            )
                    .groupBy(functions.col("words"))
                    .count();

            System.out.println(":: Open terminal and type:");
            System.out.println(":: nc -lk 9999"); // nc localhost 9999 to consume from terminal
            System.out.println(":: And then...");
            System.out.println(":: London Paris Tokyo New_York");
            System.out.println(":: Mumbai Ohio Delhi London");
            System.out.println(":: Delhi London Tokyo Paris\n");

            System.out.println("Schema of Dataframe count.");
            result.printSchema();

            result.writeStream()
                    .outputMode("complete") // update: only records that are updated in a particular batch are output to the console
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
