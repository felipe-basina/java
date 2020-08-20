package alura.spark.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrcFileContent implements Serializable, IOrder {

    private Integer id;

    private String date;

    private Double price;

    public OrcFileContent(final String orcFileContent) {
        String[] split = orcFileContent
                .replace("{", "")
                .replace("}", "")
                .split(",");
        this.id = Integer.valueOf(split[0].trim());
        this.date = split[1].trim();
        this.price = Double.valueOf(split[2].trim());
    }

    @Override
    public Integer getOrderId() {
        return this.id;
    }

}
