import java.util.Scanner;

public class Gracz {
    Plansza planszaPrzeciwnika;
    Plansza planszaWypisywana;

    public Gracz()
    {
        this.planszaPrzeciwnika = new Plansza();
        Statki_wszytskie statki = new Statki_wszytskie();
        for(int i=0; i<Stale.ilosc_rodzajow; i++)
        {
            System.out.println("Podaj statki rodzaju" + Stale.dane[i][0] + " o nazwie: " + Stale.nazwy[i]+ ": ");
//            String pom = Stale.nazwy[i];
//            System.out.println("pom: " +pom);
            //Statki_rodzaj pom = new Statki_rodzaj(Stale.rodzaje[i][1], Stale.rodzaje[i][2]);
            for(int j = 0; j<Stale.dane[i][2]; j++)
            {
                System.out.println("napisz wsporzedne "+ Stale.nazwy[i]);
                int x,y,z;
                Boolean zz;
                Scanner scanner = new Scanner(System.in);
                x= scanner.nextInt(); //tutaj to powinno byc char!!!!!
                y= scanner.nextInt();
                System.out.println("wpisz 1 jak ma byc pionowo, 0 wpp");
                z= scanner.nextInt(); //true- pionowo
                if(z==1) zz=true; else zz=false;
                Statek aktualnyStatek = statki.wszystkie[i].rodzaj[j];
                aktualnyStatek = new Statek(x,y,Stale.dane[i][0],zz);
                while(!aktualnyStatek.sprawdzStatek(this.planszaPrzeciwnika)){
                    System.out.println("Podaj nowe wspolrzedne");
                    aktualnyStatek.x= scanner.nextInt();
                    aktualnyStatek.y= scanner.nextInt();
                    System.out.println("wpisz 1 jak ma byc pionowo, 0 wpp");
                    z= scanner.nextInt(); //true- pionowo
                    if(z==1) aktualnyStatek.czyPionowo=true; else aktualnyStatek.czyPionowo=false;

//                    System.out.println("wypisuje: ");
//                    planszaPrzeciwnika.wypiszPlansze();
                }
//                aktualnyStatek = new Statek(x,y,Stale.dane[i][0],zz);
                aktualnyStatek.wpiszPola(this.planszaPrzeciwnika);
                aktualnyStatek.wstawStatek(this.planszaPrzeciwnika);

                this.planszaPrzeciwnika.wypiszPlansze();
            }
        }
    }
}
