package alura.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.stream.Stream;

public class ReadFiles {

    private static SparkConf conf;
    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        conf = new SparkConf().setAppName("ReadFiles").setMaster("local");
        sc = new JavaSparkContext(conf);

        Stream.of(Files.values()).forEach(file -> {
            JavaRDD<String> data = readFile(file.getFileName());
            System.out.println(String.format("File: %s | Total: %d", file.getFileName(), data.count()));
        });
    }

    private static JavaRDD<String> readFile(final String file) {
        return sc.textFile("in/".concat(file));
    }

}
