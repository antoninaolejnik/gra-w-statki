import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Witaj w grze w statki");
        System.out.println("Gracz 1 wpisuje swoje statki:");
        Gracz graczB = new Gracz();
        WyczyscTerminal.clearScreen();
        System.out.println("Gracz 2 wpisuje swoje statki:");
        Gracz graczA = new Gracz();
        WyczyscTerminal.clearScreen();



    }
}