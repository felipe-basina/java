package spark.lab.samples;

import java.util.Arrays;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import spark.lab.general.InitializeContext;

import static org.apache.spark.sql.types.Metadata.empty;

public class DataManipulation extends InitializeContext {

    public DataManipulation() {
        super();
    }

    public static void main(String[] args) {
        new DataManipulation();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Integer> dataset = ss.createDataset(Arrays.asList(1, 20, 3, 40, 50, 6, 7), Encoders.INT());
            Dataset<Row> formattedDataset = dataset
                    .select(
                            functions.format_string("%09d", functions.col("value")).as("formatted_val")
                            /*functions.when(
                                            functions.length(functions.col("value")).geq(2),
                                            functions.format_string("%07d", functions.col("value"))
                                    )
                                    .otherwise(functions.format_string("%08d", functions.col("value")))*/
                    );
            writeContentIntoTextFile(formattedDataset, "/home/afsimao/Downloads/file.txt");

            StructType schema = new StructType(new StructField[]{
                    new StructField("OPERATION_TYPE", DataTypes.IntegerType, true, empty())
            });
            //ss.createDataFrame(Arrays.asList(1,2,3,4,5,6,7), schema);
        }
    }

}