package alura.spark;

import alura.spark.models.CSVFileContent;
import alura.spark.models.JSONFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

public class Join2 {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("Join2").setMaster("local[4]");
        sc = new JavaSparkContext(conf);

        JavaPairRDD<Integer, CSVFileContent> contentFromCsvFile = JavaPairRDD.fromJavaRDD(readFile("/arq01.txt")
                .map(content -> {
                    CSVFileContent csvFileContent = new CSVFileContent(content);
                    return new Tuple2<>(csvFileContent.getOrderId(), csvFileContent);
                }));
        System.out.println("Total csv file: " + contentFromCsvFile.count());

        JavaPairRDD<Integer, JSONFileContent> contentFromJsonFile = JavaPairRDD.fromJavaRDD(readFile("/arq02.txt")
                .map(content -> {
                    JSONFileContent jsonFileContent = new JSONFileContent(content);
                    return new Tuple2<>(jsonFileContent.getOrderId(), jsonFileContent);
                }));
        System.out.println("Total json file: " + contentFromJsonFile.count());

        System.out.println("\n\n\nJOIN (common values)");

        // JOIN
        JavaPairRDD<Integer, Tuple2<CSVFileContent, JSONFileContent>> joinContent = contentFromCsvFile.join(contentFromJsonFile);
        joinContent.collect().forEach(System.out::println);
        System.out.println(joinContent.count());

        System.out.println("\n\n\nLEFT JOIN");

        // LEFT JOIN
        JavaPairRDD<Integer, Tuple2<CSVFileContent, Optional<JSONFileContent>>> leftJoinContent = contentFromCsvFile
                .leftOuterJoin(contentFromJsonFile);
        leftJoinContent.collect().forEach(System.out::println);
        System.out.println(leftJoinContent.count());

        System.out.println("\nLEFT JOIN (only CSV values)");

        // LEFT JOIN <ONLY CSV FILE CONTENT>
        JavaPairRDD<Integer, Tuple2<CSVFileContent, Optional<JSONFileContent>>> onlyCsvContent = leftJoinContent
                .filter(new Function<Tuple2<Integer, Tuple2<CSVFileContent, Optional<JSONFileContent>>>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Integer, Tuple2<CSVFileContent, Optional<JSONFileContent>>> tuple) throws Exception {
                        return !tuple._2()._2().isPresent();
                    }
                });
        onlyCsvContent.collect().forEach(System.out::println);
        System.out.println(onlyCsvContent.count());

        System.out.println("\n\n\nRIGHT JOIN");

        // RIGHT JOIN
        JavaPairRDD<Integer, Tuple2<Optional<CSVFileContent>, JSONFileContent>> rightJoinContent = contentFromCsvFile
                .rightOuterJoin(contentFromJsonFile);
        rightJoinContent.collect().forEach(System.out::println);
        System.out.println(rightJoinContent.count());

        System.out.println("\nRIGHT JOIN (only JSON values)");

        // RIGHT JOIN <ONLY JSON FILE CONTENT>
        JavaPairRDD<Integer, Tuple2<Optional<CSVFileContent>, JSONFileContent>> onlyJsonContent = rightJoinContent
                .filter(new Function<Tuple2<Integer, Tuple2<Optional<CSVFileContent>, JSONFileContent>>, Boolean>() {
                    @Override
                    public Boolean call(Tuple2<Integer, Tuple2<Optional<CSVFileContent>, JSONFileContent>> tuple) throws Exception {
                        return !tuple._2()._1().isPresent();
                    }
                });
        onlyJsonContent.collect().forEach(System.out::println);
        System.out.println(onlyJsonContent.count());
    }

    private static JavaRDD<String> readFile(final String fileName) {
        return sc.textFile("in".concat(fileName));
    }

}
