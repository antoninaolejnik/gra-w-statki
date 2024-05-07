public class Statek {
        private int dlugosc;
        private int ilosc;
        private int [][] pola;

        public Statek (int ilosc, int dlugosc)
        {
            this.dlugosc = dlugosc;
            this.ilosc = ilosc;
            this.pola = new int [ilosc][dlugosc];
            for(int i =0; i<ilosc;i++)
            {
                for(int j=0; j<dlugosc;j++)
                {
                    this.pola[i][j]=0;
                }
            }
        }

        public void wstawStatek(int x, int y, int strona, int dlugosc, Plansza plansza){
            //czy ja to mam tutaj robic? bo moze juz w tych dziedziczacych
          //  Plansza plansza = new Plansza();
            //System.out.println("A");
            if(strona ==1) {
                //poziomo
                for (int i = 0; i < dlugosc; i++)
                    plansza.ustawPole(x, y+i, 1);
            } else {
                for(int i=0; i<dlugosc;i++)
                    plansza.ustawPole(x+i, y, 1);
            }
        }


    public static void main(String[] args) {
        Plansza plansza = new Plansza();
       // plansza.wypiszPlansze();
        Statek statek = new Statek(1,2);
        statek.wstawStatek(5,0,0,3,plansza);
        plansza.wypiszPlansze();
    }

}
