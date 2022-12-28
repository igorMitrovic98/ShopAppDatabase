package Model.Domen;

import Model.DAO.DAOStavka;

public class Stavka {
    private Integer stavkaID;
    private String cijena;
    private Integer kolicina;
    private String barkod;
    private String nazivArtikla;
    private Integer fakturaID;

    public Stavka(Integer stavkaID,String barkod,Integer fakturaID,Integer kolicina,String cijena){
        this.stavkaID=stavkaID;
        this.cijena=cijena;
        this.kolicina=kolicina;
        this.barkod=barkod;
        this.fakturaID=fakturaID;
    }

    public Integer getStavkaID() {
        return stavkaID;
    }
    public void setStavkaID(Integer stavkaID) {
        this.stavkaID = stavkaID;
    }

    public String getBarkod() {
        return barkod;
    }
    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public Integer getFakturaID() {
        return fakturaID;
    }
    public void setFakturaID(Integer fakturaID) {
        this.fakturaID = fakturaID;
    }

    public Integer getKolicina() {
        return kolicina;
    }
    public void setKolicina(Integer kolicina) { this.kolicina = kolicina; }

    public String getCijena() { return cijena; }
    public void setCijena(String cijena) { this.cijena = cijena; }

    public String getNazivProizvoda(String barkod) { return DAOStavka.getNazivProizvoda(barkod); }

}
