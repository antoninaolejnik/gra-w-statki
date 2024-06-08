package statki.models;

import statki.Stale;

public class StatkiWszytskie {
    public final int ilosc_rodzajow= Stale.iloscRodzajow;
    public int ilosc_aktywnych = Stale.iloscStatkow;
    public StatkiRodzaj[] wszystkie ;

    public StatkiWszytskie()
    {
        wszystkie = new StatkiRodzaj[ilosc_rodzajow];
        for(int i=0; i<ilosc_rodzajow; i++)
        {
            wszystkie[i]= new StatkiRodzaj(Stale.dane[i][1],Stale.dane[i][2]);
        }
    }

}
