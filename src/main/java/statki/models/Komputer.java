package statki.models;

import javafx.scene.control.Button;
import statki.Stale;



public class Komputer implements IGracz {
    private Plansza planszaPrzeciwnika;
    private Plansza planszaWypisywana;
    private StatkiWszytskie statki;
    private String imie;
    private int punkty;
    private int mnoznik = 1;//aktualny stan mnoznika
    private boolean czyZKolei = false;
    private Button[][] buttonsGracz;

    public Komputer() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        this.imie = Stale.komputer;
        punkty = 0;
    }

    public Plansza wezPlanszePrzeciwnika() {
        return planszaPrzeciwnika;
    }

    public Plansza wezPlanszeWypisywana() {
        return planszaWypisywana;
    }

    public StatkiWszytskie wezStatki() {
        return statki;
    }

    public void ustawImie(String imie) {
        this.imie = imie;
    }

    public String wezImie() {
        return imie;
    }

    public void dodajPunkt() {
        if (czyZKolei) {
            mnoznik *= Stale.mnoznik;
            punkty += mnoznik;
        } else {
            punkty++;
        }
    }

    public int zwrocIlePunktow() {
        return punkty;
    }

    public void czyStreak(boolean wart) {
        if (!wart) {
            mnoznik = 1;
        }
        czyZKolei = wart;
    }



    @Override
    public Button[][] getButtonsGracz() {
        return new Button[0][];
    }


    @Override
    public void dopiszButtons(){
        this.buttonsGracz=new Button[10][10];
    }





}