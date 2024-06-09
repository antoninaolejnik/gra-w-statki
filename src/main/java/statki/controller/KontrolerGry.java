package statki.controller;
import statki.models.IGracz;

public interface KontrolerGry {
    void rozpocznijGre(IGracz gracz1, IGracz gracz2, boolean czyZKomputerem);
}