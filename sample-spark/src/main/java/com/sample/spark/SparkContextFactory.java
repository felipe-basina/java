package com.sample.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Objects;

public class SparkContextFactory {

    public static JavaSparkContext createContext(final String appName) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName(appName);
        return new JavaSparkContext(sparkConf);
    }

    public static void closeContext(JavaSparkContext context) {
        if (Objects.isNull(context)) {
            throw new IllegalArgumentException("Context must not be null!");
        }
        context.close();
    }

}
