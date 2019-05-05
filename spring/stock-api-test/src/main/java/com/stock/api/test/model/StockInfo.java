package com.stock.api.test.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * StockInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-05T11:51:43.406-03:00")

public class StockInfo   {
  @JsonProperty("stockId")
  private String stockId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("price")
  private Integer price = null;

  public StockInfo stockId(String stockId) {
    this.stockId = stockId;
    return this;
  }

  /**
   * Get stockId
   * @return stockId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStockId() {
    return stockId;
  }

  public void setStockId(String stockId) {
    this.stockId = stockId;
  }

  public StockInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StockInfo price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * stock price
   * @return price
  **/
  @ApiModelProperty(required = true, value = "stock price")
  @NotNull


  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockInfo stockInfo = (StockInfo) o;
    return Objects.equals(this.stockId, stockInfo.stockId) &&
        Objects.equals(this.name, stockInfo.name) &&
        Objects.equals(this.price, stockInfo.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stockId, name, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockInfo {\n");
    
    sb.append("    stockId: ").append(toIndentedString(stockId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

