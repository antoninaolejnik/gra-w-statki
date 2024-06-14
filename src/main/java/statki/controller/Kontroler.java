package statki.controller;
import javafx.scene.control.Button;
import statki.Stale;
import statki.models.Gracz;
import statki.models.IGracz;
import statki.models.Komputer;
import statki.view.Komunikator;
import statki.view.Widok;

import java.util.Random;

public class Kontroler {
    Komunikator komunikator= new Komunikator();
    private Widok view;
    private IGracz gracz1;
    private IGracz gracz2;
    private Random random;
    private int[] dlugosciStatkow =Stale.dlugosciStatkow;

    private boolean czyZKomputerem;


    private UstawianieKontroler ustawianieKontroler;


    public Kontroler(Widok view, boolean czyZKomputerem) {
        this.view = view;
        random=new Random();
        this.czyZKomputerem=czyZKomputerem;
        this.ustawianieKontroler = new UstawianieKontroler(view, dlugosciStatkow,czyZKomputerem);
    }

    public void zacznijGre() {
        String imie1 = pobierzImie(Stale.podajImie+Stale.pierwszyGracz);
        gracz1 = wezLubUtworz(imie1);
        if(czyZKomputerem){
            gracz2=new Komputer();
            gracz2 = wezLubUtworz(Stale.komputer);
        }

        else{
            String imie2 = pobierzImie(Stale.podajImie+Stale.drugiGracz);
            while(imie2.equals(imie1)){
                imie2 = pobierzImie(Stale.inneImie);
            }
            gracz2 = wezLubUtworz(imie2);
        }

        view.wyswietlPlansze(gracz1);

    }

    private String pobierzImie(String komunikat) {
        return komunikator.zapytajImie(komunikat);
    }

    public void kliknieciePrzycisku(int x, int y, Button button) {
        if (!ustawianieKontroler.pierwszyGraczUstawilStatki()) {
            ustawianieKontroler.kliknieciePrzycisku(x, y, button, gracz1, gracz2);
        } else {
            if(czyZKomputerem){
                do {
                    x = random.nextInt(Stale.rozmiarPlanszy);
                    y = random.nextInt(Stale.rozmiarPlanszy);
                    ustawianieKontroler.kliknieciePrzycisku(x, y, button, gracz1, gracz2);
                } while (ustawianieKontroler.pom!=Stale.iloscStatkow);
                ustawianieKontroler.rozpocznijGre(gracz1,gracz2);//!!!
            }else
                ustawianieKontroler.kliknieciePrzycisku(x, y, button, gracz1, gracz2);
        }
    }
    private Gracz wezLubUtworz(String imie) {
        return Stale.gracze.computeIfAbsent(imie, Gracz::new);
    }

    public UstawianieKontroler wezUstawianieKontroler() {
        return ustawianieKontroler;
    }

}
