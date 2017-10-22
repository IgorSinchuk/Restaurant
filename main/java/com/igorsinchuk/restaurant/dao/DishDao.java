package com.igorsinchuk.restaurant.dao;

import com.igorsinchuk.restaurant.model.Dish;

import java.util.List;
import java.util.Map;

public interface DishDao {

    void save (Dish dish);
    List<Dish> getAll();
    List<Dish> getByDiscount();
    List<Dish> getByPrice(double from, double to);
    List<Dish> getUpToWeight();
}
