package Model.Domen;

import java.util.Date;

public class AdministrativniR extends Zaposleni{

    public AdministrativniR(Integer mjestoID, String JMBG, String ime, String prezime, Date datumRodjenja, String adresa, String iznosPlate, String korisnickoIme, String lozinka) {
        super(JMBG, ime, prezime, datumRodjenja, adresa, iznosPlate, korisnickoIme, lozinka, mjestoID);
    }

}
