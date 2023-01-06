insert into MJESTO values (1,"Doboj");
insert into MJESTO values (2,"Banjaluka");
insert into MJESTO values (3,"Prijedor");
insert into MJESTO values (4,"Bijeljina");
insert into MJESTO values (5,"Istočno Sarajevo");
insert into MJESTO values (6,"Prnjavor");
insert into MJESTO values (7,"Modriča");


insert into BANKA values(1,"AdresaBanke1","Banka1",1);
insert into BANKA values(2,"AdresaBanke2","Banka2",2);
insert into BANKA values(3,"AdresaBanke3","Banka3",3);
insert into BANKA values(4,"AdresaBanke4","Banka4",4);
insert into BANKA values(5,"AdresaBanke5","Banka5",5);
insert into BANKA values(6,"AdresaBanke6","Banka6",6);
insert into BANKA values(7,"AdresaBanke7","Banka7",7);


insert into KUPAC values(1,"mail1@gmail.com",1);
insert into KUPAC values(2,"mail2@gmail.com",4);
insert into KUPAC values(3,"mail3@gmail.com",5);
insert into KUPAC values(4,"mail4@gmail.com",5);
insert into KUPAC values(5,"mail5@gmail.com",7);


insert into PROIZVODJAC values(1,"Proizvodjac1","066-111/111",4);
insert into PROIZVODJAC values(2,"Proizvodjac2","066-222/222",6);
insert into PROIZVODJAC values(3,"Proizvodjac3","066-333/333",1);
insert into PROIZVODJAC values(4,"Proizvodjac4","066-444/444",1);
insert into PROIZVODJAC values(5,"Proizvodjac5","066-555/555",3);
insert into PROIZVODJAC values(6,"Proizvodjac6","066-666/666",2);


insert into ZAPOSLENI values("1111111111111","Petar","Petrović",'1988-02-03',"950.00","AdresaZ1","zaposleni1","sifra1",2);
insert into ZAPOSLENI values("2222222222222","Marko","Marković",'1990-10-11',"880.00","AdresaZ2","zaposleni2","sifra2",2);
insert into ZAPOSLENI values("3333333333333","Mirko","Mirković",'1984-02-10',"1050.00","AdresaZ3","zaposleni3","sifra3",2);
insert into ZAPOSLENI values("4444444444444","Pavle","Pavlović",'1996-08-08',"950.00","AdresaZ4","zaposleni4","sifra4",2);
insert into ZAPOSLENI values("5555555555555","Dejan","Dejanović",'1992-01-12',"1020.00","AdresaZ5","zaposleni5","sifra5",2);
insert into ZAPOSLENI values("6666666666666","Nikola","Nikolić",'1995-12-03',"1000.00","AdresaZ6","zaposleni6","sifra6",2);
insert into ZAPOSLENI values("7777777777777","Stefan","Stefanović",'1989-04-06',"970.00","AdresaZ7","zaposleni7","sifra7",2);


insert into ORGANIZACIONA_JEDINICA values(10,"AdresaOJ1",2);
insert into ORGANIZACIONA_JEDINICA values(20,"AdresaOJ2",2);


insert into ADMINISTRATIVNA values(10,"Admin@gmail.com");
insert into administrativni_radnik values("6666666666666",10);
insert into administrativni_radnik values("2222222222222",10);
insert into TELEFONA values("066458795",10);

insert into MAGACIN values(20);
insert into MAGACIONER values("5555555555555",20);
insert into MAGACIONER values("7777777777777",20);


insert into ZA_RAD_SA_PROIZVODIMA values(20,"065-123/456");


insert into PRODAVNICA values(20);

insert into TRGOVAC values("1111111111111",20);
insert into TRGOVAC values("4444444444444",20);


insert into DOBAVLJAC values(1,"Dobavljac1","065-987/789");
insert into DOBAVLJAC values(2,"Dobavljac2","066-963/369");
insert into DOBAVLJAC values(3,"Dobavljac3","065-145/541");
insert into DOBAVLJAC values(4,"Dobavljac4","066-753/357");
insert into DOBAVLJAC values(5,"Dobavljac5","065-284/482");
insert into DOBAVLJAC values(6,"Dobavljac6","066-695/596");


insert into RACUN_DOBAVLJACA values("R1","1000.00",1,2);
insert into RACUN_DOBAVLJACA values("R2","1700.00",2,4);
insert into RACUN_DOBAVLJACA values("R3","900.00",3,6);
insert into RACUN_DOBAVLJACA values("R4","1150.00",4,7);
insert into RACUN_DOBAVLJACA values("R5","2000.00",5,2);
insert into RACUN_DOBAVLJACA values("R6","1650.00",6,3);


insert into PROIZVOD values("10000001","Proizvod1","300.00","Materijal1",2,3);
insert into PROIZVOD values("10000002","Proizvod2","450.00","Materijal2",2,3);
insert into PROIZVOD values("10000003","Proizvod3","420.00","Materijal2",2,3);
insert into PROIZVOD values("10000004","Proizvod4","720.00","Materijal3",2,3);
insert into PROIZVOD values("10000005","Proizvod5","360.00","Materijal4",2,3);
insert into PROIZVOD values("10000006","Proizvod6","250.00","Materijal5",2,3);
insert into PROIZVOD values("10000007","Proizvod7","1260.00","Materijal6",2,3);

insert into GITARA values(6,"10000001","Akustična");
insert into GITARA values(12,"10000002","Električna");
insert into GITARA values(4,"10000003","Bass");

insert into BUBANJ values("10000004","Metal/Rock");
insert into BUBANJ values("10000005","Jazz");

insert into KLAVIJATURA values("Upright","10000006","Sintisajzer");
insert into KLAVIJATURA values("Grand","10000007","Klavir");


insert into administrativni_radnik values("6666666666666",10);
