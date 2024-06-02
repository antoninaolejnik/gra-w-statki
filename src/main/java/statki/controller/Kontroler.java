package statki.controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import statki.Stale;
import statki.models.Gracz;
import statki.models.Plansza;
import statki.models.Statek;
import statki.view.Widok;
public class Kontroler {

    private Widok view;
    private Gracz gracz1;
    private Gracz gracz2;
    private int dlugoscStatku;
    private int[] dlugosciStatkow = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private int pom = 0;
    private boolean pierwszyGraczUstawilStatki = false;
    private boolean czyPionowo = false;
    private boolean isCzyKoniec = false;
    private Gracz aktualnyGracz;
    private Gracz drugi;
    private Plansza plansza;

    public Kontroler(Widok widok) {
        this.view = widok;
        plansza = new Plansza();
    }

    public void zacznijGre() {
        String imie1 = view.zapytajImie("Podaj imię gracz1");
        gracz1 = wezLubUtworz(imie1);

        String imie2 = view.zapytajImie("Podaj imię gracz2");
        gracz2 = wezLubUtworz(imie2);

        view.wyswietlPlansze(gracz1);
    }

    private Gracz wezLubUtworz(String imie) {
        return Stale.gracze.computeIfAbsent(imie, Gracz::new);
    }

    public void zmienOrientacje() {
        czyPionowo = !czyPionowo;
        if (czyPionowo) {
            view.wyswietlKomunikat("Statki będą ustawiane pionowo");
        } else {
            view.wyswietlKomunikat("Statki będą ustawiane poziomo");
        }
    }

    public void kliknieciePrzycisku(int x, int y, Button button, Gracz gracz) {

        if (!pierwszyGraczUstawilStatki) {
            //ustawianieNaPlanszy(gracz,gracz1,x,y);
            /*if(!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()){
                wyswietlCustomAlert("Nie ma miejsca na statek, resetuje...",5);
                gracz.wezPlanszeWypisywana().reset();
                pom=0;
            }*/
            if(!gracz.wezPlanszeWypisywana().sprawdzCzyWolne()){
                view.wyswietlCustomAlert("Nie ma miejsca na statek, resetuje...",5);
                gracz.wezPlanszeWypisywana().reset();
                pom=0;
            }
            if (gracz.wezPlanszeWypisywana().wezPunkt(x,y) == Stale.puste) {
                if (pom == 10) {

                    pierwszyGraczUstawilStatki = true;
                    pom=0;
                    view.wyswietlPlansze(gracz);
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
                       // System.out.println(aktualnystatek.wezPole(i));
                    }
                    view.aktualizujPlansze(gracz1.wezPlanszePrzeciwnika());
                } else {
                    view.wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                    //view.wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
                }
            } else {
                view.wyswietlCustomAlert("To pole nie jest wolne", 5);

               // view.wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
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

                    view.aktualizujPlansze(gracz2.wezPlanszePrzeciwnika());
                } else {
                    view.wyswietlCustomAlert("Nie można umieścić statku w tym miejscu", 5);

                    //view.wyswietlAlert("Nie można umieścić statku w tym miejscu", komunikatText.getText());
                }
            } else {
                view.wyswietlCustomAlert("To pole nie jest wolne", 5);

                //view.wyswietlAlert("To pole nie jest wolne", komunikatText.getText());
            }
        }
    }
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
        view.scena.setScene(scene);
        view.scena.setTitle("Gra w Statki");
        view.scena.show();
    }




    private void strzelaniePrzycisk(int x, int y, Button button, Gracz strzelajacy, Gracz przeciwnik) {
        if(!isCzyKoniec) {
            if(aktualnyGracz == przeciwnik) {

                if (przeciwnik.wezPlanszePrzeciwnika().wezPunkt(x,y) >= 0) { ///>>
                    button.setStyle("-fx-background-color: red;");
                    strzelajacy.dodajPunkt();
                    strzelajacy.czyStreak(true);
                    view.wyswietlCustomAlert(strzelajacy.wezImie()+" ma :"+strzelajacy.zwrocIlePunktow(),5);
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
                        view.wyswietlCustomAlert("trafiony zatopiony", 3);

                        przeciwnik.wezStatki().ilosc_aktywnych--;
                        if (przeciwnik.wezStatki().ilosc_aktywnych == 0) {
                            isCzyKoniec = true;
                            view.wyswietlCustomAlert("koniec gry, wygral : "+drugi.wezImie(),5);
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
                    }
//                    else {
//                        System.out.println("statek niezatopiony");
//                    }



                } else{
                    strzelajacy.czyStreak(false);
                   // System.out.println("pudło");
                    aktualnyGracz = strzelajacy;
                    button.setStyle("-fx-background-color: darkblue;");
                }
                button.setDisable(true);

            } else {
                view.wyswietlCustomAlert("Jest ruch innego gracza...",5);
            }
        } else {
            System.out.println("KONIECCCC");

        }
    }

    private int znajdzTypStatku(int index) {
        if (index < 1) return index;
        else if (index < 3) return index - 1;
        else if (index < 6) return index - 3;
        else return index - 6;
    }
}
