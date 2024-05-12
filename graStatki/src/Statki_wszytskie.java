public class Statki_wszytskie {
    final int ilosc_rodzajow= Stale.ilosc_rodzajow;
    int ilosc_aktywnych = Stale.ilosc_statkow;
    Statki_rodzaj[] wszystkie = new Statki_rodzaj[ilosc_rodzajow];

    public Statki_wszytskie()
    {
        for(int i=0; i<ilosc_rodzajow; i++)
        {
            wszystkie[i]= new Statki_rodzaj(Stale.dane[i][1],Stale.dane[i][2]);
        }
    }

}
