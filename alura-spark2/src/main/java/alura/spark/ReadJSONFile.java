package alura.spark;

import alura.spark.models.JSONFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ReadJSONFile {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("ReadJSONFile").setMaster("local");
        sc = new JavaSparkContext(conf);

        JavaRDD<JSONFileContent> jsonFileContents = sc.textFile("in/".concat(Files.SITE_JSON.getFileName()))
                .map(JSONFileContent::new);

        jsonFileContents.collect().forEach(System.out::println);
        System.out.println("Total : " + jsonFileContents.count());
    }

}
