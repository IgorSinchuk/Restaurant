package com.igorsinchuk.restaurant;


import com.igorsinchuk.restaurant.dao.DishDao;
import com.igorsinchuk.restaurant.dao.hibernate.HibernateDishDao;
import com.igorsinchuk.restaurant.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.printMenu();
    }

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private DishDao dao;
    private Scanner sc;


    public void printMenu() {
        try {
            init();
            try {
                while (true) {
                    System.out.println("1: add new dish");
                    System.out.println("2: view menu");
                    System.out.println("3: view dishes with discount");
                    System.out.println("4: view dishes by price");
                    System.out.println("5: view dishes up to 1kg");
                    System.out.println("Press Enter to exit");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish();
                            break;
                        case "2":
                            viewAll();
                            break;
                        case "3":
                            viewWithDiscount();
                            break;
                        case "4":
                            selectByPrice();
                            break;
                        case "5":
                            selectUpToKG();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private  void selectUpToKG() {
        dao.getUpToWeight().forEach(System.out::println);
    }

    private  void selectByPrice() {
        System.out.print("Enter price from: ");
        String fromString = sc.nextLine();
        Double from = Double.parseDouble(fromString);
        System.out.print("Enter price to: ");
        String toString = sc.nextLine();
        Double to = Double.parseDouble(toString);

        dao.getByPrice(from,to).forEach(System.out::println);
    }

    private  void viewWithDiscount() {
        dao.getByDiscount().forEach(System.out::println);
    }

    private void viewAll() {
        dao.getAll().forEach(System.out::println);
    }

    private void addDish() {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();
        System.out.print("Enter dish price: ");
        String pr = sc.nextLine();
        double price = Double.parseDouble(pr);
        System.out.print("Enter dish weigth: ");
        String wg = sc.nextLine();
        int weight = Integer.parseInt(wg);
        System.out.print("Enter dish discount(true or false): ");
        String dis = sc.nextLine();
        boolean discount = Boolean.parseBoolean(dis);
        dao.save(new Dish(name,price,weight,discount));
    }

    private void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("cnntr");
        entityManager = entityManagerFactory.createEntityManager();
        dao= new HibernateDishDao(entityManager);
        sc = new Scanner(System.in);
    }
}
