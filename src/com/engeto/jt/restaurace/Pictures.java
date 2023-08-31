package com.engeto.jt.restaurace;

import java.io.*;
import java.util.*;

public class Pictures extends ArrayList<Dish> {

    private static List<Picture> pictures = new ArrayList<>();

    public void loadPictures() throws RegisterException {
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(Settings.getPicturesPathname()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 2) { // výpis vadného řádku, jak se jej podařilo rozparsovat
                    for(int i = 0; i < items.length; i++) {System.out.println("item["+i+"]: "+ items[i]);};
                    throw new RegisterException("Nesprávný počet položek na řádku č. " + lineNo + ":\n" + line);
                }
                Picture picture = new Picture(
                        Integer.parseInt(items[0]), // dishID - může se opakovat, více fotek k jednomu jídlu
                        items[1] );                 // picturePath
                pictures.add(picture);
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla na řádku č. " + lineNo + " v souboru " + Settings.getPicturesPathname() + " : " + e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor " + Settings.getPicturesPathname() + " se nepodařilo najít: " + e.getLocalizedMessage()+"\nPokračuji ve spouštění aplikace.");
        }
    }

    public static void savePicturesToFile() throws RegisterException {
        String path = Settings.getPicturesPathname();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (Picture picture  : pictures) {
                writer.println(
                        picture.getDishID() + Settings.getDelimiter()
                                + picture.getPicturePath());
            }
        } catch (IOException e) {
            throw new RegisterException("Chyba zápisu do souboru "+path+": "+e.getLocalizedMessage());
        }
    }
    public List<Picture> getPictures() {
        return new ArrayList<>(pictures);
    }

    public void deletePictureFromPictures(Picture picture) {
                pictures.remove(picture);
    }

    public List<Picture> getPicturesForDish(int dishID) {
        List<Picture> result = new ArrayList<>();
        for (Picture n : pictures) {
            if (n.getDishID() == dishID) result.add(n);
        }
        if(result.isEmpty()) result.add(new Picture(dishID, "http://localhost/pictures/blank.png")); // vložíme prázdný obrázek
        return result;
    }
}