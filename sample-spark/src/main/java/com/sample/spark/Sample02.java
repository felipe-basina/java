package com.sample.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import javax.print.attribute.IntegerSyntax;
import java.util.Arrays;
import java.util.List;

public class Sample02 {

    public static void main(String[] args) {
        final int counter = 0;

        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Sample02");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> rdd = javaSparkContext.parallelize(data);
        int total = rdd.reduce(Integer::sum);
        System.out.println(total);

        //rdd.foreach(x -> counter += x);
    }

}
