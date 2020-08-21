package alura.spark.models;

import lombok.ToString;

import java.time.LocalDateTime;

public interface IOrder {

    public Integer getOrderId();

    public default String status() {
        return null;
    }

    public default LocalDateTime date() {
        return null;
    }

    public default String unitId() {
        return null;
    }

    public default Double sellPrice() {
        return null;
    }

    public default Integer amount() {
        return null;
    }

}
