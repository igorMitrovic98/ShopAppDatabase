package Model.Domen;

import java.util.Date;

public class Klavijatura extends Proizvod {

    private String tipKonfiguracije;
    private String vrsta;

    public Klavijatura(String naziv,String barkod,String cijena,String vrstaMaterijala,Integer JIB_P, Integer JIB_D, String tipKonfiguracije,String vrsta){
        super(naziv,barkod,cijena,vrstaMaterijala,JIB_P,JIB_D);
        this.tipKonfiguracije=tipKonfiguracije;
        this.vrsta=vrsta;
    }
    public String getTipKonfiguracije(){return tipKonfiguracije;}
    public void setTipKonfiguracije(String tipKonfiguracije){this.tipKonfiguracije=tipKonfiguracije;}

    public String getVrsta(){return vrsta;}
    public void setVrsta(String vrsta){this.vrsta=vrsta;}
}
