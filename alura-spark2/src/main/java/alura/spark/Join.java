package alura.spark;

import alura.spark.models.CSVFileContent;
import alura.spark.models.IOrder;
import alura.spark.models.JSONFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class Join {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("Join").setMaster("local[4]");
        sc = new JavaSparkContext(conf);

        JavaPairRDD<Integer, CSVFileContent> csvFileContent1 = csvFileContent(Files.CONTABILIDADE_CSV_O1.getFileName());
        JavaPairRDD<Integer, CSVFileContent> csvFileContent2 = csvFileContent(Files.CONTABILIDADE_CSV_O2.getFileName());
        JavaPairRDD<Integer, CSVFileContent> csvFileContent = csvFileContent1.union(csvFileContent2);
        System.out.printf("CSV file 1 = %d\nCSV file 2 = %d\nCSV union = %d\n",
                csvFileContent1.count(),
                csvFileContent2.count(),
                csvFileContent.count());

        JavaPairRDD<Integer, JSONFileContent> siteFileContent = siteFileContent();
        System.out.printf("JSON file = %d\n", siteFileContent.count());

        JavaPairRDD<Integer, Tuple2<CSVFileContent, JSONFileContent>> joinResponse = csvFileContent
                .join(siteFileContent)
                .distinct();
        for (Tuple2<Integer, Tuple2<CSVFileContent, JSONFileContent>> joined: joinResponse.collect()) {
            System.out.println("ID = " + joined._1());
            Tuple2<CSVFileContent, JSONFileContent> joinedContent = joined._2();
            System.out.printf("CSV file content = %s\nJSON file content = %s\n",
                    joinedContent._1(),
                    joinedContent._2());
        }
        System.out.println("\n\nTotal distincts: " + joinResponse.count());

        //joinResponse.collect().forEach(System.out::println);

//        joinResponse = csvFileContent.intersection(siteFileContent);
//        System.out.println(joinResponse.count());
    }

//    public static JavaRDD<IOrder> csvFileContent() {
//        return sc.textFile("in/".concat(Files.CONTABILIDADE_CSV_O1.getFileName()))
//                .filter(content -> !content.contains("id"))
//                .map(CSVFileContent::new);
//    }

    public static JavaPairRDD<Integer, CSVFileContent> csvFileContent(final String csvFileName) {
        return JavaPairRDD.fromJavaRDD(sc.textFile("in/".concat(csvFileName))
                .filter(Join::validCsvContent)
                .map(content -> {
                    CSVFileContent csvFileContent = new CSVFileContent(content);
                    return new Tuple2<>(csvFileContent.getOrderId(), csvFileContent);
                }));
    }

    private static boolean validCsvContent(final String csvFileContent) {
        return !csvFileContent.contains("id") && !"05:00".equals(csvFileContent);
    }

//    public static JavaRDD<IOrder> siteFileContent() {
//        return sc.textFile("in/".concat(Files.SITE_JSON.getFileName()))
//                .map(JSONFileContent::new);
//    }

    public static JavaPairRDD<Integer, JSONFileContent> siteFileContent() {
        return JavaPairRDD.fromJavaRDD(sc.textFile("in/".concat(Files.SITE_JSON.getFileName()))
                .map(content -> {
                    JSONFileContent jsonFileContent = new JSONFileContent(content);
                    return new Tuple2<>(jsonFileContent.getOrderId(), jsonFileContent);
                }));
    }

}
