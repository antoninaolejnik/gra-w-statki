package statki.models;

import statki.Stale;

import java.util.Scanner;

public class Statek {
        private int dlugosc;
//        private int ilosc;
//        private int [][] pola;
        private int [] pola;
        private int indeks;
        private boolean czyZbity = false;
        //wspolrzedne poczatku
        private static int x;
        private static int y;
        private boolean czyPionowo;

//        public Statek (int x, int y, int ilosc, int dlugosc, boolean czyPionowo)
//        {
//            this.x=x;
//            this.y=y;
//            this.dlugosc = dlugosc;
//            this.ilosc = ilosc;
//            this.czyPionowo=czyPionowo;
//            this.pola = new int [ilosc][dlugosc];
//            for(int i =0; i<ilosc;i++)
//            {
//                for(int j=0; j<dlugosc;j++)
//                {
//                    this.pola[i][j]=0;
//                }
//            }
//        }
    public Statek(int i){
        this.dlugosc=i;
        this.pola = new int [dlugosc];
        for(int j=0; j<dlugosc;j++)
        {
            this.pola[j]= Stale.puste;
        }

    }
    public Statek (int x, int y, int dlugosc, boolean czyPionowo)
    {
        this.x=x;
        this.y=y;
        this.dlugosc = dlugosc;

        this.czyPionowo=czyPionowo;
        this.pola = new int [dlugosc];
            for(int j=0; j<dlugosc;j++)
            {
                this.pola[j]=0;
            }
    }
        public boolean sprawdzStatek(Plansza plansza){
            if(czyPionowo){
                for(int i=0;i<dlugosc;i++){
                    if(y+i>=10 ||y+i<0)
                        return false;
                    if(plansza.wezPunkt(x,y+i) != Stale.puste) return false;

                }
            }else{
                for(int i=0;i<dlugosc;i++){
                    if(x+i>=10 || x+i<0)
                        return false;
                    if(plansza.wezPunkt(x+i,y) != Stale.puste) return false;
                }
            }
            return true;
        }
        public int wezPole(int x){
        return pola[x];
        }
        public void ustawX(int a){
        x=a;
        }
        public void ustawY(int b){
        y=b;
        }
        public void ustawOrient(boolean cos){
        czyPionowo=cos;
        }
        public void ustawPole(int x,int wart){
        pola[x]=wart;
        }
        public void ustawIndeks(int wart){
        indeks=wart;
        }
        public void wpiszPola(Plansza plansza)
        {
            for(int i=0; i<dlugosc; i++)
            {

//                pola[i]=10*y+x;
//                pola[i]=10*y+x+i;
//                pola[i]=10*x+y+i; //xd
//                System.out.println("pola["+i+"]: " + pola[i]);
//
                if(czyPionowo)
                {
                    pola[i]=10*(y+i)+x;
//                    System.out.println("pionpola["+i+"]: " + pola[i]);
                } else {
                    pola[i]=10*y+x+i;
//                    System.out.println("pozipola["+i+"]: " + pola[i]);
                }
            }

        }
        public void ustawZbity(boolean wart){
        czyZbity=wart;
        }
        public boolean czyJestZbity(){
        return czyZbity;
        }
        public void wstawStatek(Plansza plansza){
            //czy ja to mam tutaj robic? bo moze juz w tych dziedziczacych
          //  Plansza plansza = new Plansza();
            //System.out.println("A");
            //sprawdz statek przyda sb do wpisywaniu przy grze
            if(!sprawdzStatek(plansza))
                return;

            if(!czyPionowo) {
                //poziomo
                for (int i = 0; i < dlugosc; i++) {
                    plansza.ustawNaPlanszy(x + i, y, indeks);
                    if(y>0)  plansza.ustawNaPlanszy(x + i, y - 1, Stale.obok);
                    if(y<9)  plansza.ustawNaPlanszy(x + i, y + 1, Stale.obok);
                }
                for(int i=0;i<3;i++)
                {
                    if(x-1>=0 && x-1<10 && y-1+i>=0 && y-1+i<10) plansza.ustawNaPlanszy(x -1, y-1+i, Stale.obok);
                    if(x+dlugosc>=0 && x+dlugosc<10 && y-1+i>=0 && y-1+i<10) plansza.ustawNaPlanszy(x +dlugosc, y-1+i, Stale.obok);
                }
            } else {
                for(int i=0; i<dlugosc;i++) {
                    plansza.ustawNaPlanszy(x, y + i, indeks);
                    if(x>0)  plansza.ustawNaPlanszy(x -1, y +i, Stale.obok);
                    if(x<9)  plansza.ustawNaPlanszy(x +1, y +i, Stale.obok);
                }
                for(int i=0;i<3;i++)
                {
                    if(x-1+i>=0 && x-1+i<10 && y-1>=0 && y-1<10) plansza.ustawNaPlanszy(x -1+i, y-1, Stale.obok);
                    if(x+i-1>=0 && x+i-1<10 && y+dlugosc>=0 && y+dlugosc<10) plansza.ustawNaPlanszy(x +i-1, y+dlugosc, Stale.obok);
                }
            }
        }


    public static void main(String[] args) {
        Plansza plansza = new Plansza();
       // plansza.wypiszPlansze();
//        Statek statek = new Statek(3,2,1,2, true);

        Statek statek = new Statek(0,0,4, false);
        while(!statek.sprawdzStatek(plansza)){
            System.out.println("Podaj nowe wspolrzedne");
            Scanner scanner= new Scanner(System.in);
            x=scanner.nextInt();
            y= scanner.nextInt();
        }
        statek.wstawStatek(plansza);
        plansza.wypiszPlansze();
    }

}
