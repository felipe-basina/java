package spark.lab.general;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public abstract class InitializeContext {

    protected static Logger LOGGER = Logger.getLogger(InitializeContext.class);

    protected static SparkConf conf;

    public InitializeContext() {
        conf = new SparkConf()
                .setAppName("Java API demo")
                .setMaster("local[*]")
                .set("spark.cassandra.connection.host", "127.0.0.1");
    }

    protected static SparkSession.Builder sparkSessionBuilder() {
        return SparkSession.builder().config(conf);
    }

}
