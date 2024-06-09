package statki.models;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public interface IGracz {

    Plansza wezPlanszePrzeciwnika();
    Plansza wezPlanszeWypisywana();
    StatkiWszytskie wezStatki();
    void ustawImie(String imie);
    String wezImie();
    void dodajPunkt();
    int zwrocIlePunktow();
    void czyStreak(boolean wart);
    public Button[][] buttonsgracz = new Button[10][10];
public int wezLiczbaBomb();
    public void kupBombe();
    public void zuzyjBombe();
    Label getPunktyGracz();
    Label getBombyGracz();
    public void kupSamolot();
    public void zuzyjSamolot();
    public int wezLiczbaSamolotow();

}
