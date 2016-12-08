# chkm8 - Shakki

**Aihe:** Shakki

Toteutuksessa kaksi pelaajaa voivat pelata shakkia aivan kuten lautapeliä.
Pelaajat valitsevat oman värinsä. Valkoinen pelaaja aloittaa
ensimmäisenä. Kaikki pelin 32 nappulaa sijoitetaan pelin alussa tavanomaisille
paikoilleen ja liikkuvat aivan kuten lautapelissä. Pelaajat voivat vuorotellen
tehdä pelin sääntöjen mukaisia siirtoja ja voittaja on se, joka onnistuu
tekemään matin.

Peliin on myös mahdollista implementoida pelikello, jonka avulla pelaajien
mietintäaikaa voidaan rajoittaa. Jos pelaajalle varattu aika pääsee loppumaan,
pelaaja häviää pelin. Tätä ominaisuutta ei välttämättä implementoida peliin.

Pelissä on yksinkertainen graafinen käyttöliittymä, eikä sen ulkoasuun
kiinnitetä kehityksessä erityisesti huomiota.


## Rakenne
Peli on toteutettu [Model-View-Controller](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
arkkitehtuurityylillä. Pelin model on kiedottu ChessState-luokkaan sisältää
kaikki itse shakkiin kuuluvat asiat, esimerkiksi pelilaudan Board ja
liikkumishistorian. Board puolestaan sisältää kaikki pelinappulat, jotka
laajentavat abstraktia luokkaa Piece ja toteuttavat kunkin nappulan erityispiirteet.

Pelin View on luokka ChessView, jonka tehtävänä on piirtää malli ja esittää se käyttäjälle.
ChessView saa Game-luokalta parametrina mallin ja kontrollerin.
Näiden avulla ChessView pystyy piirtäämään nappulat oikeille paikoilleen.

Pelin Controller on ChessController-luokka, joka käyttää PlayerInputController-luokkaa
lukemaan käyttäjän syötettä. Syötteen perusteella ChessController muuttaa mallia
liikuttamalla nappuloita.


![luokkakaavio](luokkakaavio.png)

## Sekvenssikaaviot

**Mallin alustaminen**

![initialize_model](initialize_model.png)


**Pelaaja liikuttaa nappulan hyväksyttyyn paikkaan**

Liikkuminen muodostuu kahdesta erillisestä osasta:
* Mallin päivitys
* Mallin piirtäminen

Käyttäjä liikuttaa nappulan haluamaansa sallittuun paikkaan (input). Kun Game
päivittää seuraavan kerran ChessControlleria, ChessController lukee
PlayerInputController-luokalta sijainnin, johon nappula siirrettiin. ChessController
pyytää ChessStatea liikuttamaan nappulan kyseiseen paikkaan.

Kun malli on päivitetty, Game pyytää ChessViewiä piirtämään mallin. ChessView
lukee nappulat ChessStatelta ja tarkistaa, onko käyttäjä raahaamassa nappuloita.
ChessView piirtää nappulat oikeille paikoilleen.


![move_piece](move_piece.png)