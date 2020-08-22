package alura.spark.models;

import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
public class Order implements Serializable, IOrder {

    @Override
    public Integer getOrderId() {
        return null;
    }

    @Override
    public String status() {
        return null;
    }

    @Override
    public LocalDateTime date() {
        return null;
    }

    @Override
    public String unitId() {
        return null;
    }

    @Override
    public Double sellPrice() {
        return null;
    }

    @Override
    public Integer amount() {
        return null;
    }
}
