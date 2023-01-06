-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ShopMuzikeOpreme
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ShopMuzikeOpreme` ;

-- -----------------------------------------------------
-- Schema ShopMuzikeOpreme
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ShopMuzikeOpreme` DEFAULT CHARACTER SET utf8 ;
USE `ShopMuzikeOpreme` ;

-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`MJESTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`MJESTO` (
  `MjestoID` INT NOT NULL,
  `Naziv` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`MjestoID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`PROIZVODJAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`PROIZVODJAC` (
  `JIB_P` INT NOT NULL,
  `Naziv` VARCHAR(20) NOT NULL,
  `Telefon` VARCHAR(20) NOT NULL,
  `MjestoID` INT NOT NULL,
  PRIMARY KEY (`JIB_P`),
  INDEX `fk_PROIZVODJAC_MJESTO1_idx` (`MjestoID` ASC),
  CONSTRAINT `fk_PROIZVODJAC_MJESTO1`
    FOREIGN KEY (`MjestoID`)
    REFERENCES `ShopMuzikeOpreme`.`MJESTO` (`MjestoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`DOBAVLJAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`DOBAVLJAC` (
  `JIB_D` INT NOT NULL,
  `Naziv` VARCHAR(20) NOT NULL,
  `Telefon` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`JIB_D`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`PROIZVOD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`PROIZVOD` (
  `Barkod` VARCHAR(20) NOT NULL,
  `Naziv` VARCHAR(30) NOT NULL,
  `Cijena` VARCHAR(10) NOT NULL,
  `VrstaMaterijala` VARCHAR(30) NOT NULL,
  `JIB_P` INT NOT NULL,
  `JIB_D` INT NOT NULL,
  PRIMARY KEY (`Barkod`),
  INDEX `fk_PROIZVOD_PROIZVODJAC1_idx` (`JIB_P` ASC),
  INDEX `fk_PROIZVOD_DOBAVLJAC1_idx` (`JIB_D` ASC),
  CONSTRAINT `fk_PROIZVOD_PROIZVODJAC1`
    FOREIGN KEY (`JIB_P`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVODJAC` (`JIB_P`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROIZVOD_DOBAVLJAC1`
    FOREIGN KEY (`JIB_D`)
    REFERENCES `ShopMuzikeOpreme`.`DOBAVLJAC` (`JIB_D`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`GITARA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`GITARA` (
  `BrojZica` INT NOT NULL,
  `Barkod` VARCHAR(20) NOT NULL,
  `Vrsta` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Barkod`),
  CONSTRAINT `fk_GITARA_PROIZVOD`
    FOREIGN KEY (`Barkod`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVOD` (`Barkod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`BUBANJ`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`BUBANJ` (
  `Barkod` VARCHAR(20) NOT NULL,
  `Vrsta` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Barkod`),
  CONSTRAINT `fk_BUBANJ_PROIZVOD1`
    FOREIGN KEY (`Barkod`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVOD` (`Barkod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`KLAVIJATURA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`KLAVIJATURA` (
  `TipKonfiguracije` VARCHAR(10) NOT NULL,
  `Barkod` VARCHAR(20) NOT NULL,
  `Vrsta` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Barkod`),
  CONSTRAINT `fk_KLAVIJATURA_PROIZVOD1`
    FOREIGN KEY (`Barkod`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVOD` (`Barkod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ZAPOSLENI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ZAPOSLENI` (
  `JMBG` VARCHAR(13) NOT NULL,
  `Ime` VARCHAR(15) NOT NULL,
  `Prezime` VARCHAR(20) NOT NULL,
  `DatumRodjenja` DATE NOT NULL,
  `IznosPlate` VARCHAR(10) NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `KorisnickoIme` VARCHAR(15) NOT NULL,
  `Lozinka` VARCHAR(15) NOT NULL,
  `MjestoID` INT NOT NULL,
  PRIMARY KEY (`JMBG`),
  INDEX `fk_ZAPOSLENI_MJESTO1_idx` (`MjestoID` ASC),
  CONSTRAINT `fk_ZAPOSLENI_MJESTO1`
    FOREIGN KEY (`MjestoID`)
    REFERENCES `ShopMuzikeOpreme`.`MJESTO` (`MjestoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`FAKTURA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`FAKTURA` (
  `FakturaID` INT NOT NULL AUTO_INCREMENT,
  `MjestoIzdavanja` VARCHAR(30) NOT NULL,
  `DatumIzdavanja` DATE NOT NULL,
  `UkupnaCijena` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`FakturaID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`STAVKA_FAKTURE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`STAVKA_FAKTURE` (
  `StavkaID` INT NOT NULL AUTO_INCREMENT,
  `Cijena` VARCHAR(10) NOT NULL,
  `Kolicina` INT NOT NULL,
  `PROIZVOD_Barkod` VARCHAR(20) NOT NULL,
  `FAKTURA_FakturaID` INT NOT NULL,
  PRIMARY KEY (`StavkaID`),
  INDEX `fk_STAVKA_FAKTURE_PROIZVOD1_idx` (`PROIZVOD_Barkod` ASC),
  INDEX `fk_STAVKA_FAKTURE_FAKTURA1_idx` (`FAKTURA_FakturaID` ASC),
  CONSTRAINT `fk_STAVKA_FAKTURE_PROIZVOD1`
    FOREIGN KEY (`PROIZVOD_Barkod`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVOD` (`Barkod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STAVKA_FAKTURE_FAKTURA1`
    FOREIGN KEY (`FAKTURA_FakturaID`)
    REFERENCES `ShopMuzikeOpreme`.`FAKTURA` (`FakturaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK` (
  `JMBG` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`JMBG`),
  CONSTRAINT `fk_ADMINISTRATIVNI_RADNIK_ZAPOSLENI1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`ZAPOSLENI` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ORGANIZACIONA_JEDINICA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ORGANIZACIONA_JEDINICA` (
  `OrganizacionaJedinicaID` INT NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `MjestoID` INT NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`),
  INDEX `fk_ORGANIZACIONA_JEDINICA_MJESTO1_idx` (`MjestoID` ASC),
  CONSTRAINT `fk_ORGANIZACIONA_JEDINICA_MJESTO1`
    FOREIGN KEY (`MjestoID`)
    REFERENCES `ShopMuzikeOpreme`.`MJESTO` (`MjestoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA` (
  `OrganizacionaJedinicaID` INT NOT NULL,
  `Telefon` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`),
  CONSTRAINT `fk_RAD_SA_PROIZVODIMA_ORGANIZACIONA_JEDINICA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ORGANIZACIONA_JEDINICA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`BANKA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`BANKA` (
  `BankaID` INT NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `Naziv` VARCHAR(30) NOT NULL,
  `MjestoID` INT NOT NULL,
  PRIMARY KEY (`BankaID`),
  INDEX `fk_BANKA_MJESTO1_idx` (`MjestoID` ASC),
  CONSTRAINT `fk_BANKA_MJESTO1`
    FOREIGN KEY (`MjestoID`)
    REFERENCES `ShopMuzikeOpreme`.`MJESTO` (`MjestoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`RACUN_DOBAVLJACA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`RACUN_DOBAVLJACA` (
  `BrojRacuna` VARCHAR(20) NOT NULL,
  `Iznos` VARCHAR(10) NOT NULL,
  `DOBAVLJAC_JIB_D` INT NOT NULL,
  `BANKA_BankaID` INT NOT NULL,
  PRIMARY KEY (`BrojRacuna`),
  INDEX `fk_RACUN_DOBAVLJACA_DOBAVLJAC1_idx` (`DOBAVLJAC_JIB_D` ASC),
  INDEX `fk_RACUN_DOBAVLJACA_BANKA1_idx` (`BANKA_BankaID` ASC),
  CONSTRAINT `fk_RACUN_DOBAVLJACA_DOBAVLJAC1`
    FOREIGN KEY (`DOBAVLJAC_JIB_D`)
    REFERENCES `ShopMuzikeOpreme`.`DOBAVLJAC` (`JIB_D`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RACUN_DOBAVLJACA_BANKA1`
    FOREIGN KEY (`BANKA_BankaID`)
    REFERENCES `ShopMuzikeOpreme`.`BANKA` (`BankaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ULAZNA_FAKTURA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ULAZNA_FAKTURA` (
  `FakturaID` INT NOT NULL,
  `JIB_D` INT NOT NULL,
  `JMBG` VARCHAR(13) NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  `BrojRacuna` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`FakturaID`),
  INDEX `fk_ULAZNA_FAKTURA_DOBAVLJAC1_idx` (`JIB_D` ASC),
  INDEX `fk_ULAZNA_FAKTURA_ADMINISTRATIVNI_RADNIK1_idx` (`JMBG` ASC),
  INDEX `fk_ULAZNA_FAKTURA_ZA_RAD_SA_PROIZVODIMA1_idx` (`OrganizacionaJedinicaID` ASC),
  INDEX `fk_ULAZNA_FAKTURA_RACUN_DOBAVLJACA1_idx` (`BrojRacuna` ASC),
  CONSTRAINT `fk_ULAZNA_FAKTURA_FAKTURA1`
    FOREIGN KEY (`FakturaID`)
    REFERENCES `ShopMuzikeOpreme`.`FAKTURA` (`FakturaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ULAZNA_FAKTURA_DOBAVLJAC1`
    FOREIGN KEY (`JIB_D`)
    REFERENCES `ShopMuzikeOpreme`.`DOBAVLJAC` (`JIB_D`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ULAZNA_FAKTURA_ADMINISTRATIVNI_RADNIK1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ULAZNA_FAKTURA_ZA_RAD_SA_PROIZVODIMA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ULAZNA_FAKTURA_RACUN_DOBAVLJACA1`
    FOREIGN KEY (`BrojRacuna`)
    REFERENCES `ShopMuzikeOpreme`.`RACUN_DOBAVLJACA` (`BrojRacuna`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`PRODAVNICA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`PRODAVNICA` (
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`),
  CONSTRAINT `fk_PRODAVNICA_ZA_RAD_SA_PROIZVODIMA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`TRGOVAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`TRGOVAC` (
  `JMBG` VARCHAR(13) NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`JMBG`),
  INDEX `fk_TRGOVAC_PRODAVNICA1_idx` (`OrganizacionaJedinicaID` ASC),
  CONSTRAINT `fk_TRGOVAC_ZAPOSLENI1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`ZAPOSLENI` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRGOVAC_PRODAVNICA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`PRODAVNICA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`KUPAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`KUPAC` (
  `KupacID` INT NOT NULL,
  `Email` VARCHAR(30) NULL,
  `MjestoID` INT NULL,
  PRIMARY KEY (`KupacID`),
  INDEX `fk_KUPAC_MJESTO1_idx` (`MjestoID` ASC),
  CONSTRAINT `fk_KUPAC_MJESTO1`
    FOREIGN KEY (`MjestoID`)
    REFERENCES `ShopMuzikeOpreme`.`MJESTO` (`MjestoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`IZLAZNA_FAKTURA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`IZLAZNA_FAKTURA` (
  `FAKTURA_FakturaID` INT NOT NULL,
  `TRGOVAC_JMBG` VARCHAR(13) NOT NULL,
  `PRODAVNICA_OrganizacionaJedinicaID` INT NOT NULL,
  `KUPAC_KupacID` INT NULL,
  PRIMARY KEY (`FAKTURA_FakturaID`),
  INDEX `fk_IZLAZNA_FAKTURA_TRGOVAC1_idx` (`TRGOVAC_JMBG` ASC),
  INDEX `fk_IZLAZNA_FAKTURA_PRODAVNICA1_idx` (`PRODAVNICA_OrganizacionaJedinicaID` ASC),
  INDEX `fk_IZLAZNA_FAKTURA_KUPAC1_idx` (`KUPAC_KupacID` ASC),
  CONSTRAINT `fk_IZLAZNA_FAKTURA_FAKTURA1`
    FOREIGN KEY (`FAKTURA_FakturaID`)
    REFERENCES `ShopMuzikeOpreme`.`FAKTURA` (`FakturaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IZLAZNA_FAKTURA_TRGOVAC1`
    FOREIGN KEY (`TRGOVAC_JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`TRGOVAC` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IZLAZNA_FAKTURA_PRODAVNICA1`
    FOREIGN KEY (`PRODAVNICA_OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`PRODAVNICA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IZLAZNA_FAKTURA_KUPAC1`
    FOREIGN KEY (`KUPAC_KupacID`)
    REFERENCES `ShopMuzikeOpreme`.`KUPAC` (`KupacID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`MAGACIONER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`MAGACIONER` (
  `JMBG` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`JMBG`),
  CONSTRAINT `fk_MAGACIONER_ZAPOSLENI1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`ZAPOSLENI` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ADMINISTRATIVNA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ADMINISTRATIVNA` (
  `OrganizacionaJedinicaID` INT NOT NULL,
  `Email` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`),
  CONSTRAINT `fk_ADMINISTRATIVNA_ORGANIZACIONA_JEDINICA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ORGANIZACIONA_JEDINICA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`PROIZVOD_DOSTUPNOST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`PROIZVOD_DOSTUPNOST` (
  `Kolicina` INT NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  `Barkod` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`, `Barkod`),
  INDEX `fk_PROIZVOD_DOSTUPNOST_PROIZVOD1_idx` (`Barkod` ASC),
  CONSTRAINT `fk_PROIZVOD_DOSTUPNOST_ZA_RAD_SA_PROIZVODIMA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROIZVOD_DOSTUPNOST_PROIZVOD1`
    FOREIGN KEY (`Barkod`)
    REFERENCES `ShopMuzikeOpreme`.`PROIZVOD` (`Barkod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`MAGACIN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`MAGACIN` (
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`OrganizacionaJedinicaID`),
  CONSTRAINT `fk_MAGACIN_ZA_RAD_SA_PROIZVODIMA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ZA_RAD_SA_PROIZVODIMA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK_A`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK_A` (
  `DatumOd` DATE NOT NULL,
  `Datumdo` DATE NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  `JMBG` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`DatumOd`, `OrganizacionaJedinicaID`, `JMBG`),
  INDEX `fk_ADMINISTRATIVNI_RADNIK_A_ADMINISTRATIVNA1_idx` (`OrganizacionaJedinicaID` ASC),
  INDEX `fk_ADMINISTRATIVNI_RADNIK_A_ADMINISTRATIVNI_RADNIK1_idx` (`JMBG` ASC),
  CONSTRAINT `fk_ADMINISTRATIVNI_RADNIK_A_ADMINISTRATIVNA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ADMINISTRATIVNA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADMINISTRATIVNI_RADNIK_A_ADMINISTRATIVNI_RADNIK1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`ADMINISTRATIVNI_RADNIK` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`MAGACIONER_M`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`MAGACIONER_M` (
  `DatumOd` DATE NOT NULL,
  `DatumDo` DATE NOT NULL,
  `JMBG` VARCHAR(13) NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`DatumOd`, `JMBG`, `OrganizacionaJedinicaID`),
  INDEX `fk_MAGACIONER_M_MAGACIONER1_idx` (`JMBG` ASC),
  INDEX `fk_MAGACIONER_M_MAGACIN1_idx` (`OrganizacionaJedinicaID` ASC),
  CONSTRAINT `fk_MAGACIONER_M_MAGACIONER1`
    FOREIGN KEY (`JMBG`)
    REFERENCES `ShopMuzikeOpreme`.`MAGACIONER` (`JMBG`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MAGACIONER_M_MAGACIN1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`MAGACIN` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`TelefonA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`TelefonA` (
  `BrojTelefona` VARCHAR(20) NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`BrojTelefona`, `OrganizacionaJedinicaID`),
  INDEX `fk_TelefonA_ADMINISTRATIVNA1_idx` (`OrganizacionaJedinicaID` ASC),
  CONSTRAINT `fk_TelefonA_ADMINISTRATIVNA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ADMINISTRATIVNA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShopMuzikeOpreme`.`RACUN_OJ`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShopMuzikeOpreme`.`RACUN_OJ` (
  `BrojRacuna` VARCHAR(20) NOT NULL,
  `Iznos` VARCHAR(10) NOT NULL,
  `BankaID` INT NOT NULL,
  `OrganizacionaJedinicaID` INT NOT NULL,
  PRIMARY KEY (`BrojRacuna`),
  INDEX `fk_RACUN_OJ_BANKA1_idx` (`BankaID` ASC),
  INDEX `fk_RACUN_OJ_ORGANIZACIONA_JEDINICA1_idx` (`OrganizacionaJedinicaID` ASC),
  CONSTRAINT `fk_RACUN_OJ_BANKA1`
    FOREIGN KEY (`BankaID`)
    REFERENCES `ShopMuzikeOpreme`.`BANKA` (`BankaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RACUN_OJ_ORGANIZACIONA_JEDINICA1`
    FOREIGN KEY (`OrganizacionaJedinicaID`)
    REFERENCES `ShopMuzikeOpreme`.`ORGANIZACIONA_JEDINICA` (`OrganizacionaJedinicaID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
