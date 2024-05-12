import java.io.IOException;

public class WyczyscTerminal {
    public static void clearScreen() {
        /*try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            //e.printStackTrace();
        }*/
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

    }
}
