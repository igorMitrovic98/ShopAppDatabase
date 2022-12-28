package Model.Domen;


public class Dobavljac {
    private Integer JIB_D;
    private String naziv;
    private String telefon;

    public Dobavljac(Integer JIB_D, String naziv, String telefon) {
        this.JIB_D=JIB_D;
        this.naziv = naziv;
        this.telefon = telefon;
    }

    public Integer getJIB_D() {
        return JIB_D;
    }
    public void setJIB_D(Integer JIB_D) {
        this.JIB_D=JIB_D;
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
    public void setTelefon(String  telefon) { this.telefon = telefon; }
}
