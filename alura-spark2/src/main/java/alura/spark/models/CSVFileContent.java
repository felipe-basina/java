package alura.spark.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class CSVFileContent implements Serializable, IOrder {

    private Integer id;

    private String status;

    private String date;

    public CSVFileContent(final String csvContent) {
        final String[] split = csvContent.split(";");
        this.id = Integer.valueOf(split[0].trim());
        this.status = split[1];
        this.date = split[2];
    }

    @Override
    public Integer getOrderId() {
        return this.id;
    }

}
