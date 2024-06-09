package statki.controller;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import statki.Stale;
import statki.models.Gracz;
import statki.models.IGracz;
import statki.view.Komunikator;
import statki.view.Widok;

import java.util.Random;
import java.util.Scanner;


public class KontrolerGrazPrzyjacielem implements KontrolerGry {
    Komunikator komunikator = new Komunikator();
    private Widok view;
    private IGracz aktualnyGracz;
    private IGracz drugi;
    private boolean isCzyKoniec;
    int x;
    int y;
    Random random = new Random();
    private Button[][] buttonsGracz1;
    private Button[][] buttonsGracz2;
    private Label punktyGracz1;
    private Label punktyGracz2;
    private Label bombyGracz1;
    private Label bombyGracz2;
    private boolean czyStrzelanieBomba = false;
    private IGracz graczA;
    private IGracz graczB;
    private Label samolotyGracz1;
    private Label samolotyGracz2;
    private boolean czyStrzelanieSamolot=false;

    public KontrolerGrazPrzyjacielem(Widok view) {
        this.view = view;
        this.isCzyKoniec = false;
        buttonsGracz1 = new Button[10][10];
        buttonsGracz2 = new Button[10][10];
    }

    public void rozpocznijGre(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem) {
        wyswietlPlanszeDoStrzelania(gracz1, gracz2, czyZKomputerem);
        aktualnyGracz = gracz2;
        drugi = gracz1;
        graczA = gracz1;
        graczB = gracz2;
    }

    private void wyswietlPlanszeDoStrzelania(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem) {
        GridPane gridPane1 = view.tworzPlansze(gracz1, gracz2, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getRowIndex(button), GridPane.getColumnIndex(button), button, gracz1, gracz2, czyZKomputerem);
        });

        for (Node node : gridPane1.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                int x = GridPane.getRowIndex(button);
                int y = GridPane.getColumnIndex(button);
                buttonsGracz1[x][y] = button;
            }
        }
        punktyGracz1 = new Label(Stale.punkty+" " + gracz1.zwrocIlePunktow());
        bombyGracz1 = new Label(Stale.bomba +" "+ gracz1.wezLiczbaBomb());
        Button kupBombeGracz1 = new Button(Stale.kupBombe);
        kupBombeGracz1.setOnAction(event -> kupBombe(gracz1));
        Button strzelBombaGracz1 = new Button(Stale.strzelBombe);
        strzelBombaGracz1.setOnAction(event -> ustawStrzalBomba(gracz1));

        samolotyGracz1 = new Label(Stale.samolot + " " + gracz1.wezLiczbaSamolotow());
        Button kupSamolotGracz1 = new Button(Stale.kupSamolot);
        kupSamolotGracz1.setOnAction(event -> kupSamolot(gracz1));
        Button strzelSamolotGracz1 = new Button(Stale.strzelSamolot);
        strzelSamolotGracz1.setOnAction(event -> ustawStrzalSamolot(gracz1));


        GridPane gridPane2 = view.tworzPlansze(gracz2, gracz1, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getRowIndex(button), GridPane.getColumnIndex(button), button, gracz2, gracz1, czyZKomputerem);
        });


        for (Node node : gridPane2.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                int x = GridPane.getRowIndex(button);
                int y = GridPane.getColumnIndex(button);
                buttonsGracz2[x][y] = button;
            }
        }

        punktyGracz2 = new Label(Stale.punkty+" "+ gracz2.zwrocIlePunktow());
        bombyGracz2 = new Label(Stale.bomba+" "+ gracz2.wezLiczbaBomb());
        Button kupBombeGracz2 = new Button(Stale.kupBombe);
        kupBombeGracz2.setOnAction(event -> kupBombe(gracz2));
        Button strzelBombaGracz2 = new Button(Stale.strzelBombe);
         strzelBombaGracz2.setOnAction(event -> ustawStrzalBomba(gracz2));


        samolotyGracz2 = new Label(Stale.samolot + " " + gracz2.wezLiczbaSamolotow());
        Button kupSamolotGracz2 = new Button(Stale.kupSamolot);
        kupSamolotGracz2.setOnAction(event -> kupSamolot(gracz2));
        Button strzelSamolotGracz2 = new Button(Stale.strzelSamolot);
        strzelSamolotGracz2.setOnAction(event -> ustawStrzalSamolot(gracz2));

        VBox vboxGracz1 = new VBox(10, new Label(gracz1.wezImie()), punktyGracz1, bombyGracz1, samolotyGracz1, kupBombeGracz1, strzelBombaGracz1, kupSamolotGracz1, strzelSamolotGracz1, gridPane1);
        VBox vboxGracz2 = new VBox(10, new Label(gracz2.wezImie()), punktyGracz2, bombyGracz2, samolotyGracz2, kupBombeGracz2, strzelBombaGracz2, kupSamolotGracz2, strzelSamolotGracz2, gridPane2);
        HBox hBox = new HBox(10, vboxGracz1, vboxGracz2);
        Scene scene = new Scene(hBox, 900, 600);
        view.scena.setScene(scene);
        view.scena.setTitle(Stale.tytul);
        view.scena.show();
    }

private void strzelaniePrzycisk(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik, boolean czyZKomputerem) {
    if (!isCzyKoniec) {
        if (aktualnyGracz == przeciwnik) {
            if (czyStrzelanieBomba) {
                strzalBomba(x, y, button, strzelajacy, przeciwnik);
                czyStrzelanieBomba = false;
                zaktualizujPunktyIBomby();
            } else if (czyStrzelanieSamolot) {
                strzalSamolot(x, strzelajacy, przeciwnik);
                czyStrzelanieSamolot = false;
                zaktualizujPunktyIBomby();
            } else {
                if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) >= 0) {
                    jedenStrzal(x, y, button, strzelajacy, przeciwnik);
                } else {
                    nietrafiony(button, strzelajacy);
                }
            }
        } else {
            komunikator.wyswietlCustomAlert(Stale.ruchDrugiego, Stale.sekundy);
        }
    }
}



    private void jedenStrzal(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik) {
        button.setStyle(Stale.kolorZestrzelony);
        strzelajacy.dodajPunkt();
        strzelajacy.czyStreak(true);
        komunikator.wyswietlCustomAlert(strzelajacy.wezImie() +" "+ Stale.wypiszPunkty +" "+ strzelajacy.zwrocIlePunktow(), Stale.sekundy);
        zaktualizujPunktyIBomby(); //???
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
                    komunikator.wyswietlCustomAlert(Stale.trafionyZatopiony, Stale.sekundy);
                    przeciwnik.wezStatki().ilosc_aktywnych--;
                    if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                        isCzyKoniec = true;

                        komunikator.wyswietlCustomAlert(Stale.koniecGry + drugi.wezImie(), Stale.sekundy);
                        view.endGame();
                    }
                }
            }
        }

        button.setDisable(true);
    }

    private void kupBombe(IGracz gracz) {
        try {
            gracz.kupBombe();
            zaktualizujPunktyIBomby();
        } catch (IllegalStateException e) {
//            view.wyswietlKomunikat(e.getMessage());
        }
    }
    private void ustawStrzalBomba(IGracz gracz) {
        if (gracz.wezLiczbaBomb() > 0) {
            gracz.zuzyjBombe();
            wlaczStrzelanieBomba(gracz);
            zaktualizujPunktyIBomby();
        } else {
            zaktualizujPunktyIBomby();
            //view.wyswietlCustomAlert("Za mało bomb!",2);
        }
    }
    private void wlaczStrzelanieBomba(IGracz gracz) {
        czyStrzelanieBomba = true;
    }

    private void wylaczStrzelanieBomba(IGracz gracz) {
        czyStrzelanieBomba = false;
    }

    private void nietrafiony(Button button, IGracz strzelajacy) {
        strzelajacy.czyStreak(false);
        aktualnyGracz = strzelajacy;
        button.setStyle(Stale.kolorNiezestrzelony);
        button.setDisable(true);
    }

public void zaktualizujPunktyIBomby() {
    punktyGracz1.setText(Stale.punkty+" " + graczA.zwrocIlePunktow());
    bombyGracz1.setText(Stale.bomba+" "+ graczA.wezLiczbaBomb());
    samolotyGracz1.setText(Stale.samolot + " " + graczA.wezLiczbaSamolotow());
    punktyGracz2.setText(Stale.punkty+" " + graczB.zwrocIlePunktow());
    bombyGracz2.setText(Stale.bomba+" " + graczB.wezLiczbaBomb());
    samolotyGracz2.setText(Stale.samolot + " " + graczB.wezLiczbaSamolotow());

}

    public void strzalBomba(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
                        Button targetButton = (strzelajacy == graczA) ? buttonsGracz1[newX][newY] : buttonsGracz2[newX][newY];
                        if(przeciwnik.wezPlanszeWypisywana().wezPunkt(newX,newY) != 1) {
                            strzelaniePrzycisk2(newX, newY, targetButton, strzelajacy, przeciwnik);}

                    }
                }
            }
            wylaczStrzelanieBomba(strzelajacy);
        }
    }


    private void strzelaniePrzycisk2(int x, int y, Button button, IGracz strzelajacy, IGracz przeciwnik) {
        if(!isCzyKoniec) {
            if(aktualnyGracz == przeciwnik) {
                if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) >= 0) {
                    button.setStyle(Stale.kolorZestrzelony);
                    boolean pom = false;
                    boolean p=false;
                    int ktory = przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) % 10;
                    int rodzaj = (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) - ktory) / 10;
                    rodzaj--;
                    for (int i = 0; i < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); i++) {
                        if(!przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                        if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {

                            if(przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i)==Stale.puste2){
                                p = true;
                            }
                            przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i, Stale.puste2);//?????

                            przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);
                            for (int j = 0; j < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); j++) {

                                if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != Stale.puste2) {

                                    przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
                                    break;
                                }
                            }
                        }
                            } else {
                                pom = true;
                            }

                    }
//                    if(p)
//                    {
                        strzelajacy.dodajPunkt();
                        zaktualizujPunktyIBomby();
                        strzelajacy.czyStreak(true);
//                    }
                    if(!pom) {
                        if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {

                            przeciwnik.wezStatki().ilosc_aktywnych--;
                            if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                                isCzyKoniec = true;
                                view.endGame();

                            }
                        }
                    }
                } else{
                    button.setStyle(Stale.kolor6);
                }
                button.setDisable(true);
                przeciwnik.wezPlanszeWypisywana().ustaw(x,y,Stale.obok); //????

            } else {
            }
        }
    }

    private void kupSamolot(IGracz gracz) {
        try {
            gracz.kupSamolot();
            zaktualizujPunktyIBomby();
        } catch (IllegalStateException e) {
            komunikator.wyswietlCustomAlert(e.getMessage(), 2);
        }
    }
    private void ustawStrzalSamolot(IGracz gracz) {
        if (gracz.wezLiczbaSamolotow() > 0) {
            gracz.zuzyjSamolot();
            wlaczStrzelanieSamolot(gracz);
            zaktualizujPunktyIBomby();
        } else {
            komunikator.wyswietlCustomAlert("Za mało samolotów!", 2);
        }
    }

    private void wlaczStrzelanieSamolot(IGracz gracz) {
        czyStrzelanieSamolot = true;
    }

private void strzalSamolot(int x, IGracz strzelajacy, IGracz przeciwnik) {
    for (int y = 0; y < 10; y++) {
        Button targetButton = (strzelajacy == graczA) ? buttonsGracz1[x][y] : buttonsGracz2[x][y];
        strzelaniePrzycisk2(x, y, targetButton, strzelajacy, przeciwnik);
    }
    wylaczStrzelanieSamolot(strzelajacy);
}



    private void wylaczStrzelanieSamolot(IGracz gracz) {
        czyStrzelanieSamolot = false;
    }

}