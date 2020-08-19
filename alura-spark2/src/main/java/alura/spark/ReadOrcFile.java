package alura.spark;

import org.apache.hadoop.hive.ql.io.orc.OrcInputFormat;
import org.apache.hadoop.hive.ql.io.orc.OrcStruct;
import org.apache.hadoop.io.NullWritable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class ReadOrcFile {

    private static SparkConf conf;
    private static JavaSparkContext sc;

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        conf = new SparkConf().setAppName("ReadFilesAsDataSet").setMaster("local");
        sc = new JavaSparkContext(conf);

        JavaPairRDD<NullWritable, OrcStruct> orcSourceRdd = sc.hadoopFile("in/".concat(Files.LOJA_ORK.getFileName()),
                OrcInputFormat.class,
                NullWritable.class,
                OrcStruct.class, 1);

        orcSourceRdd.map(new Function<Tuple2<NullWritable, OrcStruct>, String>() {
            private static final long serialVersionUID = 5454545;
            public String call(Tuple2<NullWritable, OrcStruct> orcStruct) throws Exception {
                OrcStruct struct = orcStruct._2();
                return struct.toString();
            }
        }).take(10).forEach(System.out::println);
    }

}
