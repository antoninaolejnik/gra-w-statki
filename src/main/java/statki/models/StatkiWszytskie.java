package statki.models;

import statki.Stale;

public class StatkiWszytskie {
    public final int iloscRodzajow = Stale.iloscRodzajow;
    public int iloscAktywnych = Stale.iloscStatkow;
    public StatkiRodzaj[] wszystkie ;

    public StatkiWszytskie()
    {
        wszystkie = new StatkiRodzaj[iloscRodzajow];
        for(int i = 0; i< iloscRodzajow; i++)
        {
            wszystkie[i]= new StatkiRodzaj(Stale.dane[i][1],Stale.dane[i][2]);
        }
    }

}
//kazdy gracz ma StatkiWszystkie, tam statki są podzielone na rodzaje, w StatkachRodzaj każdy statek jest rozważany indywidulanie
//przy strzelaniu na planszy przeciwnika w starezlonym miejscu pojaiwa się id statku
//ktore jest numerem rodzaju i numerem statku tego rodzaju np drugi statek dlugosci 3 ma id 32
//a zarazem kazdy statek ma swoja tablice w ktorej ma zapisane jakie numery w tablicy 10x10 zajmuje np 90,91,92
//gdy jest strzelony po lewo na dole i ma dlugosc 3
//dzieki temu latwo znajdowac statek gdy strzelimy w dane miejsce a potem odznaczać prawidłowa kolejnosc odstzreliwania
//indywidulanego statku