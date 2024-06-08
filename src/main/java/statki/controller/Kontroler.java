package statki.controller;
import javafx.scene.control.Button;
import statki.Stale;
import statki.models.Gracz;
import statki.view.Komunikator;
import statki.view.Widok;
public class Kontroler {
    Komunikator komunikator= new Komunikator();
    private Widok view;
    private Gracz gracz1;
    private Gracz gracz2;
    private int[] dlugosciStatkow =Stale.dlugosciStatkow;
    private KontrolerGra gameController;


    private UstawianieKontroler ustawianieKontroler;


    public Kontroler(Widok view) {
        this.view = view;
        //int[] dlugosciStatkow = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        this.ustawianieKontroler = new UstawianieKontroler(view, dlugosciStatkow);
        this.gameController = new KontrolerGra(view);
    }

    public void zacznijGre() {
        String imie1 = pobierzImie(Stale.podajImie+Stale.pierwszyGracz);
        gracz1 = wezLubUtworz(imie1);

        String imie2 = pobierzImie(Stale.podajImie+Stale.drugiGracz);
        gracz2 = wezLubUtworz(imie2);

        view.wyswietlPlansze(gracz1);

    }

    private String pobierzImie(String komunikat) {
        return komunikator.zapytajImie(komunikat);
    }

    public void kliknieciePrzycisku(int x, int y, Button button) {
        if (!ustawianieKontroler.pierwszyGraczUstawilStatki()) {
            ustawianieKontroler.kliknieciePrzycisku(x, y, button, gracz1, gracz2);
        } else {
            ustawianieKontroler.kliknieciePrzycisku(x, y, button, gracz1, gracz2);
        }
    }
    private Gracz wezLubUtworz(String imie) {
        return Stale.gracze.computeIfAbsent(imie, Gracz::new);
    }

    public UstawianieKontroler getSetupController() {
        return ustawianieKontroler;
    }

}
