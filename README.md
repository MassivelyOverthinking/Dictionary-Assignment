# Dictionary – Generisk hash-baseret tabel i Java

## Overordnet Design
Projektet består primært af 2 separate klasser:
- `Dictionary<K, V>` – Den overordnede datastruktur.
- `Entry<K, V>` – En intern 'container' til at gemme nøgle/værdi-par.
Valgte yderligere at inkludere 2 `Constructor`-funktioner for nemmere at kunne teste implementationen.
En `Constructor`-metode hvori brugeren kunne specificere både Kapacitet og tilladte `load factor` før `Rehashing`.
En `Constructor`-metode hvori alt underliggende funktionalitet er håndteret på bagended. 

Derudover valgte jeg også at implementere en `Placeholder`-enum med statusværdier såsom:
- `Empty` - Ingen værdi er på nuværende tidspunkt lagret her.
- `Occupied` - Der eksisterer allerede en værdi her.
- `Tombstone` - Der har førhenværende eksisteret en værdi her. 
Grunden til at jeg valgte at benytte denne markør-værdi var, at når `Probe Chain` funktionalitet
finder sted er det nødvendigt for vores applikation at kunne se forskel på en Tom-plads, og en
en plads hvori der førhenværende eksisterede en værdi. `Tombstone` repræsenterer denne nødvendige status.

## Hashing
For at sikre at vi altid ender med et konkret og korrekt index i vores interne `Storage`-array
anvender jeg Javas `Math`-biblotek til at undgå negative værdier. 

## Occupied List
For at sikre at vi altid ender med et konkret og korrekt index i vores interne `Storage`-array
anvender jeg Javas `Math`-biblotek til at undgå negative værdier. 

## Rehashing
Når den interne `Load factor` overstiger den tilladte `Threshold` (RehashNeeded), som specificeret i vores `Constructor`, så starter applikationen selv en `Rehashing`. Måden hvorpå jeg valgte at lave denne funktionalitet var at, først gemme den eksisterende 
`Storage`-array og `OccupiedList`-liste, og derefter opdatere alle interne variables med ny kapacitet, nyt hash-array, ny occupied-liste,
og ny størrelse. Derved kan jeg genanvende applikationens interne `insert`-metode til at håndtere alt den interna logik og genindsættelse.

### 2. Intern lagring med array af `Entry`
Dictionary’en gemmer data i et internt array:

```java
private Entry<K, V>[] storage;
