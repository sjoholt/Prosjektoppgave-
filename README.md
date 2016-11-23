# Prosjektoppgave-Android
ID303911 - Mobile og distribuerte applikasjoner @ NTNU i Ålesund

Dette er en gruppeprosjektoppgave for studentene:
- Sindre Sjøholt
- Thomas Robert Tennøy
- Fredrik Mikael Valderhaug
- Gaute Hjellbakk Pettersen


Målsetningen med dette prosjektet har vært lage en Android-applikasjon, med tilhørende serverløsning for datakommunikasjon.
Prosjektet er utført i gruppe, og skal innleveres før eksaminering muntlig.


Server:
- REST-basert løsning basert på JAX-RS: Java API for RESTful Web Services
- Utviklet i Netbeans
- Satt opp med Derby database-løsning
- Bruker Payara-server for Glassfish

Applikasjon:
- Nyhetsapplikasjon for den fiktive nettavisen Tungrocken
- Krever pålogging for tilgang til innhold
- Krever pålogging hver gang applikasjonen åpnes
- Pålogging håndtert med Basic HTTP Authentication 
- Registrerte brukere har full tilgang til alle nyhetsartikler
- Brukere kan vise og redigere sin personlige informasjon
- Brukere kan endre passord
