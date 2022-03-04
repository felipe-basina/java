package spark.lab.db.cassandra.main;

import org.apache.hadoop.fs.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import spark.lab.db.cassandra.models.User;
import spark.lab.general.InitializeContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static spark.lab.general.util.GeneralConstants.SPARK_SQL_CASSANDRA_FORMAT;

public class ReadAndWriteLargeFileJob extends InitializeContext {

    private static final String KEY_SPACE = "user_management";
    private static final String TABLE = "users_by_name";

    public ReadAndWriteLargeFileJob() {
        super();
    }

    public static void main(String[] args) {
        new ReadAndWriteLargeFileJob();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
//            List<User> users = new ArrayList<>();
//            IntStream.rangeClosed(1, 20000).forEach(index -> {
//                users.add(new User(
//                        "Doe_" + index,
//                        "Joe_" + index,
//                        String.format("joe.doe_%d@domain.com", index)));
//            });
//            addUser(ss, users);
            generateUserParquetFile(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addUser(SparkSession ss, List<User> users) {
        Dataset<User> newUserDataset = ss.createDataset(users, User.encoder());
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

    private static void generateUserParquetFile(SparkSession ss) throws IOException {
        final String filePath = "/opt/dev/files";
        final String targetFolder = "/opt/dev/renamed/";

        Dataset<User> userDataset = getUsersByName(ss);
        LOGGER.info("Total users from database = " + userDataset.count());

        // To read a folder and get total bytes from it
        FileSystem fileSystem = FileSystem.get(ss.sparkContext().hadoopConfiguration());
//        long length = fileSystem.getContentSummary(new Path(filePath)).getLength();
//        long fileLengthMb = length / (1 * 1024 * 1024); // 1 MB
//        long fileLength = length / (100 * 1024); // 100 KB
//        LOGGER.info(">>>>>> Length = " + length);
//        LOGGER.info(">>>>>> File length = " + fileLength);

        // Write parquet contents in a single csv file
        userDataset
                .drop(org.apache.spark.sql.functions.col("email"))
                .coalesce(3) // Should be replaced by fileLength when it exists
                .write()
                .option("header", true)
                .mode(SaveMode.Overwrite)
                .csv(filePath);

        LOGGER.info("#### " + new Path(filePath));

        RemoteIterator<LocatedFileStatus> csvFiles = fileSystem.listFiles(new Path(filePath), false);

        int index = 0;
        while (csvFiles.hasNext()) {
            LocatedFileStatus next = csvFiles.next();
            if (!next.getPath().toString().contains(".csv")) {
                continue;
            }

            final String fileName = next.getPath().toString().replace("file:", "").trim();

            sampleRow(ss, fileName);
            copyFileToTargetFolder(ss, fileSystem, targetFolder, fileName, ++index);
        }

        compactFiles(fileSystem, targetFolder, String
                .format("compact_%s.zip", DateTimeFormatter.ofPattern("yyyyMMddkkmmss").format(LocalDateTime.now())));

        deleteTempFile(fileSystem, filePath);
    }

    private static void sampleRow(SparkSession ss, String fileName) {
        Dataset<Row> csvContent = ss.read().csv(fileName);
        Row row = csvContent.toJavaRDD().collect().get(1);
        LOGGER.info("Sample of firstName = " + row.getString(0));
        LOGGER.info("Total content for file index = [" + fileName + "]: " + csvContent.count());
    }

    private static void copyFileToTargetFolder(SparkSession ss,
                                               FileSystem fileSystem,
                                               String targetFolder,
                                               String fileName,
                                               int index)
            throws IOException {
        FileUtil.copy(
                fileSystem,
                new Path(fileName),
                fileSystem,
                new Path(targetFolder
                        .concat(String.format("file_%s_%d.csv", LocalDate.now(), index))),
                true,
                ss.sparkContext().hadoopConfiguration());
    }

    private static void deleteTempFile(FileSystem fileSystem, String filePath) throws IOException {
        fileSystem.delete(new Path(filePath), true);
    }

    private static void compactFiles(FileSystem fileSystem,
                                 String targetFolder,
                                 String compactedFileName) throws IOException {
        RemoteIterator<LocatedFileStatus> targetFiles = fileSystem.listFiles(new Path(targetFolder), false);

        try (FileOutputStream fileWriter = new FileOutputStream(targetFolder.concat(compactedFileName));
             ZipOutputStream zip = new ZipOutputStream(fileWriter)) {
            while (targetFiles.hasNext()) {
                LocatedFileStatus next = targetFiles.next();
                if (!next.getPath().toString().contains(".csv")) {
                    continue;
                }

                final String fileName = next.getPath().toString().replace("file:", "").trim();
                LOGGER.info("File name to be compacted: " + fileName);

                byte[] buf = new byte[1024];
                int len;
                try (FileInputStream in = new FileInputStream(fileName)) {
                    String name = next.getPath().getName();
                    zip.putNextEntry(new ZipEntry(name));
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                }

                deleteTempFile(fileSystem, fileName);
            }
        }
    }

}
