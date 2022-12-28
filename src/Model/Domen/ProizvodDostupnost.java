package Model.Domen;

public class ProizvodDostupnost {

    private String barkod;
    private Integer organizacionaJedinicaID;
    private Integer kolicina;
    private String nazivArtikla;
    private String cijena;
    private String vrstaMaterijala;

    public ProizvodDostupnost(String barkod,Integer organizacionaJedinicaID,Integer kolicina, String nazivArtikla,String cijena,String vrstaMaterijala) {

        this.barkod = barkod;
        this.organizacionaJedinicaID = organizacionaJedinicaID;
        this.kolicina = kolicina;
        this.nazivArtikla = nazivArtikla;
        this.cijena = cijena;
        this.vrstaMaterijala=vrstaMaterijala;
    }
    public String getBarkod() {
        return barkod;
    }
    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    public Integer getOrganizacionaJedinicaID() {
        return organizacionaJedinicaID;
    }
    public void setOrganizacionaJedinicaID(Integer organizacionaJedinicaID) { this.organizacionaJedinicaID = organizacionaJedinicaID; }

    public Integer getKolicina() {
        return kolicina;
    }
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public String getVrstaMaterijala(){return vrstaMaterijala;}
    public void setVrstaMaterijala(String vrstaMaterijala){this.vrstaMaterijala=vrstaMaterijala;}

    public String getNaziv() {
        return nazivArtikla;
    }
    public void setNaziv(String naziv) {
        this.nazivArtikla = naziv;
    }

    public String getCijena() {
        return cijena;
    }
    public void setCijena(String cijena) {
        this.cijena = cijena;
    }
}
