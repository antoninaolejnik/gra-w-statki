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
    private Button[][] buttonsGracz;
    private int liczbaBomb = 0;
    public Label punktyGracz;
    public Label bombyGracz;

    private int liczbaSamolotow = 0;
    public boolean czyStrzelanie=false;
    public boolean czyStrzelanieSamolot=false;
    public Label samolotyGracz = new Label();

    public Gracz() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;
        this.buttonsGracz = new Button[Stale.rozmiarPlanszy][Stale.rozmiarPlanszy];

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
    public Button[][] getButtonsGracz() {
        return buttonsGracz;
    }

    public Label getSamolotyGracz() {
        return samolotyGracz;
    }
    public void wlaczStrzelanie(){
        czyStrzelanie=true;
    }
    public void wylaczStrzelanie(){
        czyStrzelanie=false;
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
        if (punkty >= Stale.cenaBomby) {
            liczbaBomb++;
            punkty -= Stale.cenaBomby;
        }
    }
    public void zuzyjBombe() {
        if (liczbaBomb > 0) {
            liczbaBomb--;
        }
    }

    public Label getPunktyGracz() {
        return null;
    }

    public Label getBombyGracz() {
        return null;
    }

    public void kupSamolot() {
        if (punkty >= 80) {
            punkty -= 80;
            liczbaSamolotow++;
        } else {
           return;
        }
    }
    public void zuzyjSamolot() {
        if (liczbaSamolotow > 0) {
            liczbaSamolotow--;
        } else {
            return;
        }
    }

    public int wezLiczbaSamolotow() {
        return liczbaSamolotow;
    }
    @Override
    public void dopiszButtons(){
        this.buttonsGracz=new Button[10][10];
    }
//    public Button jedenButton(int x, int y){
//        return this.buttonsGracz[x][y];
//    }
public Button jedenButton(int x, int y){
    return this.buttonsGracz[y][x];
}
    public void wylaczStrzelanieSamolot() {
        czyStrzelanieSamolot = false;
    }
    public void wlaczStrzelanieSamolot() {
        czyStrzelanieSamolot = true;
    }
}
