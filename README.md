# Server_Innlevering

I denne oppgaven tar jeg i bruk en flertrådet serverløsning som skal fungere som en meny for klienter som kan opprette og 
fylle inn data i en databasetabell fra filen: 'subjects.csv' som er lagret i prosjektmappen.

Denne koden er en videreutvilking av koden i prosjektet ['WesterdalsTimetable'](https://github.com/MiniMarker/WesterdalsTimetable), 
hvor jeg her har forenklet databasen til kun å håndtere en enkelt tabell isteden for flere.

Dette README doukumentet inneholder instrukser som trengs for å få løsningen til å kjøre på din maskin.

**— Maven**

Når du åpner koden i ditt ønskede IDE er det viktig at du kjører enten kommadoen **mvn package**. Dette er for at filene 
som brukes for å opprette tabellen og fylle den med data skal genereres ved bruk av maven.

**— Properties**

I mappen 'resources' ligger det to property-filer. 

'serverConfig' inneholder host og portnummer som er nødvendige for å få løsningen til å fungere. 
Dette er innstillinger som ikke er nødvendige å endre på med mindre du har 
intensjon å koble til en klient på en annen maskin. Både klient og server bruker denne filen slik at en endring trer i 
kraft hos begge parter.

'dbConfig' inneholder navn på databasen, host, brukernavn og passord for opprettelse og tilkobling til databasen.
Dette er filer som er felter å endre for å kunne få prosjektet til å kjøre på din lokale maskin.

**— Kjør koden**

For å få få løsningen til å fungere må du først kjøre klassen **'Server'** sin main-metode. Deretter kan du koble til 
så mange klienter du bare vil ved å kjøre klassen **'Client'** sin main-metode.


### Enjoy


**Built With:**

+ [IntelliJ - IDE](https://www.jetbrains.com/idea/)
+ [Maven - Dependency håndtering og prosjekt format.](https://maven.apache.org)
+ [Java - Språk](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
+ [MySQL Workbench - Databasebehandling og språk](http://mysqlworkbench.org)
+ [GitHub](https://github.com/MiniMarker/Server_Innlevering)