package statki;

import java.util.Scanner;

/*public class Main {
    public static void main(String[] args) {
        System.out.println("Witaj w grze w statki");
        System.out.println("Gracz 1 wpisuje swoje statki:");
        Gracz graczB = new Gracz();
        statki.WyczyscTerminal.clearScreen();
        System.out.println("Gracz 2 wpisuje swoje statki:");
        Gracz graczA = new Gracz();
        statki.WyczyscTerminal.clearScreen();

         Gra gra1 = new Gra(graczA,graczB);

    }
}*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        System.out.println("Witaj w grze w statki");
        System.out.println("Gracz 1 wpisuje swoje statki:");
        Gracz graczB = new Gracz();
        statki.WyczyscTerminal.clearScreen();
        System.out.println("Gracz 2 wpisuje swoje statki:");
        Gracz graczA = new Gracz();
        statki.WyczyscTerminal.clearScreen();
        Gra gra1 = new Gra(graczA,graczB);

    }
}
