package spark.lab.db.cassandra.main;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import spark.lab.db.cassandra.models.User;
import spark.lab.general.InitializeContext;

import java.util.Arrays;
import java.util.Random;

public class ReadAndWriteJob extends InitializeContext {

    private static final String KEY_SPACE = "user_management";
    private static final String TABLE = "users_by_name";

    public ReadAndWriteJob() {
        super();
    }

    public static void main(String[] args) {
        new ReadAndWriteJob();

        try (SparkSession ss = SparkSession.builder().config(conf).getOrCreate()) {
            printUsersByName(ss);
            addUser(ss, new User(
                    "Doe",
                    "Joe",
                    String.format("joe.doe%d@domain.com", new Random().nextInt(1000))));
            printUsersByName(ss);
        }
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

        JavaRDD<User> userJavaRDD = userDataset.javaRDD();
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
