package com.engeto.jt.restaurace;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Boolean.TRUE;

public class Order {
    private static int nextId = 1;
    public int count;
    private int orderID;
    private int dishID;
    private int waiterID;
    private int tableID;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilledTime;
    private String note;
    private static List<Order> orders = new ArrayList<>();

    public Order(int dishID, int count, int waiterID, int tableID, String note) {
        this( nextId++, dishID, count,waiterID, tableID, LocalDateTime.now(), null, note);
    }
    public Order(int orderID, int dishID, int count, int waiterID, int tableID, LocalDateTime orderedTime, LocalDateTime fulfilledTime, String note) {
        this.orderID = orderID;
        this.dishID = dishID;
        this.count = count;
        this.waiterID = waiterID;
        this.tableID = tableID;
        this.orderedTime = orderedTime;
        this.fulfilledTime = fulfilledTime;
        this.note = note;
        orders.add(this);
    }
    public static void loadOrdersFromFile() throws RegisterException {
        String path = Settings.getOrdersPathname();
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 8) { // výpis vadného řádku, jak se jej podařilo rozparsovat
                    for(int i = 0; i < items.length; i++) {System.out.println("item["+i+"]: "+ items[i]);};
                    throw new RegisterException("Nesprávný počet položek na řádku č. " + lineNo + ":\n" + line);
                }
                Order order = new Order(
                        Integer.parseInt(items[0]), // orderID
                        Integer.parseInt(items[1]), // dishID
                        Integer.parseInt(items[2]), // count
                        Integer.parseInt(items[3]), // waiterID
                        Integer.parseInt(items[4]), // tableID
                        (items[5].contentEquals("null")?null:LocalDateTime.parse(items[5])), // orderedTime
                        (items[6].contentEquals("null")?null:LocalDateTime.parse(items[6])), // fulfilledTime
                        items[7] );                 // note
                if(nextId<=Integer.parseInt(items[0])) { nextId = Integer.parseInt(items[0]) + 1;}
//                System.out.println("\nVýpis Order:"+order.toString()); // kontrolní výpis co se načetlo a uložilo do objektu order
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla na řádku č. " + lineNo + " v souboru " + path + " : " + e.getLocalizedMessage());
        } catch (DateTimeException e) {
            throw new RegisterException(
                    "Nesprávný formát datumu či času na řádku č. " + lineNo + " v souboru " + path + " : " + e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new RegisterException(
                    "Soubor " + path + " se nepodařilo najít: " + e.getLocalizedMessage());
        }
    }

    public static void saveOrdersToFile() throws RegisterException {
        String path = Settings.getOrdersPathname();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (Order order : orders) {
                writer.println(
                        order.orderID + Settings.getDelimiter()
                                + order.dishID + Settings.getDelimiter()
                                + order.count + Settings.getDelimiter()
                                + order.waiterID + Settings.getDelimiter()
                                + order.tableID + Settings.getDelimiter()
                                + order.orderedTime + Settings.getDelimiter()
                                + order.fulfilledTime + Settings.getDelimiter()
                                + order.note );
            }
        } catch (IOException e) {
            throw new RegisterException("Chyba zápisu do souboru "+path+": "+e.getLocalizedMessage());
        }
    }
    public void closeOrder() {
        this.fulfilledTime = LocalDateTime.now();
    }

    public static List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public static int ordersCount() {
        return  orders.size();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", dishID=" + dishID +
                ", waiterID='" + waiterID + '\'' +
                ", tableID=" + String.format("%2d", tableID) +
                ", orderedTime=" + orderedTime +
                ", fulfilledTime=" + fulfilledTime +
                ", note='" + note + '\'' +
                '}';
    }

    public static List<Order> getListOfUnfinishedOrders() {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if(order.fulfilledTime==null )  result.add(order);
        }
        return result;
    }

    public int compareToWaiter(Order second) {
        if (this.waiterID==second.waiterID) return 0;
        if (this.waiterID>second.waiterID) return 1;
        return 1;
    }
    public int compareToOrderedTime(Order second) {
        return this.orderedTime.compareTo(second.orderedTime);
    }

    public int getOrderID() {
        return orderID;
    }

    public int getDishID() {
        return dishID;
    }

    public int getWaiterID() {
        return waiterID;
    }

    public int getTableID() {
        return tableID;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public LocalDateTime getFulfilledTime() {
        return fulfilledTime;
    }

    public String getNote() {
        return note;
    }
    public int getCount()  {
        return count;
    }
    public String getCountString()  {
        return count==1?"":(Integer.toString(count)+"x");
    }
}
