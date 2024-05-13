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
            gracz.planszaWypisywana.wypiszPlansze();

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
        System.out.println("czytraf"+gracz.planszaWypisywana.plansza[x][y]);
        if ((x >= 0 && x < 10 && y >= 0 && y < 10) && gracz.planszaWypisywana.plansza[x][y] != 1 ) {
//            System.out.println("if");
            gracz.planszaWypisywana.plansza[x][y] = 1;
            if (gracz.planszaPrzeciwnika.plansza[x][y] >= 0) {
                //trafione cos
//                System.out.println("trafcos");
                gracz.planszaWypisywana.plansza[x][y] = 2;
                int ktory = gracz.planszaPrzeciwnika.plansza[x][y] % 10;
                int rodzaj = (gracz.planszaPrzeciwnika.plansza[x][y] - ktory) / 10;
                //System.out.println("to dlugie cos: " +gracz.statki.wszystkie[rodzaj].dlugosc);
//                System.out.println("iff0: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[0]);
//                System.out.println("iff1: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[1]);

                for (int i = 0; i < gracz.statki.wszystkie[rodzaj].dlugosc; i++) {
                    System.out.println("iffa: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);

                    System.out.println("iffC: "+10 * y + x);
//                    if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {
                    if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {
                        System.out.println("iffB: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);
                        gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] = -3;
                        gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = true;
//                        boolean pom = true;
                        for (int j = 0; j < gracz.statki.wszystkie[rodzaj].dlugosc; j++) {
                            //System.out.println("iff: "+gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j]);
                            if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j] != -3) {
                                System.out.println("zmiana czyzbity na false: "+ gracz.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j]);
                                gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = false;
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
                if (gracz.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity) {
                    System.out.println("TRAFIONY ZATOPIONY");
                    //zbilismy caly statek
//                    System.out.println("czyzbity: "+gracz.statki.ilosc_aktywnych);
                    gracz.statki.ilosc_aktywnych--;
                    if (gracz.statki.ilosc_aktywnych == 0) {
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
