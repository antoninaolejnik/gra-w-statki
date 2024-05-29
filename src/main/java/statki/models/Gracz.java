package statki.models;


public class Gracz {
    private Plansza planszaPrzeciwnika;
    private Plansza planszaWypisywana;
    private StatkiWszytskie statki;
    private boolean czyKoniec;
    private String imie;
    private int punkty;
    private boolean czyZKolei=false;

    //    public Gracz(Plansza planszaPrzeciwnika) {
//        this.planszaPrzeciwnika = planszaPrzeciwnika;
//        this.planszaWypisywana = new Plansza();
//        this.statki = new Statki_wszytskie();
//        //ustawStatki(); // Tutaj wywołujemy metodę ustawiającą statki
//    }
    public Gracz() {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;
       // System.out.println("konstrukcja");
        //ustawStatki(); // Tutaj wywołujemy metodę ustawiającą statki
    }

    public Gracz(String s) {
        this.planszaPrzeciwnika = new Plansza();
        this.planszaWypisywana = new Plansza();
        this.statki = new StatkiWszytskie();
        punkty=0;
        this.imie=s;
    }

    public boolean czyTrafnyStrzal( int x, int y) {
        if ((x >= 0 && x < 10 && y >= 0 && y < 10) && this.planszaWypisywana.wezPunkt(x,y) != 1 &&this.planszaWypisywana.wezPunkt(x,y) != 2 ) {
            this.planszaWypisywana.ustawNaPlanszy(x,y,1);
            if (this.planszaPrzeciwnika.wezPunkt(x,y) >= 0) {
                this.planszaWypisywana.ustawNaPlanszy(x,y,2);
                int ktory = this.planszaPrzeciwnika.wezPunkt(x,y) % 10;
                //System.out.println("ktory"+ktory);
                int rodzaj = (this.planszaPrzeciwnika.wezPunkt(x,y) - ktory) / 10;
                //rodzaj--;
                System.out.println("ktory: "+ktory+" i rodzaj "+rodzaj);
                for (int i = 0; i < this.statki.wszystkie[rodzaj].wezDlugosc(); i++) {
                    if (this.statki.wszystkie[rodzaj].wezRodzaj(ktory).wezPole(i) == 10 * y + x) {
                        this.statki.wszystkie[rodzaj].wezRodzaj(ktory).ustawPole(i,-3);
                        this.statki.wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(true);
                        for (int j = 0; j < this.statki.wszystkie[rodzaj].wezDlugosc(); j++) {
                            if (this.statki.wszystkie[rodzaj].wezRodzaj(ktory).wezPole(j) != -3) {
                                this.statki.wszystkie[rodzaj].wezRodzaj(ktory).ustawZbity(false);
                                break;
                            }
                        }
                    }
                }
                if (this.statki.wszystkie[rodzaj].wezRodzaj(ktory).czyJestZbity()) {
                    System.out.println("TRAFIONY ZATOPIONY");
                    this.statki.ilosc_aktywnych--;
                    if (this.statki.ilosc_aktywnych == 0) {
                        czyKoniec = true;
                        return false;
                    }
                } else {
                    System.out.println("TRAFIONY NIEZATOPIONY");

                }
                return true;
            } else {
                System.out.println("NIETRAFIONY");
                return false; //nie trafilysmy w statek
            }
        } else {
            System.out.println("nieprawidlowe pole, sprobuj jeszcze raz");
            return true;
        }
    }
    public Plansza wezPlanszePrzeciwnika(){
        return planszaPrzeciwnika;
    }
    public Plansza wezPlanszeWypisywana(){
        return planszaWypisywana;
    }
    public StatkiWszytskie wezStatki(){
        return statki;
    }
    public void ustawImie(String imi){
        imie=imi;
    }
    public String wezImie(){
        return imie;
    }
    public void dodajPunkt(){
        if(czyZKolei)
            punkty+=10;
        else
            punkty++;
    }
    public int zwrocIlePunktow(){
        return punkty;
    }
    public void czyStreak(boolean wart){
        czyZKolei=wart;
    }

}
