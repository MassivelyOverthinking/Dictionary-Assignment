# Dictionary – Generisk hash-baseret tabel i Java

## Oversigt
Projektet består primært af 2 separate klasser:
- `Dictionary<K, V>` – Den overordnede datastruktur.
- `Entry<K, V>` – En intern 'container' til at gemme nøgle/værdi-par.

Derudover valgte jeg også at implementere en `Placeholder`-enum med statusværdier såsom:
- `Empty` - Ingen værdi er på nuværende tidspunkt lagret her.
- `Occupied` - Der eksisterer allerede en værdi her.
- `Tombstone` - Der har førhenværende eksisteret en værdi her. 
Foruden denne implementation af en markør-værdi så ville den underliggende `Probe Chain` funktionalitet
fejle. 

## Formål
Formålet med implementationen er at demonstrere centrale principper inden for datastrukturer, herunder:

- hashing
- open addressing
- linear probing
- rehashing

## Overordnet design

### 1. Generisk struktur
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