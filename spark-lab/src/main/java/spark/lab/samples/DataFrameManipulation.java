package spark.lab.samples;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import spark.lab.general.InitializeContext;
import spark.lab.samples.models.SomePojo;

public class DataFrameManipulation extends InitializeContext {

    public DataFrameManipulation() {
        super();
    }

    public static void main(String[] args) {
        new DataFrameManipulation();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            StructType somePojoSchema = somePojoSchema();
            List<SomePojo> somePojos = createSomePojoList(9);

            Dataset<Row> dataFrame = ss.createDataFrame(somePojos, SomePojo.class);
            /*Dataset<Row> s = dataFrame.select(
                    functions.regexp_replace(
                        functions.format_string("%20s", functions.col("name")),
                            " ",
                            " "
                    ).as("name"),
                    functions.regexp_replace(
                            functions.format_string("%16s", functions.col("id")),
                            " ",
                            "0"
                    ).as("id")
            );
            s.show();
            writeContentIntoCsvFile(s, "/home/afsimao/Downloads/pojoAsFile.csv", "\t");*/

            Dataset<Row> modifiedDataset = dataFrame.select(
                    functions.format_string("%20s", functions.col("name")).as("name"),
                    functions.regexp_replace(
                            functions.format_string("%16s", functions.col("id")),
                            " ",
                            "0"
                    ).as("id"),
                    functions.regexp_replace(
                            functions.format_string("%24s", functions.col("documentNumber")),
                            " ",
                            "0"
                    ).as("documentNumber"),
                    functions.when(
                            functions.col("gender").equalTo("M"),
                            "MASCULINO"
                    ).otherwise("FEMININO").as("gender"),
                    functions.when(
                            functions.col("documentType").equalTo("CNPJ"),
                            functions.format_string("%20s", functions.col("documentType"))
                    ).otherwise(functions.format_string("%2s", functions.col("documentType"))).as("documentType"),
                    functions.when(
                            functions.col("documentType").equalTo("CNPJ"),
                            functions.lit("pointless")
                    ).otherwise("NO SENSE").as("DESCRIPTION"));

            for (String column : modifiedDataset.columns()) {
                modifiedDataset = modifiedDataset.withColumnRenamed(column, column.concat("\t"));
            }
            modifiedDataset.show(false);

            /*writeContentIntoCsvFile(
                    modifiedDataset, "/home/afsimao/Downloads/pojoAsFile.csv", "\t", false
            );*/
        }
    }

    private static StructType somePojoSchema() {
        return DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.IntegerType, true),
                DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("gender", DataTypes.StringType, true),
                DataTypes.createStructField("document_type", DataTypes.StringType, true),
                DataTypes.createStructField("document_number", DataTypes.StringType, true)}
        );
    }

    private static List<SomePojo> createSomePojoList(int total) {
        return IntStream.rangeClosed(1, total)
                .mapToObj(index -> new SomePojo(
                        index,
                        "name_" + index,
                        (index % 2 == 0) ? "F" : "M",
                        (index % 2 == 0) ? "CPF" : "CNPJ",
                        (index % 2 == 0) ? "7281940203" + index : "1844328400015" + index))
                .collect(Collectors.toList());
    }

}