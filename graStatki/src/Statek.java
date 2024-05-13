import java.util.Scanner;

public class Statek {
        private int dlugosc;
        public int [] pola;
        public int indeks;
        boolean czyZbity = false;
        static int x;
        static int y;
        boolean czyPionowo;

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
                    if(plansza.plansza[x][y+i] != -2) return false;

                }
            }else{
                for(int i=0;i<dlugosc;i++){
                    if(x+i>=10 || x+i<0)
                        return false;
                    if(plansza.plansza[x+i][y] != -2) return false;
                }
            }
            return true;
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
        public void wstawStatek(Plansza plansza){
            if(!sprawdzStatek(plansza))
                return;

            if(!czyPionowo) {
                //poziomo
                for (int i = 0; i < dlugosc; i++) {
                    plansza.ustawNaPlanszy(x + i, y, indeks);
                    if(y>0)  plansza.ustawNaPlanszy(x + i, y - 1, -1);
                    if(y<9)  plansza.ustawNaPlanszy(x + i, y + 1, -1);
                }
                for(int i=0;i<3;i++)
                {
                    if(x-1>=0 && x-1<10 && y-1+i>=0 && y-1+i<10) plansza.ustawNaPlanszy(x -1, y-1+i, -1);
                    if(x+dlugosc>=0 && x+dlugosc<10 && y-1+i>=0 && y-1+i<10) plansza.ustawNaPlanszy(x +dlugosc, y-1+i, -1);
                }
            } else {
                for(int i=0; i<dlugosc;i++) {
                    plansza.ustawNaPlanszy(x, y + i, indeks);
                    if(x>0)  plansza.ustawNaPlanszy(x -1, y +i, -1);
                    if(x<9)  plansza.ustawNaPlanszy(x +1, y +i, -1);
                }
                for(int i=0;i<3;i++)
                {
                    if(x-1+i>=0 && x-1+i<10 && y-1>=0 && y-1<10) plansza.ustawNaPlanszy(x -1+i, y-1, -1);
                    if(x+i-1>=0 && x+i-1<10 && y+dlugosc>=0 && y+dlugosc<10) plansza.ustawNaPlanszy(x +i-1, y+dlugosc, -1);
                }
            }
        }


    public static void main(String[] args) {
        System.out.println("TEST klasy Statek");
        Plansza plansza = new Plansza();
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
