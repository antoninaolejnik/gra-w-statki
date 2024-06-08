package statki.models;

import statki.models.Statek;

public class StatkiRodzaj {
    private int dlugosc;
    private int ilosc;
    private Statek[] rodzaj;

    public StatkiRodzaj(int dlugosc, int ilosc)
    {
        this.dlugosc = dlugosc;
        this.ilosc= ilosc;

        this.rodzaj = new Statek[ilosc];
        for (int i = 0; i < ilosc; i++) {
            this.rodzaj[i] = new Statek(dlugosc);
        }
    }
    public int wezDlugosc(){
        return dlugosc;
    }
    public Statek wezRodzaj(int i){
        return rodzaj[i];
    }

}
