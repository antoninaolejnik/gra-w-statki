package statki.view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import statki.controller.Kontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import statki.Stale;
import statki.models.Gracz;
import statki.models.Plansza;

public class Widok extends Application {

    private Kontroler kontroler;
    public Stage scena;
    private Text komunikatTekst;
    private int indeks=0;

    @Override
    public void start(Stage primaryStage) {
        this.scena = primaryStage;
        this.kontroler = new Kontroler(this);
        kontroler.zacznijGre();
    }
    public void wyswietlPlanszeDoStrzelania(GridPane gridPane1, GridPane gridPane2) {
        HBox hBox = new HBox(10, gridPane1, gridPane2);
        Scene scene = new Scene(hBox, 800, 400);
        scena.setScene(scene);
        scena.setTitle(Stale.tytul);
        scena.show();
    }

    public GridPane tworzPlansze(Gracz strzelajacy, Gracz przeciwnik, EventHandler<ActionEvent> eventHandler) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> eventHandler.handle(new ActionEvent(button, null)));
                gridPane.add(button, i, j);
            }
        }
        return gridPane;
    }

    public void aktualizujPlansze(Plansza plansza) {
        GridPane gridPane = (GridPane) scena.getScene().getRoot();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button przycisk = (Button) gridPane.getChildren().get(i * 10 + j);
                int wartosc = plansza.wezPunkt(i, j);

                if (wartosc == Stale.puste) {
                    przycisk.setStyle(Stale.kolor2);
                } else if (wartosc == Stale.obok) {
                    przycisk.setStyle(Stale.kolor3);
                } else {
                    przycisk.setStyle(Stale.kolor4);
                }
            }
        }

        if (Stale.dlugosciStatkow[indeks] > 4) {
            wyswietlKomunikat(Stale.wszytskieUstawione);
        } else {
            wyswietlKomunikat(Stale.ustawianieDlugosc + Stale.dlugosciStatkow[indeks]);
        }
        indeks++;
    }
    public void wyswietlPlansze(Gracz gracz) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button przycisk = new Button();
                przycisk.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;
                przycisk.setOnAction(event -> kontroler.kliknieciePrzycisku(finalI, finalJ, przycisk));
                gridPane.add(przycisk, i, j);
            }
        }

        Button przyciskOrientacja = new Button(Stale.orientMozliwosc);
        przyciskOrientacja.setStyle(Stale.przyciskOrient);

        przyciskOrientacja.setOnMouseEntered(e -> przyciskOrientacja.setStyle(Stale.przyciskTlo1));
        przyciskOrientacja.setOnMouseExited(e -> przyciskOrientacja.setStyle(Stale.przyciskTlo2));
        przyciskOrientacja.setOnAction(event -> kontroler.getSetupController().zmienOrientacje());

        gridPane.add(przyciskOrientacja, 11, 0, 2, 1);

        komunikatTekst = new Text();
        komunikatTekst.setStyle(Stale.fontSize);
        gridPane.add(komunikatTekst, 0, 11, 10, 1);

        Scene scene = new Scene(gridPane, 550, 450);
        scena.setScene(scene);
        scena.setTitle(Stale.tytul);
        scena.show();
    }

    public void wyswietlKomunikat(String komunikat) {
        komunikatTekst.setText(komunikat);
    }


}
