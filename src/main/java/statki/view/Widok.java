package statki.view;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import statki.Stale;
import statki.controller.Kontroler;
import statki.models.Gracz;
import statki.models.Plansza;

import java.util.Optional;

public class Widok extends Application {

    private Kontroler kontroler;
    public Stage scena;
    private Text komunikatTekst;
    private int dlugoscStatku = 1;

    @Override
    public void start(Stage primaryStage) {
        this.scena = primaryStage;
        this.kontroler = new Kontroler(this);
        kontroler.zacznijGre();
    }

    public String zapytajImie(String title) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(title);

        ButtonType zatwierdzPrzycisk = new ButtonType("Zatwierdź", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(zatwierdzPrzycisk);
        TextField poleImie = new TextField();
        poleImie.setPromptText("Imię");

        dialog.getDialogPane().setStyle("-fx-background-color: #E0E0E0;");
        dialog.initStyle(StageStyle.UNDECORATED);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label label = new Label("Imię:");
        grid.add(label, 0, 0);
        grid.add(poleImie, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(poleImie::requestFocus);

        dialog.setResultConverter(dialogButton -> dialogButton == zatwierdzPrzycisk ? poleImie.getText() : null);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
    public void aktualizujPlansze(Plansza plansza) {
        GridPane gridPane = (GridPane) scena.getScene().getRoot();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button przycisk = (Button) gridPane.getChildren().get(i * 10 + j);
                int wartosc = plansza.wezPunkt(i, j);

                if (wartosc == Stale.puste) {
                    przycisk.setStyle("-fx-background-color: #ffffff;");
                } else if (wartosc == Stale.obok) {
                    przycisk.setStyle("-fx-background-color: rgba(5,26,112,0.37);");
                } else {
                    przycisk.setStyle("-fx-background-color: #0415ea;");
                }
            }
        }
        if (dlugoscStatku > 4) {
            wyswietlKomunikat("Wszystkie statki zostały ustawione");
        } else {
            wyswietlKomunikat("Ustawianie statku o długości " + dlugoscStatku);
        }
    }
    public void wyswietlPlansze(Gracz gracz) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button przycisk = new Button();
                przycisk.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;
                przycisk.setOnAction(event -> kontroler.kliknieciePrzycisku(finalI, finalJ, przycisk,gracz));
                gridPane.add(przycisk, i, j);
            }
        }

        Button przyciskOrientacja = new Button("Zmień orientację");
        przyciskOrientacja.setStyle("-fx-background-color: #C0C0C0; -fx-background-radius: 15; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10 20 10 20;");

        przyciskOrientacja.setOnMouseEntered(e -> przyciskOrientacja.setStyle("-fx-background-color: #A9A9A9; -fx-background-radius: 15; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));
        przyciskOrientacja.setOnMouseExited(e -> przyciskOrientacja.setStyle("-fx-background-color: #C0C0C0; -fx-background-radius: 15; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));
        przyciskOrientacja.setOnAction(event -> kontroler.zmienOrientacje());

        gridPane.add(przyciskOrientacja, 11, 0, 2, 1);

        komunikatTekst = new Text();
        komunikatTekst.setStyle("-fx-font-size: 16px;");
        gridPane.add(komunikatTekst, 0, 11, 10, 1);

        Scene scene = new Scene(gridPane, 550, 450);
        scena.setScene(scene);
        scena.setTitle("Gra w Statki");
        scena.show();
    }

    public void wyswietlKomunikat(String komunikat) {
        komunikatTekst.setText(komunikat);
    }



    public void wyswietlCustomAlert(String komunikat, int sekundy) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10; -fx-padding: 20px;");

        Label label = new Label(komunikat);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Arial", 20));
        label.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(label);

        StackPane root = new StackPane(vbox);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        Scene scene = new Scene(root, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        alertStage.setScene(scene);
        alertStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(sekundy), event -> alertStage.close()));
        timeline.setCycleCount(1);
        timeline.playFromStart();
    }

}
