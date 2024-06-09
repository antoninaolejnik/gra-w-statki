package statki;

import statki.models.Gracz;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Stale {
    public static int iloscRodzajow;
    public static int rozmiarPlanszy;
    public static int iloscStatkow;
    public static int puste;
    public static int obok;
    public static int puste2;
    public static int mnoznik;
    public static Map<String, Gracz> gracze = new HashMap<>();
    public static int[] dlugosciStatkow;
    public static int[][] dane;
    public static String[] nazwy;
    public static String podajImie;
    public static String pierwszyGracz;
    public static String drugiGracz;
    public static String ruchDrugiego;
    public static String kolorZestrzelony;
    public static String trafionyZatopiony;
    public static String koniecGry;
    public static String kolorNiezestrzelony;
    public static String zleMiejsce;
    public static String reset;
    public static String pion;
    public static String poziom;
    public static String zmianaOrient;

    public static String tytul;
    public static String wypiszPunkty;
    public static String orientMozliwosc;
    public static String przyciskOrient;
    public static String przyciskTlo1;
    public static String przyciskTlo2;
    public static String fontSize;
    public static String imie;
    public static String zatwierdz;
    public static String kolorImie;
    public static String customAlert;
    public static String font;
    public static String kolor1;
    public static String kolor2;
    public static String kolor3;
    public static String kolor4;
    public static String wszytskieUstawione;
    public static String ustawianieDlugosc;
    public static String grajZPrzyjacielem;
    public static String grajZKomputerem;
    public static String komputer;
    public static String kolor5;




    static {
        Properties properties = new Properties();
        try (InputStream input = Stale.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Plik config.properties nie został znaleziony");
            }
            properties.load(input);
            rozmiarPlanszy=Integer.parseInt(properties.getProperty("rozmiar_planszy"));
            iloscRodzajow = Integer.parseInt(properties.getProperty("ilosc_rodzajow"));
            iloscStatkow = Integer.parseInt(properties.getProperty("ilosc_statkow"));
            puste = Integer.parseInt(properties.getProperty("puste"));
            obok = Integer.parseInt(properties.getProperty("obok"));
            puste2 = Integer.parseInt(properties.getProperty("puste2"));
            mnoznik = Integer.parseInt(properties.getProperty("mnoznik"));
            podajImie=properties.getProperty("podaj_imie");
            pierwszyGracz= properties.getProperty("pierwszy_gracz");
            drugiGracz= properties.getProperty("drugi_gracz");
            ruchDrugiego=properties.getProperty("ruch_drugiego");
            kolorZestrzelony= properties.getProperty("kolor_zestrzrelony");
            trafionyZatopiony=properties.getProperty("trafiony_zatopiony");
            koniecGry=properties.getProperty("koniec_gry");
            kolorNiezestrzelony=properties.getProperty("nietrafiony");
            reset=properties.getProperty("nie_ma_miejsca");
            zleMiejsce=properties.getProperty("nie_mozna_ustawic");
            pion=properties.getProperty("pion");
            poziom=properties.getProperty("poziom");
            zmianaOrient=properties.getProperty("zmien_orientacje");
            kolorZestrzelony=properties.getProperty("kolor_zestrzelony");
            String[] dlugosciStatkowStr = properties.getProperty("dlugosci_statkow").split(",");
            dlugosciStatkow = new int[dlugosciStatkowStr.length];
            for (int i = 0; i < dlugosciStatkowStr.length; i++) {
                dlugosciStatkow[i] = Integer.parseInt(dlugosciStatkowStr[i]);
            }
            tytul=properties.getProperty("tytul");
            wypiszPunkty=properties.getProperty("wypis_punkt");
            orientMozliwosc=properties.getProperty("orient_mozliwosc");
            przyciskOrient=properties.getProperty("przycisk_orient");
            przyciskTlo1=properties.getProperty("przycisk_tlo1");
            przyciskTlo2=properties.getProperty("przycisk_tlo2");
            fontSize=properties.getProperty("font_size");
            imie=properties.getProperty("imie");
            zatwierdz=properties.getProperty("zatwierdz");
            kolorImie=properties.getProperty("kolor_imie");
            customAlert=properties.getProperty("custom_alert");
            font=properties.getProperty("font");
            kolor1=properties.getProperty("kolor1");
            kolor2=properties.getProperty("kolor2");
            kolor3=properties.getProperty("kolor3");
            kolor4=properties.getProperty("kolor4");
            wszytskieUstawione=properties.getProperty("wszystkie_ustawione");
            ustawianieDlugosc=properties.getProperty("ustawianie_dlugosc");
            String[] daneStr = properties.getProperty("dane").split(";");
            dane = new int[daneStr.length][3];
            for (int i = 0; i < daneStr.length; i++) {
                String[] values = daneStr[i].split(",");
                for (int j = 0; j < values.length; j++) {
                    dane[i][j] = Integer.parseInt(values[j]);
                }
            }
            grajZKomputerem=properties.getProperty("graj_z_komp");
            grajZPrzyjacielem=properties.getProperty("graj_z_przyj");
            komputer=properties.getProperty("komputer");
            kolor5=properties.getProperty("kolor5");

            nazwy = properties.getProperty("nazwy").split(",");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
