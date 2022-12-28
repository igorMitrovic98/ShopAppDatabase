package Model.Domen;

public class Proizvodjac {

    private Integer jib_p;
    private String naziv;
    private String telefon;
    private Integer mjestoID;
    private String nazivMjesta;

    public Proizvodjac(Integer jib_p,String naziv,String telefon,Integer mjestoID,String nazivMjesta){
        this.jib_p=jib_p;
        this.naziv=naziv;
        this.telefon=telefon;
        this.mjestoID=mjestoID;
        this.nazivMjesta=nazivMjesta;
    }
    public Integer getJib_p() {
        return jib_p;
    }
    public void setJib_p(Integer JIB_P) {
        this.jib_p=jib_p;
    }

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Integer getMjestoId() {
        return mjestoID;
    }
    public void setMjestoId(Integer mjestoID) {
        this.mjestoID = mjestoID;
    }

    public String getNazivMjesta(){return nazivMjesta;}
    public void setNazivMjesta(String nazivMjesta){this.nazivMjesta=nazivMjesta;}
}
