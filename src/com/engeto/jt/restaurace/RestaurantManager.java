package com.engeto.jt.restaurace;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {

    static int lineCount = 1;
    public static int method1_numberOfUnfinishedOrders() {
        return Order.getListOfUnfinishedOrders().size();
    }
    public static List<Order> method2_sortOrdersWaiter() {
        List<Order> result = Order.getOrders();
        Collections.sort(result, Order::compareToWaiter);
        return result;
    }
    public static List<Order> method2_sortOrdersOrderedTime() {
        List<Order> result = Order.getOrders();
        Collections.sort(result, Order::compareToOrderedTime);
        return result;
    }

    public static void method3_ordersPerWaiter() {

        Waiter.getWaitres().forEach( waiter -> {
            Order.getOrders().forEach(order -> {
                if (waiter.getWaiterID()==(order.getWaiterID())) {
                    waiter.setNumberOfOrders(waiter.getNumberOfOrders() + 1);
                    CookBook.getCookBook().forEach(n -> {
                        if(order.getDishID()==n.dishID)
                            waiter.setAmountOfMoney(waiter.getAmountOfMoney() + n.dishPrice);
                    } );
                }
            } );
            System.out.println(waiter.toString());
        } );
    }

    public static double method4_averageOrderTime (LocalDateTime from, LocalDateTime to) {
    int count = 0;
    int amountOfMinutes = 0;
        List<Order> orders = new ArrayList<>(Order.getOrders());
        for(int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderedTime().isAfter(from) && orders.get(i).getOrderedTime().isBefore(to) && orders.get(i).getFulfilledTime()!=null) {
                count++;
                amountOfMinutes += ChronoUnit.MINUTES.between(orders.get(i).getOrderedTime(), orders.get(i).getFulfilledTime());
            }
        }
        return (double) amountOfMinutes / count;
    }

    public static void method5_listOrdersToday () {
        Set<Integer> ordersToday = new HashSet<>();
        Order.getOrders().forEach(n -> {
            if (n.getOrderedTime().toLocalDate().equals(LocalDate.now())) ordersToday.add(n.getDishID());
        });
        ordersToday.forEach(n -> {
            CookBook.getCookBook().forEach(a -> {
                if(n==a.dishID) System.out.println(a.toString());
            });
        });


    };

    public static void method6_deskOrderSummary(int tableID) {
        System.out.println("\n** Objednávka pro stůl č. "+String.format("%2d", tableID)+" **");
        System.out.println("****");
        int lineCount = 1;
        List<Order> deskOrderSummary = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

        Order.getOrders().forEach(n -> {
            if (n.getOrderedTime().toLocalDate().equals(LocalDate.now()) && n.getTableID()==tableID) deskOrderSummary.add(n);
        });

        deskOrderSummary.forEach(order -> {
            CookBook.getCookBook().forEach(dish -> {
                if(order.getDishID()==dish.dishID && order.getFulfilledTime()!=null)
                    System.out.println(getLineCount() + ". " + dish.getDishName() + " " + order.getCountString() + " ("
                            + dish.dishPrice*order.getCount() + "Kč):\t" + order.getOrderedTime().toLocalTime().format(formatter)
                            + "-" + order.getFulfilledTime().toLocalTime().format(formatter) + " číšník č. " + order.getWaiterID() );
            });
        });
        System.out.println("******");

    }

    static public int getLineCount () {
        return lineCount++;
    }

}
