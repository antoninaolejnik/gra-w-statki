import java.util.Scanner;

public class Gra {
    public boolean czyKoniec = false;
    Gracz graczA;
    Gracz graczB;

    public Gra(Gracz graczA, Gracz graczB) {
        this.graczA = graczA;
        this.graczB = graczB;
        while (!czyKoniec) {
            System.out.println("strzal graczA");
            strzal(graczA);
            if (!czyKoniec) {
                System.out.println("strzal graczB");
                strzal(graczB);
            } else {
                break;
            }
        }
        System.out.println("KONIEC");
    }


    public void strzal(Gracz gracz) {
        int x, y;
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
        y = scanner.nextInt();
        while (czyTrafnyStrzal(gracz, x, y)) {
            System.out.println("plansza wypisywana");
            gracz.planszaWypisywana.wypiszPlansze();

            x = scanner.nextInt();
            y = scanner.nextInt();
        }
    }


    public boolean czyTrafnyStrzal(Gracz gracz, int x, int y) {
        if ((x >= 0 && x < 10 && y >= 0 && y < 10) && gracz.planszaWypisywana.plansza[x][y] != 1 && gracz.planszaWypisywana.plansza[x][y] != 2 ) {
            gracz.planszaWypisywana.plansza[x][y] = 1;
            if (gracz.planszaPrzeciwnika.plansza[x][y] >= 0) {
                gracz.planszaWypisywana.plansza[x][y] = 2;
                int ktory = gracz.planszaPrzeciwnika.plansza[x][y] % 10;
                int rodzaj = (gracz.planszaPrzeciwnika.plansza[x][y] - ktory) / 10;

                for (int i = 0; i < gracz.statki.wszystkie[rodzaj].dlugosc; i++) {
                    if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {
                        gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] = -3;
                        gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = true;
                        for (int j = 0; j < gracz.statki.wszystkie[rodzaj].dlugosc; j++) {
                            if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j] != -3) {
                                gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = false;
                                break;
                            }
                        }
                    }
                }
                if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity) {
                    System.out.println("TRAFIONY ZATOPIONY");
                    gracz.statki.ilosc_aktywnych--;
                    if (gracz.statki.ilosc_aktywnych == 0) {
                        czyKoniec = true;
                        return false;
                    }
                } else {
                    System.out.println("TRAFIONY NIEZATOPIONY");

                }
                return true;
            } else {
                System.out.println("NIETRAFIONY");
                return false;
            }
        } else {
            System.out.println("nieprawidlowe pole, sprobuj jeszcze raz");
            return true;
        }
    }

}
