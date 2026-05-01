# CLAUDE.md - Magical Torches

## Projekt-Übersicht

**Magical Torches** ist ein NeoForge Minecraft Mod für Minecraft 1.21.1.
- **Mod ID**: `magical_torches`
- **Package**: `de.geheimagentnr1.magical_torches`
- **Java Version**: 21
- **NeoForge Version**: 21.1.x

Fügt Fackeln mit magischen Effekten hinzu.

## Abhängigkeiten

Keine Mod-Abhängigkeiten - eigenständiger Mod.

## Projektstruktur

```
src/main/java/de/geheimagentnr1/magical_torches/
├── MagicalTorches.java              # Haupt-Mod-Klasse
├── config/
│   ├── ServerConfig.java            # Server-Konfiguration
│   └── SoundMufflersHolder.java     # Sound-Muffler Daten
├── handlers/
│   ├── SoundMufflingHandler.java    # Sound-Dämpfung Handler
│   └── SpawnBlockingHandler.java    # Spawn-Blocking Handler
├── helpers/
│   ├── NBTHelper.java               # NBT-Utilities
│   ├── RadiusHelper.java            # Radius-Berechnungen
│   ├── ResourceLocationBuilder.java # ResourceLocation Builder
│   ├── SoundMufflerHelper.java      # Sound-Muffler Helper
│   └── SpawnBlockerHelper.java      # Spawn-Blocker Helper
└── network/
    ├── Network.java                 # Netzwerk-Handler
    ├── AddSoundMufflerMsg.java      # Netzwerk-Paket
    ├── InitSoundMufflersMsg.java    # Netzwerk-Paket
    └── RemoveSoundMufflerMsg.java   # Netzwerk-Paket
```

## Besonderheiten

- **Spawn-Blocking**: Fackeln können Mob-Spawns in einem Radius blockieren
- **Sound-Muffling**: Fackeln können Sounds in einem Radius dämpfen
- **Netzwerk-Kommunikation**: Client-Server Sync für Sound-Muffler

## Code-Stil

- **Annotations**: `@NotNull` aus `org.jetbrains.annotations`
- **Lombok**: Projekt nutzt Lombok
- **Formatierung**: Leerzeichen nach `(` und vor `)` bei Methodenaufrufen

## Build & Test

```bash
./gradlew build
./gradlew runClient
./gradlew runServer
```

## Deployment

- **CurseForge**: `./gradlew curseforge`
- **Modrinth**: `./gradlew modrinth`

## Testing

### Java-Versionen

Verschiedene Java-Versionen sind unter `C:\Program Files\Eclipse Adoptium` installiert. Für einen Gradle-Build muss die passende Java-Version gewählt werden:

```powershell
# Java 21 für MC 1.20.5+ (NeoForge)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot"
./gradlew build
```

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Für Integration Tests in einer echten Minecraft-Umgebung:

```bash
./gradlew runGameTestServer
```

GameTest-Klassen werden mit `@GameTestHolder` annotiert und liegen unter `src/main/java/.../elements/gametests/`.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus
3. **GameTests**: Startet GameTestServer (optional)

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |
