public class Statki_rodzaj {
    int dlugosc;
//    String nazwa_rodzaju;
    int ilosc;
    Statek[] rodzaj;

    public Statki_rodzaj(int dlugosc, int ilosc)
    {
        this.dlugosc = dlugosc;
        this.ilosc= ilosc;
        this.rodzaj = new Statek[ilosc];
    }

}
