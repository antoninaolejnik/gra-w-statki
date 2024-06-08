package statki.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import statki.Stale;

import java.util.Optional;

public class Komunikator {

    public String zapytajImie(String title) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(title);

        ButtonType zatwierdzPrzycisk = new ButtonType(Stale.zatwierdz, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(zatwierdzPrzycisk);
        TextField poleImie = new TextField();
        poleImie.setPromptText(Stale.imie);

        dialog.getDialogPane().setStyle(Stale.kolorImie);
        dialog.initStyle(StageStyle.UNDECORATED);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label label = new Label(Stale.imie);
        grid.add(label, 0, 0);
        grid.add(poleImie, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(poleImie::requestFocus);

        dialog.setResultConverter(dialogButton -> dialogButton == zatwierdzPrzycisk ? poleImie.getText() : null);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void wyswietlCustomAlert(String komunikat, int sekundy) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle(Stale.customAlert);

        Label label = new Label(komunikat);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(Stale.font, 20));
        label.setTextAlignment(TextAlignment.CENTER);
        vbox.getChildren().add(label);

        StackPane root = new StackPane(vbox);
        root.setStyle(Stale.kolor1);
        Scene scene = new Scene(root, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        alertStage.setScene(scene);
        alertStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(sekundy), event -> alertStage.close()));
        timeline.setCycleCount(1);
        timeline.playFromStart();
    }




}

