package statki.models;

import statki.Stale;
import statki.models.StatkiRodzaj;

public class StatkiWszytskie {
    public final int ilosc_rodzajow= Stale.ilosc_rodzajow;
    public int ilosc_aktywnych = Stale.ilosc_statkow;
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
