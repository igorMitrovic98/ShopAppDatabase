package Model.Domen;

import java.util.Date;

public class Trgovac extends Zaposleni {

    public Trgovac(String JMBG,String ime, String prezime,String iznosPlate,Date datumRodjenja,String adresa,String korisnickoIme,String lozinka, Integer mjestoID){
        super(JMBG,ime,prezime,datumRodjenja,iznosPlate,adresa,korisnickoIme,lozinka,mjestoID);
    }

}
