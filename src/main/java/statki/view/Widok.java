package statki.view;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statki.controller.Kontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import statki.Stale;

import statki.models.Gracz;
import statki.models.IGracz;
import statki.models.Plansza;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Widok extends Application {

    private Kontroler kontroler;
    public Stage scena;
    private Text komunikatTekst;
    private int indeks = 0;
    private boolean czyZKomputerem = false;

    @Override
    public void start(Stage primaryStage) {
        this.scena = primaryStage;
        wyswietlMenuStartowe();
    }

    public void wyswietlMenuStartowe() {
        VBox vbox = new VBox(10);
        vbox.setStyle(Stale.kolor5);

        Button graZPrzyjacielem = new Button(Stale.grajZPrzyjacielem);
        graZPrzyjacielem.setMinSize(200, 50);
        graZPrzyjacielem.setOnAction(event -> rozpocznijGre(false));

        Button graZKomputerem = new Button(Stale.grajZKomputerem);
        graZKomputerem.setMinSize(200, 50);
        graZKomputerem.setOnAction(event -> rozpocznijGre(true));

        Button informacja = new Button(Stale.jakGracHeader);
        informacja.setMinSize(200, 50);
        informacja.setOnAction(event -> wyswietlInformacjeOkno());

        vbox.getChildren().addAll(graZPrzyjacielem, graZKomputerem, informacja);

        Scene scene = new Scene(vbox, 400, 250);
        scena.setScene(scene);
        scena.setTitle(Stale.tytul);
        scena.show();
    }

    private void wyswietlInformacjeOkno() {
        Stage infoStage = new Stage();
        infoStage.setTitle(Stale.infoGra);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle(Stale.kolor5);

        Label header = new Label(Stale.jakGracHeader);
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea infoText = new TextArea();
        infoText.setText("Statki to gra wojenna, w której dwóch graczy próbuje zatopić statki przeciwnika.\n" +
                "W naszym module gry można grać z drugą osobą lub z komputerem\n"+
                "Każdy gracz ustawia swoje statki na planszy o rozmiarze 10x10.\n" +
                "Statki mogą być ustawione poziomo lub pionowo i nie mogą się dotykać.\n" +
                "Gracz ustawia statki wybierając miejsce początku statku.\n"+
                "Po ustawieniu statków, gracze na przemian oddają strzały, próbując trafić statki przeciwnika.\n" +
                "Gra kończy się, gdy wszystkie statki jednego z graczy zostaną zatopione.");
        infoText.setWrapText(true);
        infoText.setEditable(false);

        Button zamknijButton = new Button(Stale.zamknij);
        zamknijButton.setOnAction(event -> infoStage.close());

        vbox.getChildren().addAll(header, infoText, zamknijButton);

        Scene scene = new Scene(vbox, 400, 300);
        infoStage.setScene(scene);
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.showAndWait();
    }


    public void rozpocznijGre(boolean czyZKomputerem) {
        this.czyZKomputerem = czyZKomputerem;
        this.kontroler = new Kontroler(this, czyZKomputerem);
        kontroler.zacznijGre();
    }

    public void wyswietlPlanszeDoStrzelania(GridPane gridPane1, GridPane gridPane2) {
        HBox hBox = new HBox(10, gridPane1, gridPane2);
        Scene scene = new Scene(hBox, 800, 400);
        scena.setScene(scene);
        scena.setTitle(Stale.tytul);
        scena.show();
    }

    public GridPane tworzPlansze(IGracz strzelajacy, IGracz przeciwnik, EventHandler<ActionEvent> eventHandler) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> eventHandler.handle(new ActionEvent(button, null)));
                strzelajacy.buttonsgracz[i][j]= button;
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
        if(indeks==10)
            indeks=0;
    }

    public void wyswietlPlansze(IGracz gracz) {
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

//        Button przyciskOrientacja = new Button(Stale.orientMozliwosc);
        Button przyciskOrientacja = new Button("poziomo/pionowo");
        przyciskOrientacja.setStyle(Stale.przyciskOrient);

        przyciskOrientacja.setOnMouseEntered(e -> przyciskOrientacja.setStyle(Stale.przyciskTlo1));
        przyciskOrientacja.setOnMouseExited(e -> przyciskOrientacja.setStyle(Stale.przyciskTlo2));
        przyciskOrientacja.setOnAction(event -> kontroler.getSetupController().zmienOrientacje());

        gridPane.add(przyciskOrientacja, 11, 0, 2, 1);

        komunikatTekst = new Text();
        komunikatTekst.setStyle(Stale.fontSize);
        gridPane.add(komunikatTekst, 0, 11, 10, 1);

        Scene scene = new Scene(gridPane, 600, 450);
        scena.setScene(scene);
        scena.setTitle(Stale.tytul);
        scena.show();
    }

    public void wyswietlKomunikat(String komunikat) {
        komunikatTekst.setText(komunikat);
    }

public Button wezButton(int x, int y, IGracz przeciwnik) {
    Parent root = scena.getScene().getRoot();
    if (root instanceof HBox) {
        HBox hBox = (HBox) root;
        GridPane opponentGrid = (GridPane) hBox.getChildren().get(1);
        int index = y * 10 + x;
        Node node = opponentGrid.getChildren().get(index);
        if (node instanceof Button) {
            return (Button) node;
        }
    }
    return null;
}

    public void endGame() {
        StackPane endPane = new StackPane();
        endPane.setStyle("-fx-background-color: black;");

        Label endLabel = new Label(Stale.koniecGry1);
        endLabel.setFont(Font.font("Serif", FontWeight.BOLD, 50));
        endLabel.setTextFill(Color.WHITE);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(endLabel);
        vbox.setAlignment(Pos.CENTER);
        endPane.getChildren().add(vbox);

        Scene endScene = new Scene(endPane, 800, 600);
        scena.setScene(endScene);
    }


}
