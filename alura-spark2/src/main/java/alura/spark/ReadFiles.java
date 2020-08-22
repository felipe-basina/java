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

    // https://github.com/felipe-basina/sparkTutorial/blob/master/src/main/java/com/sparkTutorial/sparkSql/RddDatasetConversion.java
    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        conf = new SparkConf().setAppName("ReadFiles").setMaster("local");
        sc = new JavaSparkContext(conf);

        Stream.of(Files.values()).forEach(file -> {
            JavaRDD<String> data = readFile(file.getFileName());
            System.out.println(String.format("File: %s | Total: %d | Exemplos: %s",
                    file.getFileName(), data.count(), data.take(25)));
        });

//        JavaRDD<String> dataContabilidade01 = readFile(Files.CONTABILIDADE_CSV_O1.getFileName());
//        JavaRDD<String> dataContabilidade02 = readFile(Files.CONTABILIDADE_CSV_O2.getFileName());
//        JavaRDD<String> dataContabilidade = dataContabilidade01.union(dataContabilidade02);
//        System.out.println("Total contabilidade: " + dataContabilidade.count());
        readFile(Files.SITE_JSON.getFileName()).take(8).forEach(System.out::println);
    }

    private static JavaRDD<String> readFile(final String file) {
        return sc.textFile("in/".concat(file));
    }

}
