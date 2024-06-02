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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import statki.Stale;
import statki.models.Gracz;
import statki.models.Plansza;
import statki.models.Statek;

import java.util.HashMap;
import java.util.Optional;


public class BattleShip extends Application {


    Gracz gracz1 ;
    Gracz gracz2 ;
    private Stage primaryStage;
    private int dlugoscStatku;
    private int[] dlugosciStatkow = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private int pom = 0;
    private boolean pierwszyGraczUstawilStatki = false;
    private Text komunikatText;
    private boolean czyPionowo = false;


    public boolean czyKoniec = false;
    @Override
    public void start(Stage primaryStage) {
        //gracz1 = new Gracz();
        //GraczeSingleton graczeSingleton = GraczeSingleton.getInstance();
        String imie1=zapytajImie();
        gracz1=wezLubUtworz(imie1);
        //System.out.println(gracz1.wezImie());

        String imie2=zapytajImie();
        gracz2=wezLubUtworz(imie2);
        this.primaryStage = primaryStage;
        wyswietlPlansze(gracz1);



    }
    private Gracz wezLubUtworz(String imie){
        return Stale.gracze.computeIfAbsent(imie,Gracz::new);
    }

    private String zapytajImie() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Podaj imię gracz1");
        dialog.setHeaderText("Wprowadź swoje imię:");

        ButtonType zatwierdzButtonType = new ButtonType("Zatwierdź", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().add(zatwierdzButtonType);

        TextField imieTextField = new TextField();
        imieTextField.setPromptText("Imię");

        dialog.getDialogPane().setStyle("-fx-background-color: #E0E0E0;"); // Set background color to light gray
        dialog.initStyle(StageStyle.UNDECORATED); // Remove window decoration (title bar, etc.)

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label label = new Label("Imię:");
        grid.add(label, 0, 0);
        grid.add(imieTextField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> imieTextField.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == zatwierdzButtonType) {
                return imieTextField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();


        return result.orElse(null);
    }
    private void wyswietlPlansze(Gracz gracz) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;

                button.setOnAction(event -> handleButtonClick(finalI, finalJ, button, gracz));
                gridPane.add(button, i, j);
            }
        }

//        gridPane.add(przyciskOrientacja, 11, 0, 2, 1);
        Button przyciskOrientacja = new Button("Zmień orientację");
        przyciskOrientacja.setStyle("-fx-background-color: #C0C0C0;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;");

        przyciskOrientacja.setOnMouseEntered(e -> przyciskOrientacja.setStyle("-fx-background-color: #A9A9A9;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;"));

        przyciskOrientacja.setOnMouseExited(e -> przyciskOrientacja.setStyle("-fx-background-color: #C0C0C0;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;"));

        przyciskOrientacja.setOnAction(event -> {
            czyPionowo = !czyPionowo;
            if (czyPionowo) {
                wyswietlKomunikat("Statki będą ustawiane pionowo");
            } else {
                wyswietlKomunikat("Statki będą ustawiane poziomo");
            }
        });
        gridPane.add(przyciskOrientacja, 11, 0, 2, 1);

        komunikatText = new Text();
        komunikatText.setStyle("-fx-font-size: 16px;");
        gridPane.add(komunikatText, 0, 11, 10, 1);
        Scene scene = new Scene(gridPane, 550, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gra w Statki");
        primaryStage.show();
    }
    private void ustawianieNaPlanszy(Gracz gracz,Gracz gracz1, int x,int y){
        if (gracz.wezPlanszeWypisywana().wezPunkt(x,y) == Stale.puste) {
            if (pom == 10) {

                pierwszyGraczUstawilStatki = true;
                pom=0;
                wyswietlPlansze(gracz2);
                czyPionowo=false;
                return;
            }
            dlugoscStatku = dlugosciStatkow[pom];
//                pom++;

            //Statek aktualnystatek = new Statek(x, y, dlugoscStatku, czyPionowo);
            int t=0;
            if(pom == 0 || pom ==1 || pom == 3 || pom ==6) t=0;
            if(pom == 2 || pom ==4|| pom ==7) t=1;
            if(pom == 5 || pom ==8) t=2;
            if(pom ==9 ) t=3;
            //pom++;
            Statek aktualnystatek = gracz.wezStatki().wszystkie[dlugoscStatku-1].wezRodzaj(t);
            aktualnystatek.ustawX(x);
            aktualnystatek.ustawY(y);
            aktualnystatek.ustawOrient(czyPionowo);

            aktualnystatek.ustawIndeks(10*dlugoscStatku+t);


            if (aktualnystatek.sprawdzStatek(gracz1.wezPlanszePrzeciwnika())) {
                pom++;
                aktualnystatek.wstawStatek(gracz1.wezPlanszePrzeciwnika());
                aktualnystatek.wpiszPola(gracz1.wezPlanszePrzeciwnika());
                for(int i=0;i<dlugoscStatku;i++){
                    System.out.println(aktualnystatek.wezPole(i));
                }
                aktualizujPlansze(gracz1.wezPlanszePrzeciwnika());
            } else {
                wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
            }
        } else {
            wyswietlCustomAlert("To pole nie jest wolne", 5);

            wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
        }
    }
    private void handleButtonClick(int x, int y, Button button, Gracz gracz) {

        if (!pierwszyGraczUstawilStatki) {
            //ustawianieNaPlanszy(gracz,gracz1,x,y);
            /*if(!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()){
                wyswietlCustomAlert("Nie ma miejsca na statek, resetuje...",5);
                gracz.wezPlanszeWypisywana().reset();
                pom=0;
            }*/
            if(!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()){
                System.out.println("aa");
                wyswietlCustomAlert("Nie ma miejsca na statek, resetuje...",5);
                gracz.wezPlanszeWypisywana().reset();
                pom=0;
            }
            if (gracz.wezPlanszeWypisywana().wezPunkt(x,y) == Stale.puste) {
                if (pom == 10) {

                    pierwszyGraczUstawilStatki = true;
                    pom=0;
                    wyswietlPlansze(gracz2);
                    czyPionowo=false;
                    return;
                }
                dlugoscStatku = dlugosciStatkow[pom];
//                pom++;

                //Statek aktualnystatek = new Statek(x, y, dlugoscStatku, czyPionowo);
                int t=0;
                if(pom == 0 || pom ==1 || pom == 3 || pom ==6) t=0;
                if(pom == 2 || pom ==4|| pom ==7) t=1;
                if(pom == 5 || pom ==8) t=2;
                if(pom ==9 ) t=3;
                //pom++;
                Statek aktualnystatek = gracz.wezStatki().wszystkie[dlugoscStatku-1].wezRodzaj(t);
                aktualnystatek.ustawX(x);
                aktualnystatek.ustawY(y);
                aktualnystatek.ustawOrient(czyPionowo);

                aktualnystatek.ustawIndeks(10*dlugoscStatku+t);


                if (aktualnystatek.sprawdzStatek(gracz1.wezPlanszePrzeciwnika())) {
                    pom++;
                    aktualnystatek.wstawStatek(gracz1.wezPlanszePrzeciwnika());
                    aktualnystatek.wpiszPola(gracz1.wezPlanszePrzeciwnika());
                    for(int i=0;i<dlugoscStatku;i++){
                        System.out.println(aktualnystatek.wezPole(i));
                    }
                    aktualizujPlansze(gracz1.wezPlanszePrzeciwnika());
                } else {
                    wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                    wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
                }
            } else {
                wyswietlCustomAlert("To pole nie jest wolne", 5);

                wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
            }
        } else {
                //ustawianieNaPlanszy(gracz2,gracz2,x,y);
           /* if(!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()){
                wyswietlCustomAlert("Nie ma miejsca na statek, resetuje...",5);
                gracz.wezPlanszeWypisywana().reset();
                pom=0;
            }*/
            if (gracz2.wezPlanszePrzeciwnika().wezPunkt(x,y) == Stale.puste) {
                if(pom==10){
                    rozpocznijGre();
                    return;}
                dlugoscStatku = dlugosciStatkow[pom];
                //pom++;
                int t=0;
                if(pom == 0 || pom ==1 || pom == 3 || pom ==6) t=0;
                if(pom == 2 || pom ==4|| pom ==7) t=1;
                if(pom == 5 || pom ==8) t=2;
                if(pom ==9 ) t=3;
                //pom++;
//                Statek statek = gracz1.statki.wszystkie[dlugoscStatku-1].rodzaj[t];
                Statek aktualnystatek = gracz2.wezStatki().wszystkie[dlugoscStatku-1].wezRodzaj(t);
                aktualnystatek.ustawIndeks(10*dlugoscStatku+t);


                aktualnystatek.ustawX(x);
                aktualnystatek.ustawY(y);
                aktualnystatek.ustawOrient(czyPionowo);
                if (aktualnystatek.sprawdzStatek(gracz2.wezPlanszePrzeciwnika())) {
                    pom++;
                    aktualnystatek.wstawStatek(gracz2.wezPlanszePrzeciwnika());
                    aktualnystatek.wpiszPola(gracz2.wezPlanszePrzeciwnika());

                    aktualizujPlansze(gracz2.wezPlanszePrzeciwnika());
                } else {
                    wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                    wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
                }
            } else {
                wyswietlCustomAlert("To pole nie jest wolne", 5);

                wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
            }
        }
    }

    private void aktualizujPlansze(Plansza plansza) {
        GridPane gridPane = (GridPane) primaryStage.getScene().getRoot();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button = (Button) gridPane.getChildren().get(i * 10 + j);
                int wartosc = plansza.wezPunkt(i,j);

                if (wartosc == Stale.puste) {
                    button.setStyle("-fx-background-color: #ffffff;");
                } else if (wartosc == Stale.obok) {
                    button.setStyle("-fx-background-color: rgba(5,26,112,0.37);");
                } else {
                    button.setStyle("-fx-background-color: #0415ea;");
                }
            }
        }
        if (dlugoscStatku > 4) {
            wyswietlKomunikat("Wszystkie statki zostały ustawione");
        } else {
            wyswietlKomunikat("Ustawianie statku o długości " + dlugoscStatku);
        }
    }





    private void wyswietlKomunikat(String komunikat) {
        komunikatText.setText(komunikat);
    }

    private void wyswietlAlert(String komunikat, String komunikatPrev) {
        komunikatText.setText(komunikat);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    komunikatText.setText(komunikatPrev);
                })
        );


        timeline1.setCycleCount(1);
        timeline1.playFromStart();
    }
    boolean isCzyKoniec=false;

    private void rozpocznijGre() {
        wyswietlPlanszeDoStrzelania(gracz1, gracz2);
        aktualnyGracz = gracz2;
        drugi = gracz1;





    }

    private void wyswietlPlanszeDoStrzelania(Gracz gracz1, Gracz gracz2) {
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button1 = new Button();
                button1.setMinSize(40, 40);
                int finalI = i;
                int finalJ = j;
                button1.setOnAction(event -> strzelaniePrzycisk(finalI, finalJ, button1, gracz1, gracz2));
                gridPane1.add(button1, i, j);

                Button button2 = new Button();
                button2.setMinSize(40, 40);
                button2.setOnAction(event -> strzelaniePrzycisk(finalI, finalJ, button2, gracz2, gracz1));
                gridPane2.add(button2, i, j);
            }
        }

        HBox hBox = new HBox(10, gridPane1, gridPane2);
        Scene scene = new Scene(hBox, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gra w Statki");
        primaryStage.show();
    }



    Gracz aktualnyGracz = new Gracz();
    Gracz drugi = new Gracz();


    private void strzelaniePrzycisk(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        if(!isCzyKoniec) {
            if(aktualnyGracz == przeciwnik) {

                if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) >= 0) { ///>>
                    button.setStyle("-fx-background-color: red;");
                    strzelajacy.dodajPunkt();
                    strzelajacy.czyStreak(true);
                    wyswietlCustomAlert(strzelajacy.wezImie()+" ma :"+strzelajacy.zwrocIlePunktow(),5);
                    int ktory = przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) % 10;
                    int rodzaj = (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) - ktory) / 10;
                   // System.out.println("ktory" + ktory + "rodzaj" + rodzaj);


                    rodzaj--;


                    for (int i = 0; i < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); i++) {




                        if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {


                            przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i,Stale.puste2);//?????
                            przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);

                            for (int j = 0; j < przeciwnik.wezStatki().wszystkie[rodzaj].wezDlugosc(); j++) {

                                if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != Stale.puste2) {

                                    przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
                                    break;
                                }
                            }


                        }
                    }

                    if (przeciwnik.wezStatki().wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                        System.out.println("statek zatopiony!");
                        wyswietlCustomAlert("trafiony zatopiony", 3);

                        przeciwnik.wezStatki().ilosc_aktywnych--;
                        System.out.println("-----------------");
                        if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                            isCzyKoniec = true;
                            System.out.println("koniec");//???
                            wyswietlCustomAlert("koniec gry, wygral : "+drugi.wezImie(),5);
                            /*ImageView imageView = new ImageView(new Image("morze.gif"));

                            imageView.setX(0);
                            imageView.setY(0);
                            imageView.setFitWidth(700);
                            imageView.setFitHeight(400);


                            StackPane root = new StackPane();
                            root.getChildren().add(imageView);

                            Scene scene = new Scene(root, 400, 400);
                            primaryStage.setScene(scene);
                            primaryStage.setTitle("Gra zakończona");
                            primaryStage.show();


                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
                                if (isCzyKoniec) {
                                    root.getChildren().clear();
                                    root.getChildren().add(imageView);
                                }
                            }));
                            timeline.setCycleCount(Timeline.INDEFINITE);
                            timeline.play();*/

                        }
                    } else {


                        System.out.println("statek niezatopiony");
                    }



                } else{

                    strzelajacy.czyStreak(false);
                    System.out.println("pudło");
                    aktualnyGracz = strzelajacy;

                    button.setStyle("-fx-background-color: darkblue;");
                }
                button.setDisable(true);

            } else {
                wyswietlCustomAlert("Jest ruch innego gracza...",5);
            }
        } else {
            System.out.println("KONIECCCC");

        }
    }


//    private void zamrozPlansze(GridPane gridPane, boolean zamroz) {
//        int size = 10;
//        for (Node node : gridPane.getChildren()) {
//            Integer columnIndex = GridPane.getColumnIndex(node);
//            Integer rowIndex = GridPane.getRowIndex(node);
//            if (columnIndex != null && rowIndex != null) {
//                int x = columnIndex;
//                int y = rowIndex;
//                node.setDisable(zamroz);
//            }
//        }
//    }
//    public void traf(Gracz gracz, int x, int y, Button button) {
//        if (gracz.czyTrafnyStrzal(x, y)) {
//            button.setStyle("-fx-background-color: red;");
//        } else {
//            button.setStyle("-fx-background-color: white;");
//        }
//    }
    private void wyswietlCustomAlert(String komunikat, int sekundy) {
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

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(sekundy), event -> alertStage.close())
        );

        timeline.setCycleCount(1);
        timeline.playFromStart();
    }




    public static void main(String[] args) {
        launch(args);
    }


}



