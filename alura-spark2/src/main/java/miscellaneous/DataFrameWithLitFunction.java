package miscellaneous;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import scala.Tuple2;

import java.io.Serializable;

public class DataFrameWithLitFunction {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("DataFrameWithLitFunction").setMaster("local[*]");
        sc = new JavaSparkContext(conf);

        // Read file
        JavaRDD<String> fileContent = sc.textFile("in/".concat("tuple-data.txt"));

        // Creates a tuple
        JavaPairRDD<String, CustomObj> contentAsTuple = JavaPairRDD.fromJavaRDD(fileContent.map(content -> {
            CustomObj co = new CustomObj(content);
            return new Tuple2<>(co.id, co);
        }));
        System.out.println("Total de registros: " + contentAsTuple.count());

        // Group by key
        JavaPairRDD<String, Iterable<CustomObj>> groupedValues = contentAsTuple.groupByKey();

        for (Tuple2<String, Iterable<CustomObj>> t : groupedValues.collect()) {
            System.out.println("id = " + t._1);
            for (CustomObj co : t._2) {
                System.out.println(co);
            }
        }

        // Transforms to DataFrame
        JavaRDD<Row> rowJavaRDD = contentAsTuple.map(tuple -> RowFactory.create(tuple._1(), tuple._2()));
        for (Row r : rowJavaRDD.collect()) {
            System.out.println(r);
            String id = r.get(0).toString();
            CustomObj co = (CustomObj) r.get(1);
            System.out.println(id);
            System.out.println(co);
        }
    }

}

class CustomObj implements Serializable {

    public String id;
    public int value;

    public CustomObj(String content) {
        String[] split = content.split(",");
        this.id = split[0].trim();
        this.value = Integer.valueOf(split[1].trim());
    }

    @Override
    public String toString() {
        return "CustomObj{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
