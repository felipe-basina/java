package com.sample.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Sample03 {

    public static String SPACE_DELIMITER = " ";
    public static String FILE_NAME = "target/classes/words.txt";

    public static void main(String[] args) {
        JavaSparkContext javaSparkContext = SparkContextFactory.createContext(Sample03.class.getSimpleName());

        JavaRDD<String> fileContent = javaSparkContext.textFile(FILE_NAME);
        JavaRDD<String> words = fileContent.flatMap(content -> Arrays.asList(content.split(SPACE_DELIMITER))
                .iterator());
        JavaPairRDD<String, Integer> wordCountPair = words.mapToPair(word -> new Tuple2<>(word, 1));
        JavaPairRDD<String, Integer> wordCountTotal = wordCountPair.reduceByKey(Integer::sum).sortByKey();

        for (Tuple2<String, Integer> wordCount : wordCountTotal.collect()) {
            System.out.printf("%s - occurrences: %d\n", wordCount._1(), wordCount._2());
        }

        SparkContextFactory.closeContext(javaSparkContext);
    }

}
