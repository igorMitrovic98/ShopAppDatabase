package Model.Domen;

import javafx.scene.Scene;

public class Proizvod {

    private String barkod;
    private String naziv;
    private String cijena;
    private String vrstaMaterijala;
    private Integer jib_p;
    private Integer jib_d;

    public Proizvod(String barkod, String naziv,String cijena, String vrstaMaterijala, Integer jib_p, Integer jib_d){
        this.barkod = barkod;
        this.naziv = naziv;
        this.cijena = cijena;
        this.jib_p = jib_p;
        this.jib_d = jib_d;
        this.vrstaMaterijala = vrstaMaterijala;
    }
    public String getBarkod(){return barkod;}
    public void setBarkod(String barkod){this.barkod = barkod;}

    public String getNaziv(){return naziv;}
    public void setNaziv(String naziv){this.naziv= naziv;}

    public String getCijena(){return cijena;}
    public void setCijena(String cijena){this.cijena = cijena;}

    public int getJib_p(){return jib_p;}
    public void setJib_p(Integer JIB_P){this.jib_p = jib_p;}

    public int getJib_d(){return jib_d;}
    public void setJib_d(Integer jib_d){this.jib_d = jib_d;}

    public String getVrstaMaterijala() {
        return vrstaMaterijala;
    }
    public void setVrstaMaterijala(String vrstaMaterijala) {
        this.vrstaMaterijala = vrstaMaterijala;
    }
}
