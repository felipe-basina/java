package spark.lab.samples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import spark.lab.general.InitializeContext;
import spark.lab.general.Pojo;
import spark.lab.samples.models.SomePojo;

import java.util.List;

public class DifferenceBetweenDataset extends InitializeContext {

    public DifferenceBetweenDataset() {
        super();
    }

    public static void main(String[] args) {
        new DifferenceBetweenDataset();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            List<SomePojo> somePojos = Pojo.createSomePojoList(9);

            Dataset<Row> originalDataset = ss.createDataFrame(somePojos, SomePojo.class)
                    .select(
                            functions.col("id"),
                            functions.col("name"),
                            functions.col("documentType"),
                            functions.col("documentNumber"),
                            functions.col("gender")
                    );
            originalDataset.show();

            Dataset<Row> withOnlyOddIdDataset = originalDataset.filter(
                    functions.col("id").mod(2).$greater(0)
                    //functions.col("documentType").equalTo(functions.lit("CNPJ"))
            );
            withOnlyOddIdDataset.show();

            Dataset<Row> deltaDataset = originalDataset
                    .join(withOnlyOddIdDataset,
                            originalDataset.col("id").equalTo(withOnlyOddIdDataset.col("id")),
                            "LEFTANTI");
            deltaDataset.show();
        }
    }

}