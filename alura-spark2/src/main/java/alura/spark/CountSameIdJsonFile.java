package alura.spark;

import alura.spark.models.JSONFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Comparator;
import java.util.stream.StreamSupport;

public class CountSameIdJsonFile {

    private static SparkConf conf;
    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("CountSameIdJsonFile").setMaster("local[4]");
        sc = new JavaSparkContext(conf);

        JavaRDD<String> fileContent = sc.textFile("in/".concat(Files.SITE_JSON.getFileName()));
        JavaPairRDD<Integer, JSONFileContent> contentAsTuple = JavaPairRDD.fromJavaRDD(fileContent.map(content -> {
            JSONFileContent jsonFileContent = new JSONFileContent(content);
            return new Tuple2<>(jsonFileContent.getOrderId(), jsonFileContent);
        }));
        System.out.println(contentAsTuple.count());

        JavaPairRDD<Integer, Iterable<JSONFileContent>> groupedValues = contentAsTuple.groupByKey();
        System.out.println(groupedValues.count());

        JavaPairRDD<Integer, Iterable<JSONFileContent>> justOne = groupedValues
                .filter(groupedValue -> StreamSupport.stream(groupedValue._2().spliterator(),
                false).count() == 1);
        System.out.println(justOne.count());

        groupedValues = groupedValues.filter(groupedValue -> StreamSupport.stream(groupedValue._2().spliterator(),
                false).count() > 1);
        System.out.println(groupedValues.count());

//        JavaPairRDD<Iterable<JSONFileContent>, Integer> inverse = JavaPairRDD.fromJavaRDD(groupedValues.map(groupValue -> {
//            return new Tuple2<Iterable<JSONFileContent>, Integer>(groupValue._2(), groupValue._1());
//        }));
//
//        inverse = inverse.sortByKey(new Comparator<Iterable<JSONFileContent>>() {
//            @Override
//            public int compare(Iterable<JSONFileContent> o1, Iterable<JSONFileContent> o2) {
//                return (int) (size(o2) - size(o1));
//            }
//        });
//        inverse.collect().forEach(System.out::println);

        for (Tuple2<Integer, Iterable<JSONFileContent>> groupValues : groupedValues.collect()) {
            System.out.printf("ID = %s, TOTAL = %d\n",
                    groupValues._1(),
                    StreamSupport.stream(groupValues._2().spliterator(),
                            false).count());
        }
    }

    private static long size(Iterable<JSONFileContent> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }

}
