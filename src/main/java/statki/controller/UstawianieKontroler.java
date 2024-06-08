package statki.controller;

import javafx.scene.control.Button;
import statki.Stale;
import statki.models.Gracz;
import statki.models.Statek;
import statki.view.Komunikator;
import statki.view.Widok;

public class UstawianieKontroler {
    Komunikator komunikator=new Komunikator();
    private Widok view;
    private boolean pierwszyGraczUstawilStatki;
    private int[] dlugosciStatkow;
    private int pom;
    private boolean czyPionowo;
    private int dlugoscStatku;

    public UstawianieKontroler(Widok view, int[] dlugosciStatkow) {
        this.view = view;
        this.dlugosciStatkow = dlugosciStatkow;
        this.pom = 0;
        this.czyPionowo = false;
        this.pierwszyGraczUstawilStatki = false;
    }

    private boolean czyPlanszaJestPelna(Gracz gracz) {
        if (!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()) {
            komunikator.wyswietlCustomAlert(Stale.reset, 5);
            gracz.wezPlanszeWypisywana().reset();
            pom = 0;
            return true;
        }
        return false;
    }

    private boolean ustawStatekNaPlanszy(int x, int y, Gracz gracz, int pom) {
        if (gracz.wezPlanszeWypisywana().wezPunkt(x, y) == Stale.puste) {
            if (pom == 10) {
                pierwszyGraczUstawilStatki = true;
                this.pom = 0;
                view.wyswietlPlansze(gracz);
                czyPionowo = false;
                return true;
            }

            dlugoscStatku = dlugosciStatkow[pom];
            int t = znajdzTypStatku(pom);
            Statek aktualnystatek = gracz.wezStatki().wszystkie[dlugoscStatku - 1].wezRodzaj(t);
            aktualnystatek.ustawX(x);
            aktualnystatek.ustawY(y);
            aktualnystatek.ustawOrient(czyPionowo);
            aktualnystatek.ustawIndeks(10 * dlugoscStatku + t);

            if (aktualnystatek.sprawdzStatek(gracz.wezPlanszePrzeciwnika())) {
                this.pom++;
                aktualnystatek.wstawStatek(gracz.wezPlanszePrzeciwnika());
                aktualnystatek.wpiszPola(gracz.wezPlanszePrzeciwnika());
                view.aktualizujPlansze(gracz.wezPlanszePrzeciwnika());
                return true;
            } else {
                komunikator.wyswietlCustomAlert(Stale.zleMiejsce, 5);
            }
        } else {
            komunikator.wyswietlCustomAlert(Stale.zleMiejsce, 5);
        }
        return false;
    }

    public void kliknieciePrzycisku(int x, int y, Button button, Gracz gracz, Gracz gracz2) {
        if (!pierwszyGraczUstawilStatki) {
            if (czyPlanszaJestPelna(gracz)) {
                return;
            }
            if (ustawStatekNaPlanszy(x, y, gracz, pom)) {
                return;
            }
        } else {
            if (gracz2.wezPlanszePrzeciwnika().wezPunkt(x, y) == Stale.puste) {
                if (pom == 10) {
                    this.rozpocznijGre(gracz, gracz2);
                    return;
                }
                if (ustawStatekNaPlanszy(x, y, gracz2, pom)) {
                    return;
                }
            } else {
                komunikator.wyswietlCustomAlert(Stale.zleMiejsce, 5);
            }
        }
    }

    private int znajdzTypStatku(int index) {
        if (index < 1) return index;
        else if (index < 3) return index - 1;
        else if (index < 6) return index - 3;
        else return index - 6;
    }

    private void rozpocznijGre(Gracz gracz1, Gracz gracz2) {
        KontrolerGra mainController = new KontrolerGra(view);
        mainController.rozpocznijGre(gracz1, gracz2);
    }


    public boolean pierwszyGraczUstawilStatki() {
        return pierwszyGraczUstawilStatki;
    }
    public void zmienOrientacje() {
        czyPionowo = !czyPionowo;
        String orientacja = czyPionowo ? Stale.pion : Stale.poziom;
        komunikator.wyswietlCustomAlert(Stale.zmianaOrient + orientacja, 3);
    }
}
