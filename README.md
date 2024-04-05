Web verzija Videoteca. BackEnd: SpringBoot Java.
Ideja: Zaradi velikega števila posnetih filmov na različnih zunanjih diskih, sem želel voditi seznam filmov in njihovo lokacijo. Tako sem lahko z to aplikacijo hitro najdel določen filem.
Uporablja MSSqlServer14 za hrambo.
(SpringBoot BE) http://localhost:8080/
(Angular FE) http://localhost:4200/

Pot http://localhost:4200/ vodi na Videoteca, kot neprijavljen uporabik.
Neprijavljen uporabnik lahko le gleda seznam naloženih lilmov:
![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/55a43d45-49ca-4ac7-8911-3b809adcb7c5)

Ob prijavi z registriranim uporabnikom:

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/29015b0e-21cd-482f-82e1-0d8f3b79ba68)

Se odprejo razširjene možnosti videoteke:

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/93746def-02b0-4979-b1f8-16218c3a996c)

Izberem možnost Load movies, kjer vpišemo direktorij z filmi.
Filmi, ki so na izbranem direktoriju, se uvozijo v MSSqlServer14. Podatki o filmih se napolnejo preko IMDB parserja, ki je ročno narejen, torej se lahko z časom spreminja. Ta akcija je edina, ki mora vsebovati internetno povezavo, vse ostale akcije delujejo nad obstoječo podatkovno bazo in interneta več ne potrebuje.

![image](https://github.com/damko81/SprVideotekaBE/assets/162964541/09e718f4-1ce0-46f7-b96c-7fea530c40a7)

...itd...
