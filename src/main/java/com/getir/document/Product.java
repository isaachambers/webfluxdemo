package com.getir.document;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {

  @Id
  private UUID id;
  private String name;
  private String description;
  private Double price;

  public Product(UUID id, String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.id = id;
  }

  public Product() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
