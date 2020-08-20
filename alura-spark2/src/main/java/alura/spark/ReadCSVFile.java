package alura.spark;

import alura.spark.models.CSVFileContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ReadCSVFile {

    private static SparkConf conf;

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        conf = new SparkConf().setAppName("ReadCSVFile").setMaster("local[2]");
        sc = new JavaSparkContext(conf);

        JavaRDD<CSVFileContent> csvFileContents = sc.textFile("in/".concat(Files.CONTABILIDADE_CSV_O1.getFileName()))
                .filter(content -> !content.contains("id"))
                .map(CSVFileContent::new);

        System.out.println("Total : " + csvFileContents.count());
    }

}
