package statki;

import java.util.Scanner;

/*public class Gracz {
    Plansza planszaPrzeciwnika;
    Plansza planszaWypisywana;
    Statki_wszytskie statki;

    public Gracz()
    {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new Statki_wszytskie();
        for(int i=0; i<Stale.ilosc_rodzajow; i++)
        {
//            System.out.println("Podaj statki rodzaju" + Stale.dane[i][0] + " o nazwie: " + Stale.nazwy[i]+ ": ");
//            String pom = Stale.nazwy[i];
//            System.out.println("pom: " +pom);
            //Statki_rodzaj pom = new Statki_rodzaj(Stale.rodzaje[i][1], Stale.rodzaje[i][2]);
            for(int j = 0; j<Stale.dane[i][2]; j++)
            {
//                System.out.println("napisz wspolrzedne "+ Stale.nazwy[i]);

                System.out.println("napisz wspolrzedne "+ Stale.nazwy[Stale.dane[i][1]-1]);
                int x,y,z;
                boolean zz=true;
                Scanner scanner = new Scanner(System.in);
                x = scanner.nextInt(); //tutaj to powinno byc char!!!!!
                y = scanner.nextInt();
                if(Stale.dane[i][1]!=1){
                    System.out.println("wpisz 1 jak ma byc pionowo, 0 wpp");
                    z= scanner.nextInt(); //true- pionowo
                    if(z==1) zz=true; else zz=false;}
//                Statek aktualnyStatek = statki.wszystkie[i].rodzaj[j];
//                Statek aktualnyStatek;
//                Statek aktualnyStatek = new Statek(x,y,Stale.dane[i][0],zz);
                Statek aktualnyStatek = new Statek(x,y,Stale.dane[i][1],zz);
                aktualnyStatek.indeks = 10*i+j;
               statki.wszystkie[i].rodzaj[j] = aktualnyStatek;
                while(!aktualnyStatek.sprawdzStatek(this.planszaPrzeciwnika)){
                    System.out.println("Podaj nowe wspolrzedne");
                    aktualnyStatek.x= scanner.nextInt();
                    aktualnyStatek.y= scanner.nextInt();
                    if(Stale.dane[i][1]!=1) {
                        System.out.println("wpisz 1 jak ma byc pionowo, 0 wpp");
                        z = scanner.nextInt(); //true- pionowo
                        if (z == 1) aktualnyStatek.czyPionowo = true;
                        else aktualnyStatek.czyPionowo = false;
                    }
//                    System.out.println("wypisuje: ");
//                    planszaPrzeciwnika.wypiszPlansze();
                }
//                aktualnyStatek = new Statek(x,y,Stale.dane[i][0],zz);
                aktualnyStatek.wpiszPola(this.planszaPrzeciwnika);
                aktualnyStatek.wstawStatek(this.planszaPrzeciwnika);

               // this.planszaPrzeciwnika.wypiszPlansze();
            }
        }
         this.planszaPrzeciwnika.wypiszPlansze();
    }
}*/
public class Gracz {
    Plansza planszaPrzeciwnika;
    Plansza planszaWypisywana;
    Statki_wszytskie statki;
    boolean czyKoniec;
    public String imie;

    //    public Gracz(Plansza planszaPrzeciwnika) {
//        this.planszaPrzeciwnika = planszaPrzeciwnika;
//        this.planszaWypisywana = new Plansza();
//        this.statki = new Statki_wszytskie();
//        //ustawStatki(); // Tutaj wywołujemy metodę ustawiającą statki
//    }
    public Gracz() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new Statki_wszytskie();
       // System.out.println("konstrukcja");
        //ustawStatki(); // Tutaj wywołujemy metodę ustawiającą statki
    }
    public boolean czyTrafnyStrzal( int x, int y) {
        if ((x >= 0 && x < 10 && y >= 0 && y < 10) && this.planszaWypisywana.plansza[x][y] != 1 &&this.planszaWypisywana.plansza[x][y] != 2 ) {
            this.planszaWypisywana.plansza[x][y] = 1;
            if (this.planszaPrzeciwnika.plansza[x][y] >= 0) {
                this.planszaWypisywana.plansza[x][y] = 2;
                int ktory = this.planszaPrzeciwnika.plansza[x][y] % 10;
                //System.out.println("ktory"+ktory);
                int rodzaj = (this.planszaPrzeciwnika.plansza[x][y] - ktory) / 10;
                //rodzaj--;
                System.out.println("ktory: "+ktory+" i rodzaj "+rodzaj);
                for (int i = 0; i < this.statki.wszystkie[rodzaj].dlugosc; i++) {
                    if (this.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {
                        this.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] = -3;
                        this.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = true;
                        for (int j = 0; j < this.statki.wszystkie[rodzaj].dlugosc; j++) {
                            if (this.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j] != -3) {
                                this.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = false;
                                break;
                            }
                        }
                    }
                }
                if (this.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity) {
                    System.out.println("TRAFIONY ZATOPIONY");
                    this.statki.ilosc_aktywnych--;
                    if (this.statki.ilosc_aktywnych == 0) {
                        czyKoniec = true;
                        return false;
                    }
                } else {
                    System.out.println("TRAFIONY NIEZATOPIONY");

                }
                return true;
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
