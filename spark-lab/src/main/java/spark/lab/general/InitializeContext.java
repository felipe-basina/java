package spark.lab.general;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;

public abstract class InitializeContext {

    protected static Logger LOGGER = Logger.getLogger(InitializeContext.class);

    protected static SparkConf conf;

    public InitializeContext() {
        this.logging();

        conf = new SparkConf()
                .setAppName("Java API demo")
                .setMaster("local[*]")
                .set("spark.cassandra.connection.host", "127.0.0.1");
    }

    private void logging() {
        LOGGER.getLogger("org").setLevel(Level.ERROR);
        LOGGER.getLogger("com.datastax").setLevel(Level.ERROR);
    }

}
