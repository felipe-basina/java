package alura.spark.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class JSONFileContent implements Serializable {

    private Integer id;

    private String unitId;

    private Double sellPrice;

    private Integer amount;

    public JSONFileContent(final String jsonContent) {
        final String[] split = jsonContent.replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .split(",");
        this.id = Integer.valueOf(this.getJsonValue(split[0]));
        this.unitId = this.getJsonValue(split[1]);
        this.sellPrice = Double.valueOf(this.getJsonValue(split[2]));
        this.amount = Integer.valueOf(this.getJsonValue(split[3]));
    }

    private String getJsonValue(final String jsonContent) {
        return jsonContent.split(":")[1].trim();
    }

}
