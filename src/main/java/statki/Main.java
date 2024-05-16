package statki;

import java.util.Scanner;

/*public class Main {
    public static void main(String[] args) {
        System.out.println("Witaj w grze w statki");
        System.out.println("Gracz 1 wpisuje swoje statki:");
        Gracz graczB = new Gracz();
        statki.WyczyscTerminal.clearScreen();
        System.out.println("Gracz 2 wpisuje swoje statki:");
        Gracz graczA = new Gracz();
        statki.WyczyscTerminal.clearScreen();

         Gra gra1 = new Gra(graczA,graczB);

    }
}*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

