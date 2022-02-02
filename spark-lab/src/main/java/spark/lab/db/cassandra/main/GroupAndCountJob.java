package spark.lab.db.cassandra.main;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import spark.lab.general.InitializeContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.IntStream;

import static spark.lab.general.util.GeneralConstants.SPARK_SQL_CASSANDRA_FORMAT;

public class GroupAndCountJob extends InitializeContext {

    private static final String KEY_SPACE = "random_stuah";
    private static final String TABLE = "random_stuff";
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2021, 1, 1);

    public GroupAndCountJob() {
        super();
    }

    public static void main(String[] args) {
        new GroupAndCountJob();

        try (SparkSession ss = sparkSessionBuilder().getOrCreate()) {
            Dataset<Row> randomStuff = getRawRandomStuff(ss);
            LOGGER.info("Total data = " + randomStuff.count());
            groupByProcessedDateAndNumPartition(randomStuff);
        }
    }

    private static Dataset<Row> getRawRandomStuff(SparkSession ss) {
        return ss.read()
                .format(SPARK_SQL_CASSANDRA_FORMAT)
                .option("keyspace", KEY_SPACE)
                .option("table", TABLE)
                .load();
    }

    private static void groupByProcessedDateAndNumPartition(Dataset<Row> randomStuff) {
        IntStream.range(0, 11).forEach(index -> {
            long totalByFilter = randomStuff
                    .orderBy(functions.col("num_partition"))
                    .groupBy(functions.col("processed_date"),
                            functions.col("num_partition"))
                    .df()
                    .filter(getRowFilterFunction(index))
                    .count();
            LOGGER.info("Filter processed_date = '" + DEFAULT_DATE + "', index = '" + index + "', total = " + totalByFilter);
        });
    }

    private static FilterFunction<Row> getRowFilterFunction(int index) {
        return value -> value.getAs("processed_date").equals(Date.valueOf(DEFAULT_DATE))
                && ((Integer) value.getAs("num_partition")) == index;
    }

    // TODO: Realizar consulta por filtro
    private static Dataset<Row> getRawRandomStuffByProcessedDateAndNumPartition(SparkSession ss,
                                                                                LocalDate processedDate,
                                                                                int numPartition) {
        return null;
    }

}
