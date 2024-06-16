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

public class KontrolerGrazPrzyjacielem implements KontrolerGry {
    Komunikator komunikator = new Komunikator();
    private Widok widok;
    private IGracz aktualnyGracz;
    private IGracz drugi;
    private boolean isCzyKoniec;

    private Gracz graczA;
    private Gracz graczB;

    public KontrolerGrazPrzyjacielem(Widok view, Gracz gracz1, Gracz gracz2) {
        this.widok = view;
        this.isCzyKoniec = false;
        gracz1.dopiszButtons();
        gracz2.dopiszButtons();
    }

    public void rozpocznijGre(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem) {
        wyswietlPlanszeDoStrzelania((Gracz)gracz1, (Gracz)gracz2, czyZKomputerem);
        aktualnyGracz = gracz2;
        drugi = gracz1;
        graczA = (Gracz)gracz1;
        graczB = (Gracz)gracz2;
    }


    private GridPane utworzGridPane(Gracz gracz1, Gracz gracz2, Button[][] buttonsGracz) {
        GridPane gridPane = widok.tworzPlansze(gracz1, gracz2, event -> {
            Button button = (Button) event.getSource();
            strzelaniePrzycisk(GridPane.getColumnIndex(button), GridPane.getRowIndex(button), button, gracz1, gracz2);

        });

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                int x = GridPane.getRowIndex(button);
                int y = GridPane.getColumnIndex(button);
                buttonsGracz[x][y] = button;
            }
        }
        return gridPane;
    }
    private VBox utworzVBoxGracza(Gracz gracz,Gracz gracz2 ,boolean czyZKomputerem, Button[][] buttonsGracz, GridPane gridPane) {
        Label punkty = new Label(Stale.punkty + " " + gracz.zwrocIlePunktow());
        Label bomby = new Label(Stale.bomba + " " + gracz.wezLiczbaBomb());
        Button kupBombe = new Button(Stale.kupBombe);
        kupBombe.setOnAction(event -> kupBombe(gracz));
        Button strzelBomba = new Button(Stale.strzelBombe + " \uD83D\uDCA3");
        strzelBomba.setOnAction(event -> ustawStrzalBomba(gracz));

        Label samoloty = new Label(Stale.samolot + " " + gracz.wezLiczbaSamolotow());
        Button kupSamolot = new Button(Stale.kupSamolot);
        kupSamolot.setOnAction(event -> kupSamolot(gracz));
        Button strzelSamolot = new Button(Stale.strzelSamolot);
        strzelSamolot.setOnAction(event -> ustawStrzalSamolot(gracz));

        return new VBox(10, new Label(gracz.wezImie()), punkty, bomby, samoloty, kupBombe, strzelBomba, kupSamolot, strzelSamolot, gridPane);
    }
    private void wyswietlPlanszeDoStrzelania(Gracz gracz1, Gracz gracz2, boolean czyZKomputerem) {
        GridPane gridPane1 = utworzGridPane(gracz1, gracz2,gracz1.getButtonsGracz());
        GridPane gridPane2 = utworzGridPane(gracz2, gracz1, gracz2.getButtonsGracz());

        VBox vboxGracz1 = utworzVBoxGracza(gracz1,gracz2, czyZKomputerem,gracz1.getButtonsGracz() , gridPane1);
        VBox vboxGracz2 = utworzVBoxGracza(gracz2,gracz2, czyZKomputerem, gracz2.getButtonsGracz(), gridPane2);

        gracz1.punktyGracz = (Label) vboxGracz1.getChildren().get(1);
        gracz1.bombyGracz = (Label) vboxGracz1.getChildren().get(2);
        gracz1.samolotyGracz = (Label) vboxGracz1.getChildren().get(3);

        gracz2.punktyGracz = (Label) vboxGracz2.getChildren().get(1);
        gracz2.bombyGracz = (Label) vboxGracz2.getChildren().get(2);
        gracz2.samolotyGracz = (Label) vboxGracz2.getChildren().get(3);

        HBox hBox = new HBox(10, vboxGracz1, vboxGracz2);
        Scene scene = new Scene(hBox, 850, 700);
        widok.scena.setScene(scene);
        widok.scena.setTitle(Stale.tytul);
        widok.scena.show();
    }




    private void jedenStrzal(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        button.setStyle(Stale.kolorZestrzelony);
        strzelajacy.dodajPunkt();
        strzelajacy.czyStreak(true);
        komunikator.wyswietlCustomAlert(strzelajacy.wezImie() + " " + Stale.wypiszPunkty + " " + strzelajacy.zwrocIlePunktow(), Stale.sekundy);
        zaktualizujPunktyIBomby(strzelajacy);

        int ktory = przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) % 10;
        int rodzaj = (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x, y) - ktory) / 10 - 1;
        aktualizujStanStatku(przeciwnik, rodzaj, ktory, x, y);

        button.setDisable(true);
    }

    private void aktualizujStanStatku(Gracz przeciwnik, int rodzaj, int ktory, int x, int y) {
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
                    komunikator.wyswietlCustomAlert(Stale.trafionyZatopiony + " \uD83D\uDEA2", Stale.sekundy);
                    przeciwnik.wezStatki().iloscAktywnych--;
                    sprawdzCzyKoniecGry(przeciwnik);
                }
            }
        }
    }

    private void sprawdzCzyKoniecGry(Gracz przeciwnik) {
        if (przeciwnik.wezStatki().iloscAktywnych == 0) {
            isCzyKoniec = true;
            komunikator.wyswietlCustomAlert(Stale.koniecGry + drugi.wezImie(), Stale.sekundy);
            widok.koniecGry();
        }
    }

    private void kupBombe(Gracz gracz) {
        gracz.kupBombe();
        zaktualizujPunktyIBomby(gracz);

    }
    private void ustawStrzalBomba(Gracz gracz) {
        if (gracz.wezLiczbaBomb() > 0) {
            gracz.zuzyjBombe();
            gracz.wlaczStrzelanie();
            zaktualizujPunktyIBomby(gracz);
        } else {
            zaktualizujPunktyIBomby(gracz);
        }
    }


    private void nietrafiony(Button button, Gracz strzelajacy) {
        strzelajacy.czyStreak(false);
        aktualnyGracz = strzelajacy;
        button.setStyle(Stale.kolorNiezestrzelony);
        button.setDisable(true);
    }

    public void zaktualizujPunktyIBomby(Gracz gracz) {
        gracz.punktyGracz.setText(Stale.punkty+" " + gracz.zwrocIlePunktow());
        gracz.bombyGracz.setText(Stale.bomba+" "+ gracz.wezLiczbaBomb());
        gracz.samolotyGracz.setText(Stale.samolot + " " + gracz.wezLiczbaSamolotow());


    }

    private void strzelaniePrzycisk(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        if (!isCzyKoniec) {
            if (aktualnyGracz == przeciwnik) {
                if (strzelajacy.czyStrzelanie) {
                    strzalBomba(x, y, button, strzelajacy, przeciwnik);
                    strzelajacy.wylaczStrzelanie();
                    zaktualizujPunktyIBomby(przeciwnik);
                } else if (strzelajacy.czyStrzelanieSamolot) {
                    strzalSamolot(y, strzelajacy, przeciwnik);
                    strzelajacy.wylaczStrzelanieSamolot();
                    zaktualizujPunktyIBomby(przeciwnik);
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
        } else {
            widok.koniecGry();
        }
    }

private void kupSamolot(Gracz gracz) {
    gracz.kupSamolot();
    zaktualizujPunktyIBomby(gracz);

}
private void ustawStrzalSamolot(Gracz gracz) {
        if (gracz.wezLiczbaSamolotow() > 0) {
            gracz.zuzyjSamolot();
            gracz.wlaczStrzelanieSamolot();
            zaktualizujPunktyIBomby(gracz);
        } else {
            komunikator.wyswietlCustomAlert(Stale.zaMaloGazociagow, Stale.sekundy);
        }
    }


    private void strzalSamolot(int y, Gracz przeciwnik, Gracz strzelajacy) {
        for (int x = 0; x < Stale.rozmiarPlanszy; x++) {
        Button targetButton = przeciwnik.jedenButton(x, y);
            if (przeciwnik.wezPlanszeWypisywana().wezPunkt(x, y) != 1) {
                if (strzelajacy.wezPlanszePrzeciwnika().wezPunkt(x, y) >= 0) {
                    jedenStrzal(x, y, targetButton, przeciwnik, strzelajacy);
                } else {
                    nietrafiony(targetButton, strzelajacy);
                }
            }
        }
        strzelajacy.wylaczStrzelanieSamolot();
    }
    public void strzalBomba(int x, int y, Button button, Gracz przeciwnik, Gracz strzelajacy) {
        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
                        Button targetButton = przeciwnik.jedenButton(newX, newY);
                        if (przeciwnik.wezPlanszeWypisywana().wezPunkt(newX, newY) != 1) {
                            if (strzelajacy.wezPlanszePrzeciwnika().wezPunkt(newX, newY) >= 0) {
                                jedenStrzal(newX, newY, targetButton, przeciwnik, strzelajacy);
                            } else {
                                nietrafiony(targetButton, strzelajacy);
                            }
                        }
                    }
                }
            }
        }
        strzelajacy.wylaczStrzelanie();
    }


}