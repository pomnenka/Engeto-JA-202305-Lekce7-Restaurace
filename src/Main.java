import com.engeto.jt.restaurace.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Začátek úlohy \"Restaurace\"!\n");

        new Waiter("Martin");
        new Waiter("Jana");
        new Waiter("Roman");
        Waiter.getWaitres().forEach(n-> System.out.println(n.toString()));

        Menu menu = new Menu();
        CookBook cookBook = new CookBook();
        Pictures pictures = new Pictures();

        System.out.println("Začátek úlohy \"Načtení souboru CookBook...");
        try { cookBook.loadDishes();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve spouštění aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("Začátek úlohy \"Načtení souboru Pictures...");
        try { pictures.loadPictures();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve spouštění aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("Výpis obsahu Listu CookBook (v vloženými linky na obrázky z Pictures:");
        cookBook.getCookBook().forEach(n -> {
                    System.out.println(n.toString());
                    pictures.getPicturesForDish(n.getDishID()).forEach(x -> System.out.println(x.toString()));
                }
        );

        System.out.println("\nSmazání jídla č. 1 z CookBook a kompletní výpis -  - momentálně zakomentované:");
        if(cookBook.deleteDishFromCookBook(1,menu, pictures)==0) {
            System.out.println("Smazání jídla č. 1 z CookBook proběhlo úspěšně.");
        } else {
            System.out.println("Smazání jídla č. 1 z CookBook se nezdařilo, zřejmě již bylo smazáno dříve.");
        }
        cookBook.getCookBook().forEach(n -> {
            System.out.println(n.toString());
            pictures.getPicturesForDish(n.getDishID()).forEach(x -> System.out.println(x.toString()));
            }
        );


        System.out.println("\nPřidáme jídel do menu a vytiskneme Menu:");
        menu.addMenuItem(3);
        menu.addMenuItem(6);
        menu.addMenuItem(7);
        menu.addMenuItem(6);
        menu.printMenu(cookBook);

        System.out.println("\nOdebereme jídlo 6 z menu a vytiskneme Menu:");
        menu.deleteMenuItem(6);
        menu.printMenu(cookBook);

        System.out.println("\nSmazání jídla č. 3 z CookBook a kompletní výpis - momentálně zakomentované:");

    //    cookBook.deleteDishFromCookBook(3, menu, pictures);
        cookBook.getCookBook().forEach(n -> System.out.println(n.toString()));

        System.out.println("\nVýpis Menu:");
        menu.printMenu(cookBook);

        System.out.println("\nPřidáme jídel do menu a vytiskneme Menu:");
        menu.addMenuItem(4);
        menu.addMenuItem(5);
        menu.addMenuItem(6);
        menu.printMenu(cookBook);

//        System.out.println("\nSmažeme Menu a vytiskneme jej:");
//        menu.clearMenu();
//        menu.printMenu(cookBook);

        System.out.println("\nZměníme jídlo č. 7 na Bavorské vdolečky, s jinou cenou a jinou kategorií a vytiskneme CookBook:");
        cookBook.changeDishInCookBook(7, "'Bavorské vdolečky'", 120, 15, "'vegetariánské'");
        cookBook.getCookBook().forEach(n -> System.out.println(n.toString()));

        System.out.println("\nVytiskneme Menu:");
        menu.printMenu(cookBook);

        System.out.println("\nNačtení souboru Ordes...");
        try { Order.loadOrdersFromFile();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve spouštění aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("Výpis Orders:");
        Order.getOrders().forEach(n -> System.out.println(n.toString()));
        System.out.println("Orders size: "+Order.getOrders().size());

        System.out.println("\nZaložení objednávky jídla č.4");
        int newOrder = 4;
        if (menu.containsDishID(newOrder)) {
        Order order = new Order(
                newOrder, // dishID
                1, // one peace of it
                3, // waiterID
                5, // tableID
                "starší pár" ); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("\nZaložení objednávky jídla č.3");
        newOrder = 3;
        if (menu.containsDishID(newOrder)) {
            Order order = new Order(
                    newOrder, // dishID
                    1, // one peace of it
                    3, // waiterName
                    13, // tableID
                    "starší pár" ); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("\nVýpis Orders:");
        Order.getOrders().forEach(n -> System.out.println(n.toString()));
        System.out.println("Orders size: "+Order.getOrders().size());

        System.out.println("\nUkládání souboru Ordes...");
        try { Order.saveOrdersToFile();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve ukončování aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("\nUkládání souboru CookBook...");
        try { cookBook.saveCookBookToFile();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve ukončování aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("\nUkládání souboru Pictures...");
        try { pictures.savePicturesToFile();}
        catch (RegisterException e) { System.out.println(e.getLocalizedMessage()+"\nPokračuji ve ukončování aplikace.");} // chyby v souborech nejsou překážkou ve spuštění aplikace

        System.out.println("\n=============================================");
        System.out.println("Požadované výstupy z RestaurantManagera:");
        System.out.println("\n1) Kolik objednávek je aktuálně rozpracovaných a nedokončených: "+RestaurantManager.method1_numberOfUnfinishedOrders());

        System.out.println("\n2) Možnost seřadit objednávky podle číšníka : ");
        RestaurantManager.method2_sortOrdersWaiter().forEach(n -> System.out.println(n.toString()));

        System.out.println("\n2) Možnost seřadit objednávky podle času zadání: ");
        RestaurantManager.method2_sortOrdersOrderedTime().forEach(n -> System.out.println(n.toString()));

        System.out.println("\n3) Počet objednávek podle číšníka: ");
        RestaurantManager.method3_ordersPerWaiter();

        System.out.println("\n4) Průměrná doba objednávky v minutách: ");
        System.out.println( RestaurantManager.method4_averageOrderTime(LocalDateTime.of(2023, 07, 11, 23,30), LocalDateTime.of(2023, 07, 11, 23,32)));

        System.out.println("\n5) Seznam dnes objednaných jídel: ");
        RestaurantManager.method5_listOrdersToday();

        System.out.println("\n6) Objednávka pro stůl č.13: ");
        RestaurantManager.method6_deskOrderSummary(13);

        System.out.println("\n============================================ ");
        System.out.println("=====  T E S T O V A C I   S A D A   ======= ");
        System.out.println("============================================ ");

        System.out.println("\n== TESTOVACI SADA == úloha č.1) Stav evidence z disku byla načtena při startu programu.");
        System.out.println("\n== TESTOVACI SADA == úloha č.2) Příprava testovacích dat:");
        cookBook.addDishInCookBook("Kuřecí řízek obalovaný 150g", 165, 17, "minutka");
        cookBook.addDishInCookBook("Hranolky 150g", 99, 10, "příloha");
        cookBook.addDishInCookBook("Pstruh na víně 200g", 300, 22, "minutka");

        System.out.println("Výpis obsahu Listu CookBook (v vloženými linky na obrázky z Pictures:");
        cookBook.getCookBook().forEach(n -> {
                    System.out.println(n.toString());
                    pictures.getPicturesForDish(n.getDishID()).forEach(x -> System.out.println(x.toString()));
                }
        );

        // zařazení do Menu
        System.out.println("\nPřidáme jídel do menu a vytiskneme Menu:");
        menu.addMenuItem(8);
        menu.addMenuItem(10);
        menu.printMenu(cookBook);

        System.out.println("\nZaložení objednávky jídla č.8 pro stůl č. 15");
        newOrder = 8;
        if (menu.containsDishID(newOrder)) { Order order = new Order( newOrder, 1, 3, 15, "strašně hladoví"); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("\nZaložení objednávky jídla č.10 pro stůl č. 15");
        newOrder = 10;
        if (menu.containsDishID(newOrder)) { Order order = new Order( newOrder, 1, 2, 15, "strašně hladoví"); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("\nZaložení objednávky jídla č.5 pro stůl č. 2");
        newOrder = 5;
        if (menu.containsDishID(newOrder)) { Order order = new Order( newOrder, 1, 1, 2, "strašně hladoví"); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("\n== TESTOVACI SADA == úloha č.3) Založení chybné objednávky jídla č.25 pro stůl č. 2");
        newOrder = 25;
        if (menu.containsDishID(newOrder)) { Order order = new Order( newOrder, 1, 1, 2, "strašně hladoví"); // note
        } else {
            System.out.println("Objednávku: "+newOrder+" nelze založit, protože není na jídelním lístku.");
        }

        System.out.println("Výpis Orders:");
        Order.getOrders().forEach(n -> System.out.println(n.toString()));

        System.out.println("\n== TESTOVACI SADA == úloha č.4) Uzavření objednávky posledního objednaného jídla");
        Order.getOrders().get(Order.ordersCount() - 1).closeOrder();
        System.out.println("Výpis Orders:");
        Order.getOrders().forEach(n -> System.out.println(n.toString()));

        System.out.println("\nKonec úlohy \"Restaurace\"!");
    }
}

