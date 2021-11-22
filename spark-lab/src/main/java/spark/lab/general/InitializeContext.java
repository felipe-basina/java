package spark.lab.general;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public abstract class InitializeContext {

    protected static Logger LOGGER = Logger.getLogger(InitializeContext.class);

    protected JavaSparkContext sc;

    public InitializeContext() {
        this.logging();

        SparkConf conf = new SparkConf()
                .setAppName("Java API demo")
                .setMaster("local[*]")
                .set("spark.cassandra.connection.host", "127.0.0.1");
        this.sc = new JavaSparkContext(conf);
    }

    private void logging() {
        LOGGER.getLogger("org").setLevel(Level.ERROR);
        LOGGER.getLogger("com.datastax").setLevel(Level.ERROR);
    }

}
