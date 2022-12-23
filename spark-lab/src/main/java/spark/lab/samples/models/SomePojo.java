package spark.lab.samples.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SomePojo {

    private Integer id;

    private String name;

    private String gender;

    private String documentType;

    private String documentNumber;

}
