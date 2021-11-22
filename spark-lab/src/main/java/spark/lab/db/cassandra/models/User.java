package spark.lab.db.cassandra.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String lastName;

    private String firstName;

    private String email;

    public static Encoder<User> encoder() {
        return Encoders.bean(User.class);
    }

}
