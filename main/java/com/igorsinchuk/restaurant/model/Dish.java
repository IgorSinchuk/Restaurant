package com.igorsinchuk.restaurant.model;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class Dish {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name = "dish")
private String dish;

@Column(name = "price")
private Double price;

@Column(name = "weight")
private Integer weight;

@Column(name = "discount")
private Boolean discount;

    public Dish() {

    }

    public Dish(String dish, Double price, Integer weight, Boolean discount) {
        this.dish = dish;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }


}