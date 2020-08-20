package alura.spark;

import alura.spark.models.OrcFileContent;
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

    // referência: http://timepasstechies.com/reading-orc-file-spark/
    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        conf = new SparkConf().setAppName("ReadFilesAsDataSet").setMaster("local");
        sc = new JavaSparkContext(conf);

        JavaPairRDD<NullWritable, OrcStruct> orcSourceRdd = sc.hadoopFile("in/".concat(Files.LOJA_ORK.getFileName()),
                OrcInputFormat.class,
                NullWritable.class,
                OrcStruct.class, 1);

        JavaRDD<OrcFileContent> orcFileContents = orcSourceRdd.map(new Function<Tuple2<NullWritable, OrcStruct>,
                OrcFileContent>() {
            private static final long serialVersionUID = 5454545;

            public OrcFileContent call(Tuple2<NullWritable, OrcStruct> orcStruct) throws Exception {
                OrcStruct struct = orcStruct._2();
                return new OrcFileContent(struct.toString());
            }
        });

        orcFileContents.collect().forEach(System.out::println);

        //.saveAsTextFile("out/output_data.txt");
    }

}
