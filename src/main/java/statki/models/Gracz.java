package statki.models;


import statki.Stale;

public class Gracz implements IGracz{
    private Plansza planszaPrzeciwnika;
    private Plansza planszaWypisywana;
    private StatkiWszytskie statki;
    private String imie;
    private int punkty;
    private int mnoznik=1;
    private boolean czyZKolei=false;


    public Gracz() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;

    }

    public Gracz(String s) {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;
        this.imie=s;
    }


    public Plansza wezPlanszePrzeciwnika(){
        return planszaPrzeciwnika;
    }
    public Plansza wezPlanszeWypisywana(){
        return planszaWypisywana;
    }
    public StatkiWszytskie wezStatki(){
        return statki;
    }
    public void ustawImie(String imi){
        imie=imi;
    }
    public String wezImie(){
        return imie;
    }
    public void dodajPunkt(){
        if(czyZKolei){
            mnoznik*= Stale.mnoznik;
            punkty+=mnoznik;}
        else
            punkty++;
    }
    public int zwrocIlePunktow(){
        return punkty;
    }
    public void czyStreak(boolean wart){
        if(!wart){
            mnoznik=1;
        }
        czyZKolei=wart;
    }

}
