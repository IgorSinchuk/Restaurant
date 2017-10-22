package com.igorsinchuk.restaurant.dao.hibernate;

import com.igorsinchuk.restaurant.dao.DishDao;
import com.igorsinchuk.restaurant.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HibernateDishDao implements DishDao {

    private EntityManager entityManager;

    public HibernateDishDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Dish dish) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dish);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public List<Dish> getAll() {
        List<Dish> dishes = new ArrayList<>();
        dishes.addAll(entityManager.createQuery("select d from Dish d").getResultList());
        return dishes;
    }

    @Override
    public List<Dish> getByDiscount() {
        List<Dish> dishes = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dish> creiteriaQuery = cb.createQuery(Dish.class);
        Root<Dish> dishRoot = creiteriaQuery.from(Dish.class);
        creiteriaQuery.select(dishRoot)
                .where(cb.equal(dishRoot.get("discount"), true));
        dishes = entityManager.createQuery(creiteriaQuery).getResultList();
        return dishes;
    }

    @Override
    public List<Dish> getByPrice(double from, double to) {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("select d from Dish d where d.price between :from1 and :to");
        query.setParameter("from1",from);
        query.setParameter("to",to);
        dishes.addAll(query.getResultList());
        return dishes;
    }

    @Override
    public List<Dish> getUpToWeight() {
        List<Dish> dishes = new ArrayList<>();
        List<Dish> result = new ArrayList<>();
        dishes.addAll(getAll());
        Collections.shuffle(dishes);
        int weight = 0;
        for(int i = 0; i < dishes.size()||weight <=1000; i++){
            weight+=dishes.get(i).getWeight();
            if(weight <= 1000){
                result.add(dishes.get(i));
            }
        }
        System.out.println("Total weigth = " + result.stream().reduce(0,(summ,dish) -> summ+=dish.getWeight(),(sum1,sum2) -> sum1+sum2));
        return result;
    }
}
