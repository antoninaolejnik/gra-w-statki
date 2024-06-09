package statki.models;

public interface IGracz {
    Plansza wezPlanszePrzeciwnika();
    Plansza wezPlanszeWypisywana();
    StatkiWszytskie wezStatki();
    void ustawImie(String imie);
    String wezImie();
    void dodajPunkt();
    int zwrocIlePunktow();
    void czyStreak(boolean wart);
}
