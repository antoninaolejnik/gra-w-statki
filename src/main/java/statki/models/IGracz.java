package statki.models;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public interface IGracz {

    Plansza wezPlanszePrzeciwnika();
    Plansza wezPlanszeWypisywana();
    StatkiWszytskie wezStatki();

    String wezImie();
    void dodajPunkt();
    int zwrocIlePunktow();
    void czyStreak(boolean wart);
    public Button[][] buttonsgracz = new Button[10][10];


    Button[][] getButtonsGracz();

    void dopiszButtons();
}
