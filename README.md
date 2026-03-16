# Dictionary – Generisk hash-baseret tabel i Java

## Oversigt
Projektet består primært af 2 separate klasser:
- `Dictionary<K, V>` – Den overordnede datastruktur.
- `Entry<K, V>` – En intern 'container' til at gemme nøgle/værdi-par.

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

## Overordnet design

### 1. Dictionary Struktur
Klassen `Dictionary<K, V>` er generisk, hvilket betyder, at den kan bruges med vilkårlige datatyper til både nøgler og værdier, så længe nøglen understøtter `hashCode()` og `equals()` korrekt.

Eksempel:
- `Dictionary<String, Integer>`
- `Dictionary<Integer, String>`
- `Dictionary<String, Person>`

Dette gør løsningen fleksibel og genanvendelig.

### 2. Intern lagring med array af `Entry`
Dictionary’en gemmer data i et internt array:

```java
private Entry<K, V>[] storage;
