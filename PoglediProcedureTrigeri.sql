delimiter $$
create procedure insert_into_gitara(in naziv varchar(30),
									in cijena varchar(10), in jib_p int, in jib_d int, in vrstaMaterijala varchar(30),
                                   in vrsta varchar(20), in barkod varchar(20), in brojZica int)
						
begin
	insert into proizvod(Barkod, Naziv,Cijena,VrstaMaterijala,JIB_P,JIB_D)
    values (barkod, naziv, cijena, vrstaMaterijala,jib_p, jib_d)
            ON DUPLICATE KEY UPDATE Naziv=naziv, Cijena=cijena, JIB_D=jib_d,
            JIB_P=jib_p, VrstaMaterijala=vrstaMaterijala;
            insert into gitara(Barkod, BrojZica,Vrsta) value (barkod, brojZica,vrsta)
            ON DUPLICATE KEY UPDATE BrojZica=brojZica,Vrsta=vrsta;
 
end$$
delimiter ;

delimiter $$
create procedure insert_into_bubanj(in naziv varchar(30),
									in cijena varchar(10), in jib_p int, in jib_d int, in vrstaMaterijala varchar(30),
                                   in vrsta varchar(20), in barkod varchar(20))
						
begin
	insert into proizvod(Barkod, Naziv,Cijena,VrstaMaterijala,JIB_P,JIB_D)
    values (barkod, naziv, cijena, vrstaMaterijala,jib_p, jib_d)
            ON DUPLICATE KEY UPDATE Naziv=naziv, Cijena=cijena, JIB_D=jib_d,
            JIB_P=jib_p, VrstaMaterijala=vrstaMaterijala;
            insert into gitara(Barkod,Vrsta) value (barkod,vrsta)
            ON DUPLICATE KEY UPDATE Vrsta=vrsta;
 
end$$
delimiter ;

delimiter $$
create procedure insert_into_klavijatura(in naziv varchar(30),
									in cijena varchar(10), in jib_p int, in jib_d int, in vrstaMaterijala varchar(30),
                                   in vrsta varchar(20), in barkod varchar(20), in tipKonfiguracije varchar(10))
						
begin
	insert into proizvod(Barkod, Naziv,Cijena,VrstaMaterijala,JIB_P,JIB_D)
    values (barkod, naziv, cijena, vrstaMaterijala,jib_p, jib_d)
            ON DUPLICATE KEY UPDATE Naziv=naziv, Cijena=cijena, JIB_D=jib_d,
            JIB_P=jib_p, VrstaMaterijala=vrstaMaterijala;
            insert into gitara(Barkod, TipKonfiguracije,Vrsta) value (barkod, tipKonfiguracije,vrsta)
            ON DUPLICATE KEY UPDATE TipKonfiguracije=tipKonfiguracije,Vrsta=vrsta;
 
end$$
delimiter ;

create view prikaz_proizvoda as
select * from proizvod;

create view prikaz_gitara as
select * from proizvod
natural join gitara;

create view prikaz_bubanj as
select * from proizvod
natural join bubanj;

create view prikaz_klavijatura as
select * from proizvod
natural join klavijatura;

delimiter $$
create procedure `azuriraj_gitara`(
                                                       
                                                        in naziv varchar(30),
                                                        in cijena varchar(10),
                                                        in jib_p int,
                                                        in jib_d int,
                                                        in vrstaMaterijala varchar(30),
														in brojZica int,
														in vrsta varchar(20),
														in barkod varchar(20))
BEGIN
update proizvod, gitara set proizvod.Barkod = barkod,  proizvod.Naziv = naziv,
			proizvod.Cijena = cijena, proizvod.JIB_D = jib_d, proizvod.JIB_P = JIB_P,
			proizvod.VrstaMaterijala = vrstaMaterijala,
			gitara.BrojZica = brojZica, gitara.Vrsta = vrsta
			where proizvod.Barkod = barkod;
END$$
delimiter ;

delimiter $$
create procedure `azuriraj_bubanj`(
                                                       
                                                        in naziv varchar(30),
                                                        in cijena varchar(10),
                                                        in jib_p int,
                                                        in jib_d int,
                                                        in vrstaMaterijala varchar(30),
														in vrsta varchar(20),
														in barkod varchar(20))
BEGIN
update proizvod, bubanj set proizvod.Barkod = barkod,  proizvod.Naziv = naziv,
			proizvod.Cijena = cijena, proizvod.JIB_D = jib_d, proizvod.JIB_P = JIB_P,
			proizvod.VrstaMaterijala = vrstaMaterijala,
			bubanj.Vrsta = vrsta
			where proizvod.Barkod = barkod;
END$$
delimiter ;

delimiter $$
create procedure `azuriraj_klavijatura`(
                                                       
                                                        in naziv varchar(30),
                                                        in cijena varchar(10),
                                                        in jib_p int,
                                                        in jib_d int,
                                                        in vrstaMaterijala varchar(30),
														in tipKonfiguracije varchar(10),
														in vrsta varchar(20),
														in barkod varchar(20))
BEGIN
update proizvod, klavijatura set proizvod.Barkod = barkod,  proizvod.Naziv = naziv,
			proizvod.Cijena = cijena, proizvod.JIB_D = jib_d, proizvod.JIB_P = JIB_P,
			proizvod.VrstaMaterijala = vrstaMaterijala,
			klavijatura.TipKonfiguracije = tipKonfiguracije, klavijatura.Vrsta = vrsta
			where proizvod.Barkod = barkod;
END$$
delimiter ;


create view prikaz_dobavljaca as
select * from dobavljac
order by Naziv;


create view prikaz_racuna_dobavljaca as
select BrojRacuna from racun_dobavljaca
natural join dobavljac
where racun_dobavljaca.DOBAVLJAC_JIB_D = dobavljac.JIB_D;


delimiter $$
create procedure insert_into_ulaznafaktura(in datumIzdavanja date, in ukupnaCijena varchar(10),
									in mjestoIzdavanja varchar(30), in jib_d int,
                                    in organizacionaJedinicaId int, in jmbg varchar(13), in brojRacuna varchar(20))
						
begin
	insert into faktura(FakturaId, MjestoIzdavanja, DatumIzdavanja, UkupnaCijena) values (null, mjestoIzdavanja,datumIzdavanja,ukupnaCijena);
    insert into ulazna_faktura(FakturaId, JIB_D, JMBG,OrganizacionaJedinicaId, BrojRacuna) values (LAST_INSERT_ID(), jib_d,
										jmbg,organizacionaJedinicaId,brojRacuna);
end$$
delimiter ;

delimiter $$
create procedure insert_into_stavka (in barkod varchar(20), in fakturaId int, in kolicina int, in cijena varchar(10))
						
begin
	insert into stavka_fakture(StavkaID, Cijena, Kolicina, Barkod, FakturaID) values (null, cijena, kolicina, barkod, fakturaId);
end$$
delimiter ;


delimiter $$
create trigger trgovac_delete_trigger after delete
on trgovac
for each row
BEGIN
update prodavnica_trgovac
set DatumDo = now()
where old.jmbg = prodavnica_trgovac.jmbg
and old.OrganizacionaJedinicaID = prodavnica_trgovac.OrganizacionaJedinicaID;
END$$
delimiter ; 



delimiter $$
create trigger trgovac_insert_trigger after insert
on trgovac
for each row
BEGIN
 insert into prodavnica_trgovac(OrganizacionaJedinicaID, JMBG, DatumOd)
 values (new.OrganizacionaJedinicaID, new.JMBG, now());
END$$
delimiter ;



delimiter $$
create trigger administrativni_radnik_delete_trigger after delete
on administrativni_radnik
for each row
BEGIN
update administrativni_radnik_a
set DatumDo = now()
where old.jmbg = administrativni_radnik_a.jmbg
and old.OrganizacionaJedinicaID = administrativni_radnik_a.OrganizacionaJedinicaID;
END$$
delimiter ; 


delimiter $$
create trigger administrativni_radnik_insert_trigger after insert
on administrativni_radnik
for each row
BEGIN
 insert into administrativni_radnik_a(DatumOd,OrganizacionaJedinicaID, JMBG)
 values (now(),new.OrganizacionaJedinicaID, new.JMBG);
END$$
delimiter ;


delimiter $$
create trigger magacioner_delete_trigger after delete
on magacioner
for each row
BEGIN
update magacioner_m
set DatumDo = now()
where old.jmbg = magacioner_m.jmbg
and old.OrganizacionaJedinicaID = magacioner_m.OrganizacionaJedinicaID;
END$$
delimiter ; 


delimiter $$
create trigger magacioner_insert_trigger after insert
on magacioner
for each row
BEGIN
 insert into magacioner_m(DatumOd,JMBG,OrganizacionaJedinicaID)
 values (now(),new.JMBG,new.OrganizacionaJedinicaID);
END$$
delimiter ;

delimiter $$
create procedure insert_into_proizvod_dustupnost (in barkod char(30),
 in organizacionaJedinicaId int, in kolicina int)
						
begin
	if exists(select * from proizvod_dostupnost
		where proizvod_dostupnost.Barkod=barkod
		and proizvod_dostupnost.OrganizacionaJedinicaID=organizacionaJedinicaId) then
        
		update proizvod_dostupnost
		set proizvod_dostupnost.Kolicina=kolicina + proizvod_dostupnost.Kolicina
        where proizvod_dostupnost.Barkod=Barkod
        and proizvod_dostupnost.OrganizacionaJedinicaID=organizacionaJedinicaId;
    else
    	insert into proizvod_dostupnost(Barkod, OrganizacionaJedinicaID, Kolicina) values (barkod, organizacionaJedinicaId, kolicina);
    end if;
end$$
delimiter ;


create view prikaz_proizvod_dostupnost as
select Barkod,OrganizacionaJedinicaID,Kolicina,Naziv,Cijena,VrstaMaterijala
from proizvod_dostupnost
natural join proizvod;

create view prikaz_kupca as
select KupacID
from kupac;


delimiter $$
create procedure insert_into_izlaznafaktura (in datumIzdavanja date, in ukupnaCijena varchar(10),
									in mjestoIzdavanja varchar(30),
                                    in kupacId int, in organizacionaJedinicaId int, in jmbg varchar(13))
						
begin
	insert into faktura(FakturaId, MjestoIzdavanja, DatumIzdavanja, UkupnaCijena) values (null, mjestoIzdavanja, datumIzdavanja, ukupnaCijena);
    insert into izlazna_faktura(FakturaId, JMBG, OrganizacionaJedinicaID, KupacID) values (LAST_INSERT_ID(), jmbg,
										organizacionaJedinicaId, kupacId);
end$$
delimiter ;


delimiter $$
create procedure azuriraj_proizvod_dostupnost (in barkod char(30),
 in organizacionaJedinicaId int, in kolicina int, out kolicinaNaRaspolaganju boolean)
						
begin
			
			if exists(select *
            from proizvod_dostupnost as p
            where p.Barkod=barkod
            and p.OrganizacionaJedinicaID=organizacionaJedinicaId
            and p.Kolicina >= kolicina) then update proizvod_dostupnost as p
			set p.Kolicina= p.Kolicina - kolicina 
			where p.Barkod=barkod
			and p.OrganizacionaJedinicaID=organizacionaJedinicaId;
            set kolicinaNaRaspolaganju=true;
            else
				set kolicinaNaRaspolaganju=false;
            end if;
			delete from proizvod_dostupnost
            where proizvod_dostupnost.Kolicina = 0;
end$$
delimiter ;

create view prikaz_za_rad_sa_proizvodima as
select OrganizacionaJedinicaID from za_rad_sa_proizvodima;

create view prikaz_prodavnice as
select OrganizacionaJedinicaID from prodavnica;


create view prikaz_proizvodjaca as
select JIB_P,proizvodjac.Naziv as nazivP,Telefon,proizvodjac.MjestoID,mjesto.Naziv as nazivM
from proizvodjac INNER JOIN mjesto
where proizvodjac.MjestoID=mjesto.MjestoID;



delimiter $$
create procedure insert_into_proizvodjac (in jib_p int,
									in naziv varchar(20), in telefon varchar(20),in mjestoId int)
						
begin
	insert into proizvodjac(JIB_P, Naziv, Telefon, MjestoID)
    values (jib_p, naziv, telefon, mjestoId)
	ON DUPLICATE KEY UPDATE Naziv=naziv, Telefon=telefon, MjestoID=mjestoId;
end$$
delimiter ;

create view prikaz_zaposlenih as
select * from zaposleni
NATURAL JOIN mjesto
where zaposleni.MjestoID=mjesto.MjestoID
and zaposleni.Aktivan=true;

delimiter $$
create procedure insert_into_trgovac (in jmbg varchar(13),
									in ime varchar(15), in prezime varchar(20),in datumRodjenja date,
                                    in adresa varchar(45), in mjestoId int, in iznosPlate varchar(10),
                                    in korisnickoIme varchar(15), in lozinka varchar(15), in organizacionaJedinicaId int)
						
begin
	insert into zaposleni(JMBG, Ime, Prezime, DatumRodjenja, IznosPlate,Adresa, KorisnickoIme, Lozinka, MjestoID)
    values (jmbg, ime, prezime, datumRodjenja, iznosPlate,adresa, korisnickoIme, lozinka,mjestoId)
	ON DUPLICATE KEY UPDATE Ime=ime, Prezime=prezime, DatumRodjenja=datumRodjenja,
    Adresa=adresa,MjestoID=mjestoId,IznosPlate=iznosPlate,KorisnickoIme=korisnickoIme,Lozinka=lozinka;
    insert into trgovac(JMBG, OrganizacionaJedinicaID) values (jmbg, organizacionaJedinicaId)
     ON DUPLICATE KEY UPDATE OrganizacionaJedinicaID=organizacionaJedinicaId;
end$$
delimiter ;


delimiter $$
create procedure insert_into_magacioner (in jmbg varchar(13),
									in ime varchar(15), in prezime varchar(20),in datumRodjenja date,
                                    in adresa varchar(45), in mjestoId int, in iznosPlate varchar(10),
                                    in korisnickoIme varchar(15), in lozinka varchar(15), in organizacionaJedinicaId int)
						
begin
	insert into zaposleni(JMBG, Ime, Prezime, DatumRodjenja, IznosPlate,Adresa, KorisnickoIme, Lozinka, MjestoID)
    values (jmbg, ime, prezime, datumRodjenja, iznosPlate,adresa, korisnickoIme, lozinka,mjestoId)
	ON DUPLICATE KEY UPDATE Ime=ime, Prezime=prezime, DatumRodjenja=datumRodjenja,
    Adresa=adresa,MjestoID=mjestoId,IznosPlate=iznosPlate,KorisnickoIme=korisnickoIme,Lozinka=lozinka;
    insert into magacioner(JMBG, OrganizacionaJedinicaID) values (jmbg, organizacionaJedinicaId)
    ON DUPLICATE KEY UPDATE OrganizacionaJedinicaID=organizacionaJedinicaId;
end$$
delimiter ;


delimiter $$
create procedure insert_into_administrativni_radnik (in jmbg varchar(13),
									in ime varchar(15), in prezime varchar(20),in datumRodjenja date,
                                    in adresa varchar(45), in mjestoId int, in iznosPlate varchar(10),
                                    in korisnickoIme varchar(15), in lozinka varchar(15), in organizacionaJedinicaId int)
						
begin
	insert into zaposleni(JMBG, Ime, Prezime, DatumRodjenja, IznosPlate,Adresa, KorisnickoIme, Lozinka, MjestoID)
    values (jmbg, ime, prezime, datumRodjenja, iznosPlate,adresa, korisnickoIme, lozinka,mjestoId)
	ON DUPLICATE KEY UPDATE Ime=ime, Prezime=prezime, DatumRodjenja=datumRodjenja,
    Adresa=adresa,MjestoID=mjestoId,IznosPlate=iznosPlate,KorisnickoIme=korisnickoIme,Lozinka=lozinka;
    insert into administrativni_radnik(JMBG, OrganizacionaJedinicaID) values (jmbg, organizacionaJedinicaId)
    ON DUPLICATE KEY UPDATE OrganizacionaJedinicaID=organizacionaJedinicaId;
end$$
delimiter ;


create view prikaz_trgovca as
select * from zaposleni
NATURAL JOIN trgovac
NATURAL JOIN mjesto
where zaposleni.MjestoID=mjesto.MjestoID;

create view prikaz_magacionera as
select * from zaposleni
NATURAL JOIN magacioner
NATURAL JOIN mjesto
where zaposleni.MjestoID=mjesto.MjestoID;

create view prikaz_administrativnog_radnika as
select * from zaposleni
NATURAL JOIN administrativni_radnik
NATURAL JOIN mjesto
where zaposleni.MjestoID=mjesto.MjestoID;


delimiter $$
CREATE TRIGGER trgovac_trigger AFTER UPDATE ON zaposleni
FOR EACH ROW
BEGIN

if exists(select * from trgovac where trgovac.JMBG = NEW.JMBG) then
	update prodavnica_trgovac
    set DatumDo = now()
    where prodavnica_trgovac.JMBG = new.JMBG;
END IF;
END$$
delimiter ;

delimiter $$
CREATE TRIGGER magacioner_trigger AFTER UPDATE ON zaposleni
FOR EACH ROW
BEGIN

if exists(select * from magacioner where magacioner.JMBG = NEW.JMBG) then
	update magacioner_m
    set DatumDo = now()
    where magacioner_m.JMBG = new.JMBG;
END IF;
END$$
delimiter ;

delimiter $$
CREATE TRIGGER administrativni_radnik_trigger AFTER UPDATE ON zaposleni
FOR EACH ROW
BEGIN

if exists(select * from administrativni_radnik where administrativni_radnik.JMBG = NEW.JMBG) then
	update administrativni_radnik_a
    set DatumDo = now()
    where administrativni_radnik_a.JMBG = new.JMBG;
END IF;
END$$
delimiter ;
