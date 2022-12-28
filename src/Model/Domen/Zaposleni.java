package Model.Domen;

import java.util.Date;

public class Zaposleni {

    private String JMBG;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String iznosPlate;
    private String adresa;
    private String korisnickoIme;
    private String lozinka;
    private Integer mjestoID;

    public Zaposleni(String JMBG,String ime,String prezime,Date datumRodjenja,String iznosPlate, String adresa,String korisnickoIme,String lozinka,Integer mjestoID){
        this.JMBG=JMBG;
        this.ime=ime;
        this.prezime=prezime;
        this.datumRodjenja=datumRodjenja;
        this.iznosPlate=iznosPlate;
        this.adresa=adresa;
        this.korisnickoIme=korisnickoIme;
        this.lozinka=lozinka;
        this.mjestoID=mjestoID;
    }
    public String getJMBG() {
        return JMBG;
    }
    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }
    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getAdresa() {
        return adresa;
    }
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getMjestoID() {
        return mjestoID;
    }
    public void setMjestoID(Integer mjestoID) {
        this.mjestoID = mjestoID;
    }

    public String getIznosPlate() {
        return iznosPlate;
    }
    public void setIznosPlate(String iznosPlate) {
        this.iznosPlate = iznosPlate;
    }


    public String getKorisnickoIme() {
        return korisnickoIme;
    }
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}

