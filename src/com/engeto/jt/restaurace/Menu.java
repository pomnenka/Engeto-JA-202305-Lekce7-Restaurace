package com.engeto.jt.restaurace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {
    private static Set<Integer> menu = new HashSet<>();

    public boolean containsDishID(int dishID) {
        return menu.contains(dishID);
    }

    public static Set<Integer> getMenu() {
        return new HashSet<>(menu);
    }

    public static void addMenuItem(int dishID) {
        menu.add(dishID);
        System.out.println("Přidáno jídlo do Menu č.: "+dishID);
    }

    public static int deleteMenuItem(int dishID) {
        if(menu.contains(dishID)) {
            menu.remove(dishID);
            return 0;
        } else
            return -1;  // položka požadovaná pro smazání v seznamu není
    }
    public static void clearMenu() {
        menu.clear();
    }

    public void printMenu(CookBook cookBook) {
    menu.forEach(dishID ->
            cookBook.getCookBook().forEach(x -> {if(x.getDishID() == dishID) System.out.println(x.toString());}));
    }
}


