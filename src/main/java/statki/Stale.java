package statki;

import statki.models.Gracz;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Stale {
    public static int ilosc_rodzajow;
    public static int ilosc_statkow;
    public static int puste;
    public static int obok;
    public static int puste2;
    public static int mnoznik;
    public static Map<String, Gracz> gracze = new HashMap<>();
    public static int[] dlugosciStatkow;
    public static int[][] dane;
    public static String[] nazwy;

    static {
        Properties properties = new Properties();
        try (InputStream input = Stale.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Plik config.properties nie został znaleziony");
            }
            properties.load(input);
            ilosc_rodzajow = Integer.parseInt(properties.getProperty("ilosc_rodzajow"));
            ilosc_statkow = Integer.parseInt(properties.getProperty("ilosc_statkow"));
            puste = Integer.parseInt(properties.getProperty("puste"));
            obok = Integer.parseInt(properties.getProperty("obok"));
            puste2 = Integer.parseInt(properties.getProperty("puste2"));
            mnoznik = Integer.parseInt(properties.getProperty("mnoznik"));

            String[] dlugosciStatkowStr = properties.getProperty("dlugosci_statkow").split(",");
            dlugosciStatkow = new int[dlugosciStatkowStr.length];
            for (int i = 0; i < dlugosciStatkowStr.length; i++) {
                dlugosciStatkow[i] = Integer.parseInt(dlugosciStatkowStr[i]);
            }

            String[] daneStr = properties.getProperty("dane").split(";");
            dane = new int[daneStr.length][3];
            for (int i = 0; i < daneStr.length; i++) {
                String[] values = daneStr[i].split(",");
                for (int j = 0; j < values.length; j++) {
                    dane[i][j] = Integer.parseInt(values[j]);
                }
            }

            nazwy = properties.getProperty("nazwy").split(",");

        } catch (IOException ex) {
            ex.printStackTrace();
            // Możesz dodać bardziej wyrafinowane logowanie lub obsługę błędów tutaj
        }
    }
}
