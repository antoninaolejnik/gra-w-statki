package statki.models;

import statki.Stale;

import java.util.Scanner;

public class Statek {
        private int dlugosc;

        private int [] pola;
        private int indeks;
        private boolean czyZbity = false;
        private static int x;
        private static int y;
        private boolean czyPionowo;


    public Statek(int i){
        this.dlugosc=i;
        this.pola = new int [dlugosc];
        for(int j=0; j<dlugosc;j++)
        {
            this.pola[j]= Stale.puste;
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


                if(czyPionowo)
                {
                    pola[i]=10*(y+i)+x;
                } else {
                    pola[i]=10*y+x+i;
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
           if(!sprawdzStatek(plansza))
                return;

            if(!czyPionowo) {
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




}
