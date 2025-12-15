# AGENTS.md - Magical Torches

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
