package Model.Domen;

import java.util.Date;
import java.util.List;

public class IzlaznaFaktura {

    private Integer fakturaID;
    private Integer kupacID;
    private Date datumIzdavanja;
    private String mjestoIzdavanja;
    private String ukupnaCijena;
    private String jmbg;
    private Integer organizacionaJedinicaID;
    private List<Stavka> stavke;

    public IzlaznaFaktura(Integer fakturaID,Integer kupacID,Date datumIzdavanja,String mjestoIzdavanja,String ukupnaCijena,String jmbg,Integer organizacionaJedinicaID){

        this.fakturaID=fakturaID;
        this.datumIzdavanja=datumIzdavanja;
        this.kupacID=kupacID;
        this.jmbg=jmbg;
        this.mjestoIzdavanja=mjestoIzdavanja;
        this.ukupnaCijena=ukupnaCijena;
        this.organizacionaJedinicaID=organizacionaJedinicaID;
    }
    public Integer getFakturaID() {
    return fakturaID;
}
    public void setFakturaID(Integer fakturaID) {
        this.fakturaID=fakturaID;
    }

    public Date getDatumIzdavanja() { return datumIzdavanja; }
    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public String getUkupnaCijena() {
        return ukupnaCijena;
    }
    public void setUkupnaCijena(String ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    public String getMjestoIzdavanja() {
        return mjestoIzdavanja;
    }
    public void setMjestoIzdavanja(String mjestoIzdavanja) {
        this.mjestoIzdavanja = mjestoIzdavanja;
    }

    public Integer getKupacID() {
        return kupacID;
    }
    public void setKupacID(Integer kupacID) {
        this.kupacID=kupacID;
    }

    public Integer getOrganizacionaJedinicaID() {
        return organizacionaJedinicaID;
    }
    public void setOrganizacionaJedinicaID(Integer organizacionaJedinicaID) { this.organizacionaJedinicaID = organizacionaJedinicaID; }

    public String getJmbg() {
        return jmbg;
    }
    public void setJmbg(String jmbg) {
        this.jmbg=jmbg;
    }

    public List<Stavka> getStavke() {
        return stavke;
    }
    public void setStavke(List<Stavka> stavke) {
        this.stavke = stavke;
    }
}
