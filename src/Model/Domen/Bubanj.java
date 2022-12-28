package Model.Domen;

import java.util.Date;

public class Bubanj extends Proizvod {


    private String vrsta;

    public Bubanj(String naziv,String barkod,String cijena,String vrstaMaterijala,Integer JIB_P, Integer JIB_D,String vrsta){
        super(naziv,barkod,cijena,vrstaMaterijala,JIB_P,JIB_D);
        this.vrsta=vrsta;
    }

    public String getVrsta(){return vrsta;}
    public void setVrsta(String vrsta){this.vrsta=vrsta;}
}
