package spark.lab.general;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import spark.lab.samples.models.SomePojo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pojo {

    public static StructType somePojoSchema() {
        return DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.IntegerType, true),
                DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("gender", DataTypes.StringType, true),
                DataTypes.createStructField("document_type", DataTypes.StringType, true),
                DataTypes.createStructField("document_number", DataTypes.StringType, true)}
        );
    }

    public static List<SomePojo> createSomePojoList(int total) {
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
