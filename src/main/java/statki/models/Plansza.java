package statki.models;

import statki.Stale;

public class Plansza {
    private int[][] plansza;
    public Plansza()
    {
        plansza = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                plansza[i][j] = Stale.puste;
            }
        }
    }

    public void ustawNaPlanszy(int x, int y, int wartość) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            plansza[x][y] = wartość; //!!!
        } else {
            System.out.println("wspolrzedne poza zakresem");
        }
    }


    public boolean sprawdzCzyWolne(){
        boolean odp=false;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(plansza[i][j]==Stale.puste){
                    odp= true;
                }
            }
        }
        return odp;
    }
    public void reset(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                plansza[i][j]=Stale.puste;

            }
        }
    }
    public int wezPunkt(int x, int y){
        return plansza[x][y];
    }
    public void ustaw(int x, int y, int wart){
        plansza[x][y]=wart;
    }


    public static void main(String[] args) {
        Plansza plansza = new Plansza();

    }
}
