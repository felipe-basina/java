package com.sample.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.InputStream;
import java.util.Arrays;

public class Sample01 {

    public static String SPACE_DELIMITER = " ";
    public static String FILE_NAME = "target/classes/numbers.txt";

    public static void main(String... args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkFileSumApp");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> input = javaSparkContext.textFile(FILE_NAME);
        inputContent(input);

        JavaRDD<String> numbersAsString = input.flatMap(line -> Arrays.asList(line.split(SPACE_DELIMITER)).iterator());
        JavaRDD<String> validNumbersAsString = numbersAsString.filter(number -> !number.isEmpty());
        JavaRDD<Integer> numbers = validNumbersAsString.map(numberAsString -> Integer.parseInt(numberAsString));

        int finalSum = numbers.reduce((x, y) -> x + y);

        System.out.printf("Sum up = %d\n", finalSum);

        javaSparkContext.close();
    }

    private static void inputContent(JavaRDD<String> input) {
        input.collect().forEach(line -> {
            System.out.printf("Partial sum up: %d\n",
                    Arrays.asList(line.split(SPACE_DELIMITER))
                            .stream()
                            .map(Integer::parseInt)
                            .reduce(0, (x, y) -> x + y));
        });
    }

}
