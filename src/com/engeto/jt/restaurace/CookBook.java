package com.engeto.jt.restaurace;

import java.io.*;
import java.util.*;

public class CookBook extends ArrayList<Dish> {
    private static int nextId = 1;
    private static List<Dish> cookBook = new ArrayList<>();

    public void loadDishes() throws RegisterException {
        String path = Settings.getCookBookPathname();
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 5) { // výpis vadného řádku, jak se jej podařilo rozparsovat
                    for(int i = 0; i < items.length; i++) {System.out.println("item["+i+"]: "+ items[i]);};
                    throw new RegisterException("Nesprávný počet položek na řádku č. " + lineNo + ":\n" + line);
                }
                Dish dish = new Dish(
                        Integer.parseInt(items[0]), // dishID
                        items[1],                   // dishName
                        Integer.parseInt(items[2]), // dishPrice
                        Integer.parseInt(items[3]), // dishPrepTime
                        items[4] );                 // dishCategory
                if(nextId<=Integer.parseInt(items[0])) { nextId = Integer.parseInt(items[0]) + 1;}
                cookBook.add(dish);
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla na řádku č. " + lineNo + " v souboru " + path + " : " + e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor " + path + " se nepodařilo najít: " + e.getLocalizedMessage()+"\nPokračuji ve spouštění aplikace.");
        }
    }

    public static void saveCookBookToFile() throws RegisterException {
        String path = Settings.getCookBookPathname();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (Dish dish  : cookBook) {
                writer.println(
                        dish.getDishID() + Settings.getDelimiter()
                                + dish.getDishName() + Settings.getDelimiter()
                                + dish.getDishPrice() + Settings.getDelimiter()
                                + dish.getDishPrepTime() + Settings.getDelimiter()
                                + dish.getDishCategory());
            }
        } catch (IOException e) {
            throw new RegisterException("Chyba zápisu do souboru "+path+": "+e.getLocalizedMessage());
        }
    }

    public static List<Dish> getCookBook() {
        return new ArrayList<>(cookBook);
    }

    public int deleteDishFromCookBook(int dishID, Menu menu, Pictures pictures) {
        for (Dish n : cookBook) {
            if (n.dishID == dishID) {
                cookBook.remove(n);
                menu.deleteMenuItem(dishID); // smažeme i případný výskyt v Menu
                pictures.getPicturesForDish(n.getDishID()).forEach(x -> pictures.deletePictureFromPictures(x)); // tady smažu i obrázky, vztahující se k mazanému jídlu
                return 0;
            }
        }
        return -1; // položka nenalezena
    }

    public int changeDishInCookBook(int dishID, String dishName, int dishPrice, int dishPrepTime, String dishCategory) {
        for (Dish n : cookBook) {
            if (n.dishID == dishID) {
                n.dishName = dishName;
                n.dishPrice = dishPrice;
                n.dishPrepTime = dishPrepTime;
                n.dishCategory = dishCategory;
                return 0;
            }
        }
        return -1; // položka nenalezena
    }

    public int addDishInCookBook(String dishName, int dishPrice, int dishPrepTime, String dishCategory) {
        Dish n = new Dish(nextId++, dishName, dishPrice, dishPrepTime, dishCategory);
        cookBook.add(n);
        return n.dishID;
    }


}
