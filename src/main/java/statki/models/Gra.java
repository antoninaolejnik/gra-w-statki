package statki.models;

import java.util.Scanner;

public class Gra {
     private boolean czyKoniec = false;
    private Gracz graczA;
    private Gracz graczB;

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
        //koniec
        System.out.println("KONIEC");
    }


    public void strzal(Gracz gracz) {
//        System.out.println("strzal gracz");
        int x, y;
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
        y = scanner.nextInt();
//        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
//            gracz.planszaWypisywana.plansza[x][y] = 1;
//        }
        while (czyTrafnyStrzal(gracz, x, y)) {
//            System.out.println("plansza przeciwnika");
//            gracz.planszaPrzeciwnika.wypiszPlansze();
            System.out.println("plansza wypisywana");
            gracz.wezPlanszeWypisywana().wypiszPlansze();

//            if (x >= 0 && x < 10 && y >= 0 && y < 10) {
//                gracz.planszaWypisywana.plansza[x][y] = 1;
//            }
            x = scanner.nextInt();
            y = scanner.nextInt();
        }
    }
    //jak wyjdzie to nie trafilismy w statek lub skonczyla sie gra


//    public boolean czyTrafnyStrzal (Gracz gracz, int x, int y)
//    {
//        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
//            if(gracz.planszaPrzeciwnika.plansza[x][y] == 1){
//
//            }
//        } else {
//            return true;
//        }
//        return false;
//    }


    public boolean czyTrafnyStrzal(Gracz gracz, int x, int y) {
//        System.out.println("czytraf"+gracz.planszaWypisywana.plansza[x][y]);
        if ((x >= 0 && x < 10 && y >= 0 && y < 10) && gracz.wezPlanszeWypisywana().wezPunkt(x,y) != 1 &&gracz.wezPlanszeWypisywana().wezPunkt(x,y) != 2 ) {
//            System.out.println("if");
            gracz.wezPlanszeWypisywana().ustaw(x,y,1);
            if (gracz.wezPlanszePrzeciwnika().wezPunkt(x,y) >= 0) {
                //trafione cos
//                System.out.println("trafcos");
                gracz.wezPlanszeWypisywana().ustaw(x,y,2); //????
                int ktory = gracz.wezPlanszePrzeciwnika().wezPunkt(x,y) % 10;
                int rodzaj = (gracz.wezPlanszePrzeciwnika().wezPunkt(x,y) - ktory) / 10;
                //System.out.println("to dlugie cos: " +gracz.statki.wszystkie[rodzaj].dlugosc);
//                System.out.println("iff0: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[0]);
//                System.out.println("iff1: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[1]);

                for (int i = 0; i < gracz.wezStatki().wszystkie[rodzaj].wezDlugosc(); i++) {
//                    System.out.println("iffa: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);
//
//                    System.out.println("iffC: "+10 * y + x);
//                    if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {
                    if (gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {
//                        System.out.println("iffB: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);
                        gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i,-3);
                        gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);
//                        boolean pom = true;
                        for (int j = 0; j < gracz.wezStatki().wszystkie[rodzaj].wezDlugosc(); j++) {
                            //System.out.println("iff: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j]);
                            if (gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != -3) {
//                                System.out.println("zmiana czyzbity na false: "+ gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j]);
                                gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
//                                pom=false;
                                break; //??
                            }
                        }
//                        if(!gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity && !pom)
//                        {
//                            pom = true;
//                            break;
//                        }
                    }
                }
//                System.out.println("czy czyzbity jest false: "+ gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity);
                if (gracz.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                    System.out.println("TRAFIONY ZATOPIONY");
                    //zbilismy caly statek
//                    System.out.println("czyzbity: "+gracz.statki.ilosc_aktywnych);
                    gracz.wezStatki().ilosc_aktywnych--;
                    if (gracz.wezStatki().ilosc_aktywnych == 0) {
                        //koniec gry
                        czyKoniec = true;
                        return false;
                    }
                } else {
                    System.out.println("TRAFIONY NIEZATOPIONY");

                }
                return true; //????
            } else {
                System.out.println("NIETRAFIONY");
                return false; //nie trafilysmy w statek
            }
        } else {
            System.out.println("nieprawidlowe pole, sprobuj jeszcze raz");
            return true;
        }
    }

}
