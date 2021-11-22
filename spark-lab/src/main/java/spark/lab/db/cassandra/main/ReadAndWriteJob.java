package spark.lab.db.cassandra.main;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import spark.lab.db.cassandra.models.User;

import java.util.Arrays;
import java.util.Random;

public class ReadAndWriteJob {

    private static Logger LOGGER = Logger.getLogger(ReadAndWriteJob.class);

    private static final String KEY_SPACE = "user_management";
    private static final String TABLE = "users_by_name";

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        LOGGER.getLogger("org").setLevel(Level.ERROR);
        LOGGER.getLogger("com.datastax").setLevel(Level.ERROR);

        SparkConf conf = new SparkConf();
        conf.setAppName("Java API demo");
        conf.setMaster("local[*]");
        conf.set("spark.cassandra.connection.host", "127.0.0.1");
        sc = new JavaSparkContext(conf);

        SparkSession ss = SparkSession.builder().getOrCreate();

        printUsersByName(ss);
        addUser(ss, new User(
                "Doe",
                "Joe",
                String.format("joe.doe%d@domain.com", new Random().nextInt(1000))));
        printUsersByName(ss);

        sc.close();
    }

    private static void addUser(SparkSession ss, User newUser) {
        Dataset<User> newUserDataset = ss.createDataset(Arrays.asList(newUser), User.encoder());

        newUserDataset
                .withColumnRenamed("firstName", "first_name")
                .withColumnRenamed("lastName", "last_name")
                .write()
                .mode("append")
                .format("org.apache.spark.sql.cassandra")
                .option("keyspace", KEY_SPACE)
                .option("table", TABLE)
                .save();
    }

    private static void printUsersByName(SparkSession ss) {
        Dataset<User> userDataset = getUsersByName(ss);
        System.out.println("Total registers fetched = " + userDataset.count());

        userDataset
                .orderBy(org.apache.spark.sql.functions.col("first_name"))
                .show();

        JavaRDD<User> userJavaRDD = userDataset
                .javaRDD();
        userJavaRDD.collect().forEach(System.out::println);
    }

    private static Dataset<User> getUsersByName(SparkSession ss) {
        return ss.read()
                .format("org.apache.spark.sql.cassandra")
                .option("keyspace", KEY_SPACE)
                .option("table", TABLE)
                .load()
                .select(org.apache.spark.sql.functions.col("first_name").as("firstName"),
                        org.apache.spark.sql.functions.col("last_name").as("lastName"),
                        org.apache.spark.sql.functions.col("email"))
                .as(User.encoder());
    }

}
