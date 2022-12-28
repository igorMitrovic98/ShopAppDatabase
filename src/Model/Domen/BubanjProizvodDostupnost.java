package Model.Domen;

public class BubanjProizvodDostupnost {


        private String barkod;
        private String nazivArtikla;
        private String cijena;
        private Integer organizacionaJedinicaID;
        private String vrsta;
        private Integer kolicina;

        public BubanjProizvodDostupnost(String barkod,String nazivArtikla, Integer kolicina,String cijena,String vrsta,Integer organizacionaJedinicaID){

            this.barkod=barkod;
            this.nazivArtikla=nazivArtikla;
            this.kolicina=kolicina;
            this.cijena=cijena;
            this.vrsta=vrsta;
            this.organizacionaJedinicaID=organizacionaJedinicaID;


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

        public String getNazivArtikla() {
            return nazivArtikla;
        }
        public void setNazivArtikla(String naziv) {
            this.nazivArtikla = naziv;
        }

        public String getCijena() {
            return cijena;
        }
        public void setCijena(String cijena) {
            this.cijena = cijena;
        }

        public String getVrsta() {
            return vrsta;
        }
        public void setVrsta(String vrsta) {
            this.vrsta=vrsta;
        }



    }


