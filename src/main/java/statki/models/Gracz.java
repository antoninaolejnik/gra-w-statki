package statki.models;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import statki.Stale;

public class Gracz implements IGracz{
    private Plansza planszaPrzeciwnika;
    private Plansza planszaWypisywana;
    private StatkiWszytskie statki;
    public String imie;
    private int punkty;
    private int mnoznik=1;
    private boolean czyZKolei=false;
   // public Button[][] buttonsgracz;
   private Button[][] buttonsGracz;
    private int liczbaBomb = 0;
    public Label punktyGracz;
    public Label bombyGracz;


    public Gracz() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;
        this.buttonsGracz = new Button[10][10];

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

    public int wezLiczbaBomb() {
        return liczbaBomb;
    }
    public void kupBombe() {
        if (punkty >= 15) {
            liczbaBomb++;
            punkty -= 15;
        } else {
            throw new IllegalStateException("Za mało punktów, aby kupić bombę!");
        }
    }
    public void zuzyjBombe() {
        if (liczbaBomb > 0) {
            liczbaBomb--;
        }
    }

    @Override
    public Label getPunktyGracz() {
        return null;
    }

    @Override
    public Label getBombyGracz() {
        return null;
    }

}
