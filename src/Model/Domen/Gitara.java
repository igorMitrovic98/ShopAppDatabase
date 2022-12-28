package Model.Domen;

import java.util.Date;

public class Gitara extends Proizvod {

    private Integer brojZica;
    private String vrsta;

    public Gitara(String naziv,String barkod,String cijena,String vrstaMaterijala,Integer JIB_P, Integer JIB_D, Integer brojZica,String vrsta){
        super(naziv,barkod,cijena,vrstaMaterijala,JIB_P,JIB_D);
        this.brojZica=brojZica;
        this.vrsta=vrsta;
    }
    public Integer getBrojZica(){return brojZica;}
    public void setBrojZica(Integer brojZica){this.brojZica=brojZica;}

    public String getVrsta(){return vrsta;}
    public void setVrsta(String vrsta){this.vrsta=vrsta;}
}
