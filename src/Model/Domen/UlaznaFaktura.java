package Model.Domen;

import java.util.Date;
import java.util.List;


public class UlaznaFaktura {

    private Integer fakturaID;
    private Integer jib_d;
    private Date datumIzdavanja;
    private String mjestoIzdavanja;
    private String ukupnaCijena;
    private String jmbg;
    private String brojRacuna;
    private Integer organizacionaJedinicaID;
    private List<Stavka> stavke;

    public UlaznaFaktura(Integer fakturaID,Integer jib_d,Date datumIzdavanja,String mjestoIzdavanja,String ukupnaCijena,String jmbg,String brojRacuna,Integer organizacionaJedinicaID){

        this.fakturaID=fakturaID;
        this.brojRacuna=brojRacuna;
        this.datumIzdavanja=datumIzdavanja;
        this.jib_d=jib_d;
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

    public Integer getJib_d() {
        return jib_d;
    }
    public void setJib_d(Integer jib_d) {
        this.jib_d=jib_d;
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

    public String getBrojRacuna() {
        return brojRacuna;
    }
    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public List<Stavka> getStavke() {
        return stavke;
    }
    public void setStavke(List<Stavka> stavke) {
        this.stavke = stavke;
    }
}
