package statki.controller;

import javafx.scene.control.Button;
import statki.Stale;
import statki.models.Gracz;
import statki.models.IGracz;
import statki.models.Statek;
import statki.view.Komunikator;
import statki.view.Widok;

public class UstawianieKontroler {
    Komunikator komunikator=new Komunikator();
    private Widok widok;
    private boolean pierwszyGraczUstawilStatki;
    private int[] dlugosciStatkow;
    int pom;//liczenie ktory statek teraz rozwazamy
    private boolean czyPionowo;
    private int dlugoscStatku;
    private boolean czyZKomputerem;

    public UstawianieKontroler(Widok view, int[] dlugosciStatkow, boolean czyZKomputerem) {
        this.widok = view;
        this.czyZKomputerem=czyZKomputerem;
        this.dlugosciStatkow = dlugosciStatkow;
        this.pom = 0;
        this.czyPionowo = false;
        this.pierwszyGraczUstawilStatki = false;
    }

    private boolean czyPlanszaJestPelna(IGracz gracz) {
        if (!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()) {
            komunikator.wyswietlCustomAlert(Stale.reset, Stale.sekundy);
            gracz.wezPlanszeWypisywana().reset();
            pom = 0;
            return true;
        }
        return false;
    }

    private boolean ustawStatekNaPlanszy(int x, int y, IGracz gracz, int pom) {
        if (gracz.wezPlanszeWypisywana().wezPunkt(x, y) == Stale.puste) {
            if (pom == Stale.iloscStatkow) {
                pierwszyGraczUstawilStatki = true;
                this.pom = 0;
                widok.wyswietlPlansze(gracz);
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
                widok.aktualizujPlansze(gracz.wezPlanszePrzeciwnika());
                return true;
            } else {
                if(!czyZKomputerem) {
                    if (gracz instanceof Gracz)
                        komunikator.wyswietlCustomAlert(Stale.zleMiejsce, Stale.sekundy);
                }
            }
        } else {
            if(!czyZKomputerem) {
                if (gracz instanceof Gracz)
                    komunikator.wyswietlCustomAlert(Stale.zleMiejsce, Stale.sekundy);
            }
        }
        return false;
    }

    public void kliknieciePrzycisku(int x, int y, Button button, IGracz gracz, IGracz gracz2) {
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
            }

        }
    }

    private int znajdzTypStatku(int index) {
        if (index < 1) return index;
        else if (index < 3) return index - 1;
        else if (index < 6) return index - 3;
        else return index - 6;
    }

    public void rozpocznijGre(IGracz gracz1, IGracz gracz2) {
        KontrolerGraZKomputerem mainController = new KontrolerGraZKomputerem(widok);
        KontrolerGrazPrzyjacielem mainController2 = new KontrolerGrazPrzyjacielem(widok,(Gracz)gracz1,(Gracz)gracz2);
        if(czyZKomputerem) mainController.rozpocznijGre(gracz1, gracz2, czyZKomputerem);
        else mainController2.rozpocznijGre(gracz1, gracz2, czyZKomputerem);
    }


    public boolean pierwszyGraczUstawilStatki() {
        return pierwszyGraczUstawilStatki;
    }
    public void zmienOrientacje() {
        czyPionowo = !czyPionowo;
        String orientacja = czyPionowo ? Stale.pion : Stale.poziom;
        komunikator.wyswietlCustomAlert(orientacja, Stale.sekundy);
    }
}
