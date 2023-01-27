package spark.lab.streams;

import java.util.concurrent.TimeoutException;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;
import spark.lab.general.InitializeContext;

import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.StringType;

public class FileStream extends InitializeContext {

    private FileStream() {
        super();
    }

    public static void main(String[] args) {
        new FileStream();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            StructType schema = new StructType()
                    .add("Date", StringType, true)
                    .add("Open", DoubleType, true)
                    .add("High", DoubleType, true)
                    .add("Low", DoubleType, true)
                    .add("Close", DoubleType, true)
                    .add("Adjusted Close", DoubleType, true)
                    .add("Volume", DoubleType, true);

            Dataset<Row> dataset = ss.readStream()
                    .format("csv")
                    .option("maxFilesPerTrigger", 2) // This will read maximum of 2 files per mini batch. However, it can read less than 2 files.
                    .option("header", true)
                    .option("path", "data/stream")
                    .schema(schema)
                    .load()
                    .withColumn("Name", getFileName());

            Dataset<Row> result = dataset.groupBy(
                    functions.col("Name"),
                    functions.year(functions.col("Date")).as("Year")
            ).agg(functions.max("High").as("Max"));

            result.writeStream()
                    .outputMode("update") // Try "update" and "complete" mode.
                    .option("truncate", false)
                    .option("numRows", 3)
                    .format("console")
                    .start()
                    .awaitTermination();
        } catch (StreamingQueryException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static Column getFileName() {
        Column fileNameCol = functions.reverse(
                        functions.split(functions.input_file_name(), "/")
                )
                .getItem(0);
        return functions.split(fileNameCol, "_").getItem(0);
    }
}
