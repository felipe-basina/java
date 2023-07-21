package spark.lab.samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.*;
import spark.lab.general.InitializeContext;
import spark.lab.general.Pojo;
import spark.lab.samples.models.SomePojo;

import java.util.ArrayList;
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
                    )
                    //.repartition(3)
                    ;
            originalDataset.show();

            Dataset<Row> withOnlyOddIdDataset = originalDataset.filter(
                    functions.col("id").mod(2).$greater(0)
                    //functions.col("documentType").equalTo(functions.lit("CNPJ"))
            );
            withOnlyOddIdDataset.show();

            Dataset<Row> deltaDataset = originalDataset
                    .join(withOnlyOddIdDataset,
                            originalDataset.col("id").equalTo(withOnlyOddIdDataset.col("id")),
                            org.apache.spark.sql.catalyst.plans.LeftAnti.productPrefix());
            deltaDataset.show();

            Dataset<SomePojo> mapPartitionsDS = originalDataset
                    .mapPartitions((MapPartitionsFunction<Row, SomePojo>) itr -> {
                        List<SomePojo> finalList = new ArrayList<>();

                        ObjectMapper om = new ObjectMapper();
                        int count = 0;
                        while (itr.hasNext()) {
                            Row row = itr.next();
//                            if (count == 0) {
//                                System.out.println(row.schema().toString());
//                            }
                            SomePojo sp = om.readValue(row.json(), SomePojo.class);
                            sp.setName(sp.getName() + ":::" + sp.getId());
                            finalList.add(sp);
                            count++;
                        }
                        System.out.println("Total count = " + count);

                        return finalList.iterator();

                    }, Encoders.bean(SomePojo.class));
            mapPartitionsDS.show();
        }
    }

}