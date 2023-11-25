package nacteniAulozeni;

import AgendaKraj.Agenda;
import abstrTable.Obec;
import abstrTable.eTypProhl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UlozeniAnacteni {

    public static <T> void uloz(String nazevSouboru, Agenda list, eTypProhl value)
            throws IOException {
        Objects.requireNonNull(list);
        try (PrintWriter uloz = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)))) {
            Iterator<T> iterator = (Iterator<T>) list.vytvorIterator(value);
            while (iterator.hasNext()) {
                uloz.println(iterator.next());
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Zadejte typ prohlidky");
            alert.showAndWait();
        }
    }

    public static Agenda nacti(String nazevSouboru, Agenda list, eTypProhl value) throws IOException {
        Objects.requireNonNull(list);
        try (Scanner scanner = new Scanner(new File(nazevSouboru))) {
            list.zrus();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                int cisloKraje = Integer.parseInt(parts[1].split(": ")[1]);
                int psc = Integer.parseInt(parts[2].split(": ")[1]);
                String nazevObce = parts[0];
                int pocetMuzu = Integer.parseInt(parts[3].split(": ")[1]);
                int pocetZen = Integer.parseInt(parts[4].split(": ")[1]);

                Obec obec = new Obec(cisloKraje, psc, nazevObce, pocetMuzu, pocetZen);
                list.vloz(obec);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }
}
