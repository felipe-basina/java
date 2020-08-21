package alura.spark;

import alura.spark.models.CSVFileContent;
import alura.spark.models.IOrder;
import alura.spark.models.JSONFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Join {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("Join").setMaster("local");
        sc = new JavaSparkContext(conf);

        JavaRDD<IOrder> csvFileContent = csvFileContent();
        JavaRDD<IOrder> siteFileContent = siteFileContent();

        System.out.printf("CSV file = %d\nJSON file = %d\n", csvFileContent.count(), siteFileContent.count());

        JavaRDD<IOrder> joinResponse = csvFileContent.union(siteFileContent);
        System.out.println(joinResponse.count());
        joinResponse.collect().forEach(System.out::println);

        //joinResponse = csvFileContent.intersection(siteFileContent);
        //System.out.println(joinResponse.count());
    }

    public static JavaRDD<IOrder> csvFileContent() {
        return sc.textFile("in/".concat(Files.CONTABILIDADE_CSV_O1.getFileName()))
                .filter(content -> !content.contains("id"))
                .map(CSVFileContent::new);
    }

    public static JavaRDD<IOrder> siteFileContent() {
        return sc.textFile("in/".concat(Files.SITE_JSON.getFileName()))
                .map(JSONFileContent::new);
    }

}
