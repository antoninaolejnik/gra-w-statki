import java.util.Scanner;

public class Gra {
    private boolean czyKoniec = false;
    Gracz graczA;
    Gracz graczB;
    public Gra(Gracz graczA, Gracz graczB) {
        this.graczA = graczA;
        this.graczB = graczB;
        while (!czyKoniec) {
            strzal(graczA);
            if(!czyKoniec)
            {
                strzal(graczB);
            } else {break;}
        }
        //koniec
    }


    public void strzal (Gracz gracz)
    {
        int x, y,z;
        Scanner scanner = new Scanner(System.in);
        x= scanner.nextInt();
        y=scanner.nextInt();


       // gracz.planszaPrzeciwnika

    }

    public boolean czyTrafnyStrzal (Gracz gracz, int x, int y)
    {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            //if(gracz.planszaPrzeciwnika[x][y] == 1)
        } else {
            return true;
        }
    }

}
