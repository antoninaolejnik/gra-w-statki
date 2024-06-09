package statki.models;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @Override
    public int wezLiczbaBomb() {
        return 0;
    }

    @Override
    public void kupBombe() {

    }

    @Override
    public void zuzyjBombe() {

    }

    @Override
    public Label getPunktyGracz() {
        return null;
    }

    @Override
    public Label getBombyGracz() {
        return null;
    }

    @Override
    public void kupSamolot() {

    }

    @Override
    public void zuzyjSamolot() {

    }

    @Override
    public int wezLiczbaSamolotow() {
        return 0;
    }

    public void generujrand(int x, int y){
        x = random.nextInt(Stale.rozmiarPlanszy);
        y = random.nextInt(Stale.rozmiarPlanszy);
    }





}