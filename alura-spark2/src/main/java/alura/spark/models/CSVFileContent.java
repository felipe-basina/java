package alura.spark.models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CSVFileContent implements Serializable, IOrder {

    private Integer id;

    private String status;

    private LocalDateTime date;

    public CSVFileContent(final String csvContent) {
        final String[] split = csvContent.split(";");
        this.id = Integer.valueOf(split[0].trim());
        this.status = split[1];
        this.date = this.toLocalDateTime(split[2]);
    }

    private LocalDateTime toLocalDateTime(final String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception ex) {
            return LocalDateTime.now().minusYears(1l);
        }
    }

    @Override
    public Integer getOrderId() {
        return this.id;
    }

    @Override
    public String status() {
        return this.status;
    }

    @Override
    public LocalDateTime date() {
        return this.date;
    }
}
