package spark.lab.db.cassandra.main;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import spark.lab.db.cassandra.models.User;
import spark.lab.general.InitializeContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static spark.lab.general.util.GeneralConstants.SPARK_SQL_CASSANDRA_FORMAT;

public class ReadAndWriteJob extends InitializeContext {

    private static final String KEY_SPACE = "user_management";
    private static final String TABLE = "users_by_name";

    public ReadAndWriteJob() {
        super();
    }

    public static void main(String[] args) {
        new ReadAndWriteJob();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            /*printUsersByName(ss);
            addUser(ss, new User(
                    "Doe",
                    "Joe",
                    String.format("joe.doe%d@domain.com", new Random().nextInt(1000))));
            printUsersByName(ss);
            printTotalGrouped(ss);*/
            generateUserParquetFile(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addUser(SparkSession ss, User newUser) {
        Dataset<User> newUserDataset = ss.createDataset(Arrays.asList(newUser), User.encoder());
        newUserDataset
                .withColumnRenamed("firstName", "first_name")
                .withColumnRenamed("lastName", "last_name")
                .write()
                .mode("append")
                .format(SPARK_SQL_CASSANDRA_FORMAT)
                .option("keyspace", KEY_SPACE)
                .option("table", TABLE)
                .save();
    }

    private static void printUsersByName(SparkSession ss) {
        Dataset<User> userDataset = getUsersByName(ss);
        LOGGER.info("Total registers fetched = " + userDataset.count());

        userDataset
                .orderBy(org.apache.spark.sql.functions.col("first_name"))
                .show();

        JavaRDD<User> userJavaRDD = userDataset.javaRDD()
                .sortBy(User::getFirstName, Boolean.TRUE, 0);
        userJavaRDD.collect().forEach(LOGGER::info);
    }

    private static Dataset<User> getUsersByName(SparkSession ss) {
        return ss.read()
                .format(SPARK_SQL_CASSANDRA_FORMAT)
                .option("keyspace", KEY_SPACE)
                .option("table", TABLE)
                .load()
                .select(org.apache.spark.sql.functions.col("first_name").as("firstName"),
                        org.apache.spark.sql.functions.col("last_name").as("lastName"),
                        org.apache.spark.sql.functions.col("email"))
                .as(User.encoder());
    }

    private static void printTotalGrouped(SparkSession ss) {
        Dataset<User> userDataset = getUsersByName(ss);
        userDataset.groupBy(org.apache.spark.sql.functions.col("lastName"))
                .count()
                .sort(org.apache.spark.sql.functions.desc("count"),
                        org.apache.spark.sql.functions.asc("lastName"))
                .filter((FilterFunction<Row>) value -> ((Long) value.getAs("count")) > 1)
                .show();
    }

    private static void generateUserParquetFile(SparkSession ss) throws IOException {
        final String filePath = "/opt/dev/files";
        Dataset<User> userDataset = getUsersByName(ss);
        LOGGER.info("Total users from database = " + userDataset.count());

        // Generates parquet files
        userDataset
                .write()
                .option("header", true)
                .mode(SaveMode.Overwrite)
                .parquet(filePath);
        LOGGER.info("File generated at = " + filePath);

        // Read parquet file contents
        Dataset<User> usersFromParquetFile = ss.read()
                .parquet(filePath)
                .as(User.encoder());
        LOGGER.info("Total users from file = " + usersFromParquetFile.count());
        usersFromParquetFile.show();

        FileSystem fileSystem = FileSystem.get(ss.sparkContext().hadoopConfiguration());
        long length = fileSystem.getContentSummary(new Path(filePath)).getLength();
        long fileLength = length / (1 * 1024 * 1024); // 1MB

        // Write parquet contents in a single csv file
        usersFromParquetFile
                .coalesce(1)
                .write()
                .option("header", true)
                .mode(SaveMode.Overwrite)
                .csv(filePath.concat("csv"));

        // Rename generated csv file
        FileUtil.copyMerge(
                fileSystem,
                new Path(filePath.concat("csv")),
                fileSystem,
                new Path(filePath.concat("/new/").concat(String.format("file_%s.csv", LocalDate.now()))),
                true,
                ss.sparkContext().hadoopConfiguration(),
                null);
    }

}
