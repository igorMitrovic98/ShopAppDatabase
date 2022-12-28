package Model.Domen;

import java.util.Date;

public class Magacioner extends Zaposleni{
    public Magacioner(Integer mjestoID, String JMBG, String ime, String prezime, Date datumRodjenja, String adresa, String iznosPlate, String korisnickoIme, String lozinka) {
        super(JMBG,ime, prezime, datumRodjenja, adresa, iznosPlate, korisnickoIme, lozinka,mjestoID);
    }
}
