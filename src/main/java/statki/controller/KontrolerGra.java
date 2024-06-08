package statki.controller;


import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import statki.Stale;
import statki.models.Gracz;
import statki.view.Komunikator;
import statki.view.Widok;


public class KontrolerGra {
    Komunikator komunikator=new Komunikator();
    private Widok view;
    private Gracz aktualnyGracz;
    private Gracz drugi;
    private boolean isCzyKoniec;

    public KontrolerGra(Widok view) {
        this.view = view;
        this.isCzyKoniec = false;
    }

    public void rozpocznijGre(Gracz gracz1, Gracz gracz2) {
        wyswietlPlanszeDoStrzelania(gracz1, gracz2);
        aktualnyGracz = gracz2;
        drugi = gracz1;
    }
    private void wyswietlPlanszeDoStrzelania(Gracz gracz1, Gracz gracz2) {
        GridPane gridPane1 = view.tworzPlansze(gracz1, gracz2, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getRowIndex(button), GridPane.getColumnIndex(button), button, gracz1, gracz2);
        });

        GridPane gridPane2 = view.tworzPlansze(gracz2, gracz1, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getRowIndex(button), GridPane.getColumnIndex(button), button, gracz2, gracz1);
        });

        view.wyswietlPlanszeDoStrzelania(gridPane1, gridPane2);
    }



    private void strzelaniePrzycisk(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        if (!isCzyKoniec) {
            if (aktualnyGracz == przeciwnik) {
                if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) >= 0) {
                    jedenStrzal(x, y, button, strzelajacy, przeciwnik);
                } else {
                    nietrafiony(button, strzelajacy);
                }
            } else {
                komunikator.wyswietlCustomAlert(Stale.ruchDrugiego, 5);
            }
        }
    }

    private void jedenStrzal(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        button.setStyle(Stale.kolorZestrzelony);
        strzelajacy.dodajPunkt();
        strzelajacy.czyStreak(true);
        komunikator.wyswietlCustomAlert(strzelajacy.wezImie() + Stale.wypiszPunkty + strzelajacy.zwrocIlePunktow(), 5);
        int ktory = przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) % 10;
        int rodzaj = (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) - ktory) / 10 - 1;

        for (int i = 0; i < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); i++) {
            if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {
                przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i, Stale.puste2);
                przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);

                for (int j = 0; j < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); j++) {
                    if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != Stale.puste2) {
                        przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
                        break;
                    }
                }

                if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                    komunikator.wyswietlCustomAlert(Stale.trafionyZatopiony, 3);
                    przeciwnik.wezStatki().ilosc_aktywnych--;
                    if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                        isCzyKoniec = true;
                        komunikator.wyswietlCustomAlert(Stale.koniecGry + drugi.wezImie(), 5);
                    }
                }
            }
        }

        button.setDisable(true);
    }

    private void nietrafiony(Button button, Gracz strzelajacy) {
        strzelajacy.czyStreak(false);
        aktualnyGracz = strzelajacy;
        button.setStyle(Stale.kolorNiezestrzelony);
        button.setDisable(true);
    }
}


