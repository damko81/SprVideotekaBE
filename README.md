Web verzija Videoteca. BackEnd: SpringBoot Java.
Ideja: Zaradi velikega števila posnetih filmov na različnih zunanjih diskih, sem želel voditi seznam filmov in njihovo lokacijo. Tako sem lahko z to aplikacijo hitro najdel določen filem.
Uporablja MSSqlServer14 za hrambo.
(SpringBoot BE) http://localhost:8080/
(Angular FE) http://localhost:4200/

Pot http://localhost:4200/ vodi na Videoteca, kot neprijavljen uporabik.
Neprijavljen uporabnik lahko le gleda seznam naloženih lilmov:
![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/cb01d540-96d0-4615-a3ad-5ee5cdd7f203)

Novi uporabnik izvede registracijo z klikom na Register:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/737d9b0e-995b-4d31-aff7-3eabc9fc95e1)

Ob prijavi z registriranim uporabnikom:

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/29015b0e-21cd-482f-82e1-0d8f3b79ba68)

Se odprejo razširjene možnosti videoteke:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/0b2f8bb3-b708-45f9-947b-e46d4be29745)

Izberem možnost Load movies, kjer vpišemo direktorij z filmi.
Filmi, ki so na izbranem direktoriju, se uvozijo v MSSqlServer14. Podatki o filmih se napolnejo preko IMDB parserja, ki je ročno narejen, torej se lahko z časom spreminja. Ta akcija je edina, ki mora vsebovati internetno povezavo, vse ostale akcije delujejo nad obstoječo podatkovno bazo in interneta več ne potrebuje.

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/09e718f4-1ce0-46f7-b96c-7fea530c40a7)

Prijava z autenticiranim uporabnikom = admin in geslom = password:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/73196116-37c9-4296-b523-288930423fd7)

Odprejo se administracijske možnosti, npr. Users za urejanje registriranih uporabnikov:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/65b00b5e-c528-4ae9-a044-d3b3837e82c6)

Pregled Users:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/1d7a495f-9a89-42a9-a156-8f962313ef90)

Edit:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/bc240fcc-74fb-450c-8104-5d8edddf6a13)

Popravljanje uporabniškega profila prijavljenega uporabnika:

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/53350985-757e-4748-b4cc-5f74aea2bdc5)

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/58759df1-9df9-4c89-a8ad-943582b11c5a)

Importanje Filmi.xml in exsportanje v Filmi.xml (Imp/Exp XML):

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/b2d2fca6-aa9f-4700-920e-572ff603dfb3)

Lahko se trenutno stanje naloženih filmov exsporta v datoteko Filmi.xml, ki se jo uporabi kot vir podatkov za namizno aplikacijo Videoteka.

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/2b54a223-fa64-4aa0-8225-9b6706d79099)

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/b44298c8-9c21-4d47-aacf-0663f2db45ef)

Z klikom na eksportano datoteko Filmi.xml posname na lokalni računalnik. Dodamo jo k namiszni aplikaciji Videoteka:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/0125245b-522c-42b0-8cb7-e8231286c56f)

Ob zagonu namizne aplikacije Videoteka, Filmi.xml se naložijo:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/8f2b17c6-fbb1-4c96-97ae-cac9e8c25a74)

Enako lahko uvozimo datoteko Filmi.xml, ki je nastala z namizno aplikacijo Videoteka.
Izbrana datoteka mora biti Filmi.xml.

Primer, da izberemo napačno datoteko in jo želimo uvoziti, javi napako:
![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/0454593b-389b-4c0c-b892-af28d2c93a85)

Izberem pravo datoteko in jo uvozim:
V seznamu datotek klienta vidimo [ime uporabnika]_Filmi.xml

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/b952c23d-8756-4619-a3d9-6bea9d4964ad)

Za dodajanje filmov v sistem kliknemo Load Movies, ki doda filme iz datoteke [ime uporabnika]_Filmi.xml:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/645c7e9a-e3cd-4ebf-a0da-71ed85978640)

V sistem se je odal novi filem Evil Dead, ki je sedaj viden:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/8d089cfa-5497-4f68-b317-6696e3a3970f)

Če se v sistem prijavimo kot uporabnik = admin z geslom = password, se v Imp/Exp XML vidijo uvozi vseh [username]_Filmi.xml datotek:

![image](https://github.com/damko81/AngularVideotekaFE/assets/162964541/23581f93-6585-4402-ac71-a77b283b584e)



