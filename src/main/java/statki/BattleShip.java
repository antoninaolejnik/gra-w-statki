package statki;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        gracz1 = new Gracz();
        gracz2 = new Gracz();
        this.primaryStage = primaryStage;
        //System.out.println(gracz1.statki.wszystkie[0].rodzaj[0].indeks);
        wyswietlPlansze(gracz1);

//        gracz1.planszaPrzeciwnika.wypiszPlansze();
//        gracz2.planszaPrzeciwnika.wypiszPlansze();

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

//        gridPane.add(orientationButton, 11, 0, 2, 1);
        Button orientationButton = new Button("Zmień orientację");
        orientationButton.setStyle("-fx-background-color: #C0C0C0;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;");

        orientationButton.setOnMouseEntered(e -> orientationButton.setStyle("-fx-background-color: #A9A9A9;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;"));

        orientationButton.setOnMouseExited(e -> orientationButton.setStyle("-fx-background-color: #C0C0C0;"
                + "-fx-background-radius: 15;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-padding: 10 20 10 20;"));

        orientationButton.setOnAction(event -> {
            czyPionowo = !czyPionowo;
            if (czyPionowo) {
                wyswietlKomunikat("Statki będą ustawiane pionowo");
            } else {
                wyswietlKomunikat("Statki będą ustawiane poziomo");
            }
        });
        gridPane.add(orientationButton, 11, 0, 2, 1);

        komunikatText = new Text();
        komunikatText.setStyle("-fx-font-size: 16px;");
        gridPane.add(komunikatText, 0, 11, 10, 1);
        Scene scene = new Scene(gridPane, 550, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gra w Statki");
        primaryStage.show();
    }

    private void handleButtonClick(int x, int y, Button button, Gracz gracz) {

        if (!pierwszyGraczUstawilStatki) {

            if (gracz.planszaPrzeciwnika.plansza[x][y] == -2) {
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
//                Statek aktualnystatek = gracz1.statki.wszystkie[dlugoscStatku-1].rodzaj[t];
                Statek aktualnystatek = gracz.statki.wszystkie[dlugoscStatku-1].rodzaj[t];
                aktualnystatek.x=x;
                aktualnystatek.y=y;
                aktualnystatek.czyPionowo=czyPionowo;

                aktualnystatek.indeks=10*dlugoscStatku+t;


                if (aktualnystatek.sprawdzStatek(gracz1.planszaPrzeciwnika)) {
                    pom++;
                    aktualnystatek.wstawStatek(gracz1.planszaPrzeciwnika);
                    aktualnystatek.wpiszPola(gracz1.planszaPrzeciwnika);
                    for(int i=0;i<dlugoscStatku;i++){
                        System.out.println(aktualnystatek.pola[i]);
                    }
                    aktualizujPlansze(gracz1.planszaPrzeciwnika);
                } else {
                    wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                    wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
                }
            } else {
                wyswietlCustomAlert("To pole nie jest wolne", 5);

                wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
            }
        } else {

            if (gracz2.planszaPrzeciwnika.plansza[x][y] == -2) {
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
                Statek aktualnystatek = gracz2.statki.wszystkie[dlugoscStatku-1].rodzaj[t];
                aktualnystatek.indeks=10*dlugoscStatku+t;


                aktualnystatek.x=x;
                aktualnystatek.y=y;
                aktualnystatek.czyPionowo=czyPionowo;
                if (aktualnystatek.sprawdzStatek(gracz2.planszaPrzeciwnika)) {
                    pom++;
                    aktualnystatek.wstawStatek(gracz2.planszaPrzeciwnika);
                    aktualnystatek.wpiszPola(gracz2.planszaPrzeciwnika);

                    aktualizujPlansze(gracz2.planszaPrzeciwnika);
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
                int wartosc = plansza.plansza[i][j];
                if (wartosc == -2) {
                    button.setStyle("-fx-background-color: #ffffff;");
                } else if (wartosc == -1) {
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

//        new Gra (gracz1,gracz2);



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
                button1.setOnAction(event -> handleShootButtonClick(finalI, finalJ, button1, gracz1, gracz2));
                gridPane1.add(button1, i, j);

                Button button2 = new Button();
                button2.setMinSize(40, 40);
                button2.setOnAction(event -> handleShootButtonClick(finalI, finalJ, button2, gracz2, gracz1));
//                button2.setOnAction(event -> handleShootButtonClick(finalI, finalJ, button2, gracz1, gracz2));
                gridPane2.add(button2, i, j);
            }
        }

        HBox hBox = new HBox(10, gridPane1, gridPane2);
        Scene scene = new Scene(hBox, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gra w Statki");
        primaryStage.show();
    }


    private void wyłączPrzyciski(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(true);
            }
        }
    }

    private void włączPrzyciski(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(false);
            }
        }
    }


    Gracz aktualnyGracz = new Gracz();
    Gracz drugi = new Gracz();


    private void handleShootButtonClick(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        if(!isCzyKoniec) {
            if(aktualnyGracz == przeciwnik) {

                if (przeciwnik.planszaPrzeciwnika.plansza[x][y] >= 0) { ///>>
                    button.setStyle("-fx-background-color: red;");
                    int ktory = przeciwnik.planszaPrzeciwnika.plansza[x][y] % 10;
                    int rodzaj = (przeciwnik.planszaPrzeciwnika.plansza[x][y] - ktory) / 10;
                    System.out.println("ktory" + ktory + "rodzaj" + rodzaj);


                    rodzaj--;

                    System.out.println("C " + x + " " + y);

                    for (int i = 0; i < przeciwnik.statki.wszystkie[rodzaj].dlugosc; i++) {

                        System.out.println("D " + przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);

                        System.out.println("H " + strzelajacy.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i]);
                        if (przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] == 10 * y + x) {

                            System.out.println("W");
                            przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].pola[i] = -5;//?????
                            przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = true;
                            System.out.println("A");
                            for (int j = 0; j < przeciwnik.statki.wszystkie[rodzaj].dlugosc; j++) {
                                System.out.println("B");
                                if (przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].pola[j] != -5) {
                                    System.out.println("U");
                                    przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (przeciwnik.statki.wszystkie[rodzaj].rodzaj[ktory].czyZbity) {
                        System.out.println("Statek zatopiony!");
                        wyswietlCustomAlert("Trafiony zatopiony", 3);

                        przeciwnik.statki.ilosc_aktywnych--;
                        System.out.println("-----------------");
                        if (przeciwnik.statki.ilosc_aktywnych == 0) {
                            isCzyKoniec = true;
                            System.out.println("kkkkkkkkkk");//???

                        }
                    } else {
                        wyswietlCustomAlert("Trafiony niezatopiony", 3);

                        System.out.println("Statek niezatopiony");
                    }



                } else{
                   // wyswietlCustomAlert("pudlo", 5);

                    System.out.println("pudło");
                    aktualnyGracz = strzelajacy;
                    //drugi = przeciwnik;
                    button.setStyle("-fx-background-color: darkblue;");
                }
                button.setDisable(true);

            } else {
                System.out.println("ciekawe co sie stanie");
            }
        } else {
            System.out.println("KONIECCCC");

        }
    }


    private void zamrozPlansze(GridPane gridPane, boolean zamroz) {
        int size = 10; // Rozmiar planszy 10x10
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null) {
                int x = columnIndex;
                int y = rowIndex;
                node.setDisable(zamroz);
            }
        }
    }
    public void traf(Gracz gracz, int x, int y, Button button) {
        if (gracz.czyTrafnyStrzal(x, y)) {
            button.setStyle("-fx-background-color: red;");
        } else {
            button.setStyle("-fx-background-color: white;");
        }
    }
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



