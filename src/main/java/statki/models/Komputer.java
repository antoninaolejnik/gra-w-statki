package statki.models;

import javafx.scene.control.Button;
import statki.Stale;

import java.util.Random;

import java.util.Random;

public class Komputer implements IGracz {
    private Plansza planszaPrzeciwnika;
    private Plansza planszaWypisywana;
    private StatkiWszytskie statki;
    private String imie;
    private int punkty;
    private int mnoznik = 1;
    private boolean czyZKolei = false;
    private Random random;

    public Komputer() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        this.imie = Stale.komputer;
        this.random = new Random();
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
    public void generujrand(int x, int y){
        x = random.nextInt(Stale.rozmiarPlanszy);
        y = random.nextInt(Stale.rozmiarPlanszy);
    }



    private int znajdzTypStatku(int dlugosc) {
        if (dlugosc < 2) return dlugosc;
        else if (dlugosc < 4) return dlugosc - 1;
        else if (dlugosc < 7) return dlugosc - 3;
        else return dlugosc - 6;
    }


}
