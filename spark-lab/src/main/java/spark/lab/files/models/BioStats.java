package spark.lab.files.models;

import lombok.Data;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

import java.io.Serializable;

@Data
public class BioStats implements Serializable {

    private String name;

    private String sex;

    private int height;

    private int weight;

    public static Encoder<BioStats> encoder() {
        return Encoders.bean(BioStats.class);
    }

}
