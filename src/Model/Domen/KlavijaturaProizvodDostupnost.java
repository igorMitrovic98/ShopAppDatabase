package Model.Domen;

public class KlavijaturaProizvodDostupnost {

        private String barkod;
        private String nazivArtikla;
        private String cijena;
        private Integer organizacionaJedinicaID;
        private String tipKonfiguracije;
        private String vrsta;
        private Integer kolicina;

        public KlavijaturaProizvodDostupnost(String barkod,String nazivArtikla, Integer kolicina,String cijena,String tipKonfiguracije,String vrsta,Integer organizacionaJedinicaID){

            this.barkod=barkod;
            this.tipKonfiguracije=tipKonfiguracije;
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

        public String getTipKonfiguracije() {
            return tipKonfiguracije;
        }
        public void setTipKonfiguracije(String tipKonfiguracije) { this.tipKonfiguracije=tipKonfiguracije; }


    }


