import java.util.Scanner;

public class Statek {
        private int dlugosc;
        private int ilosc;
        private int [][] pola;
        boolean czyZbity;
        //wspolrzedne poczatku
        static int x;
        static int y;
        boolean czyPionowo;

        public Statek (int x, int y, int ilosc, int dlugosc, boolean czyPionowo)
        {
            this.x=x;
            this.y=y;
            this.dlugosc = dlugosc;
            this.ilosc = ilosc;
            this.czyPionowo=czyPionowo;
            this.pola = new int [ilosc][dlugosc];
            for(int i =0; i<ilosc;i++)
            {
                for(int j=0; j<dlugosc;j++)
                {
                    this.pola[i][j]=0;
                }
            }
        }
        public boolean sprawdzStatek(Plansza plansza){
            if(czyPionowo){
                for(int i=0;i<dlugosc;i++){
                    if(x+i>=10)
                        return false;
                }
            }else{
                for(int i=0;i<dlugosc;i++){
                    if(y+i>=10)
                        return false;
                }
            }
            return true;
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
        Statek statek = new Statek(9,9,1,2, true);
        //tak bym chciala zeby to wygladalo w trakcie gry juz czyli dopoki uzytkwonik nie wpisze dobrej kratki caly czas pytamy
        //w ogole to w klasie jak mamy pola to nie musimy ich podawac do funkcji bo ona bedzie stricte dotyczyla statku np wstawStatek
        //to ona wie ktory juz
        //dodalam metode ktora przed sprawdza czy mozemy wstawic bo np jak w srodku nam wypisze ze nie mozna to tablica juz zostanie zmieniona
        //i mamy problem a tak to wczesniej sobie sprawdza
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
