package statki.controller;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import statki.Stale;
import statki.models.IGracz;
import statki.view.Komunikator;
import statki.view.Widok;
import java.util.Random;

public class KontrolerGraZKomputerem implements KontrolerGry{
    Komunikator komunikator = new Komunikator();
    private Widok view;
    private IGracz aktualnyGracz;
    private IGracz drugi;
    private boolean isCzyKoniec;
    int x;
    int y;
    Random random = new Random();

    public KontrolerGraZKomputerem(Widok view) {
        this.view = view;
        this.isCzyKoniec = false;
    }

    public void rozpocznijGre(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem) {
        wyswietlPlanszeDoStrzelania(gracz1, gracz2, czyZKomputerem);
        aktualnyGracz = gracz2;
        drugi = gracz1;
    }

    private void wyswietlPlanszeDoStrzelania(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem) {
        GridPane gridPane1 = view.tworzPlansze(gracz1, gracz2, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getRowIndex(button), GridPane.getColumnIndex(button), button, gracz1, gracz2, czyZKomputerem);

        });

        GridPane gridPane2 = view.tworzPlansze(gracz2, gracz1, event -> {
        });
        view.wyswietlPlanszeDoStrzelania(gridPane1, gridPane2);
    }

    private void strzelaniePrzycisk(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik, boolean czyZKomputerem) {
        if (isCzyKoniec) return;
        if (aktualnyGracz == przeciwnik) {
            if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) >= 0) {

                jedenStrzal(x, y, button, strzelajacy, przeciwnik,czyZKomputerem);
            } else {
                nietrafiony(button, strzelajacy,przeciwnik);
                if (czyZKomputerem) {
                    komputerowyRuch(przeciwnik, strzelajacy);
                }
            }
        } else {
            nietrafiony(button,strzelajacy,przeciwnik);
        }

    }

    private void komputerowyRuch(IGracz komputer, IGracz przeciwnik) {
        do {
            x = random.nextInt(Stale.rozmiarPlanszy);
            y = random.nextInt(Stale.rozmiarPlanszy);
        } while (komputer.wezPlanszeWypisywana().wezPunkt(x, y) != Stale.puste2);
        komputer.wezPlanszeWypisywana().ustawNaPlanszy(x,y,Stale.obok);
        Button button = view.wezButton(y, x);
        if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) >= 0) {
            strzelaniePrzycisk(x, y, button, komputer, przeciwnik, true);
            komputerowyRuch(komputer,przeciwnik);
        } else {
            strzelaniePrzycisk(x, y, button, komputer, przeciwnik, false);
        }
    }
    private void jedenStrzal(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik, boolean czyZKomputerem) {
        button.setStyle(Stale.kolorZestrzelony);
        strzelajacy.dodajPunkt();
        strzelajacy.czyStreak(true);
        aktualizujStatki(x, y, strzelajacy, przeciwnik);

        button.setDisable(true);
    }

    private void aktualizujStatki(int x, int y, IGracz strzelajacy, IGracz przeciwnik) {
        int ktory = przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) % 10;
        int rodzaj = (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) - ktory) / 10 - 1;

        for (int i = 0; i < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); i++) {
            if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {
                przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i, Stale.puste2);
                przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);

                if (!sprawdzCzyZbity(przeciwnik, rodzaj, ktory)) {
                    przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
                }

                if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                    przeciwnik.wezStatki().ilosc_aktywnych--;
                    if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                        isCzyKoniec = true;
                        view.wyswietlKomunikat(Stale.koniecGry + drugi.wezImie());
                        view.endGame();
                    }
                }
            }
        }
    }

    private boolean sprawdzCzyZbity(IGracz przeciwnik, int rodzaj, int ktory) {
        for (int j = 0; j < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); j++) {
            if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != Stale.puste2) {
                return false;
            }
        }
        return true;
    }

    private void nietrafiony(Button button, IGracz strzelajacy, IGracz przeciwnik) {
        strzelajacy.czyStreak(false);
        aktualnyGracz = strzelajacy;
        drugi=przeciwnik;
        button.setStyle(Stale.kolorNiezestrzelony);
        button.setDisable(true);
    }


}

