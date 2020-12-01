package com.getir.web.controller.request;

public class CreateProductRequest {

  private String name;
  private String description;
  private Double price;

  public CreateProductRequest(String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getPrice() {
    return price;
  }
}
