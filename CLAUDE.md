# CLAUDE.md - Magical Torches

## Projekt-Übersicht

**Magical Torches** ist ein NeoForge Minecraft Mod.
- **Mod ID**: `magical_torches`
- **Package**: `de.geheimagentnr1.magical_torches`
- **Java Version**: 21 (`develop_26.1`: 25, `jdk-25.0.4.7-hotspot`)

Fügt Fackeln mit magischen Effekten hinzu: Spawn-Blocking (feindliche Mobs, Fledermäuse, alle Mobs), Hühnerei-Blocker und Sound-Muffling. Die Fackelpositionen werden als Level-Attachments gespeichert (`ModAttachments`), die Sound-Muffler zusätzlich an die Clients gesendet.

| Branch | MC | Range | NeoForge (kompiliert gegen) | Grund für den Schnitt |
|---|---|---|---|---|
| `develop_1.21.1` | 1.21.1 | `[1.21.1,1.21.2)` | 21.1.x | |
| `develop_1.21.2` | 1.21.2 - 1.21.4 | `[1.21.2,1.21.5)` | `21.2.1-beta` | Blöcke über `BLOCKS.registerBlock( name, MyTorch::new, Properties.of() )` (ID), Items über `registerSimpleBlockItem`; `updateShape`-Signatur; `MobSpawnType` → `EntitySpawnReason`; Rezepte mit `c:`- statt `forge:`-Tags (leere Tags laden ab 1.21.2 nicht); `assets/magical_torches/items/*.json` für 1.21.4 |
| `develop_1.21.5` | 1.21.5 | `[1.21.5,1.21.6)` | `21.5.98` | `onRemove` → `affectNeighborsAfterRemoval` (nur bei Blockwechsel mit Nachbar-Updates, nicht bei `/setblock`); Tooltip über `BlockTooltipHandler` (`ItemTooltipEvent`); `CompoundTag`-Getter mit `Optional` |
| `develop_1.21.6` | 1.21.6 - 1.21.10 | `[1.21.6,1.21.11)` | `21.6.20-beta` | Attachments als `ValueIOSerializable` (Liste `entries`); `LegacyAttachmentMigrationHandler` übernimmt beim Laden die alten `ListTag`-Daten (≤ 1.21.5) aus `neoforge_data_attachments.dat`. 1.21.9-Cross-fixes: `noOcclusion()` statt `noCollission()`, `isClientSide()`, `level().getServer()` |
| `develop_1.21.11` | 1.21.11 | `[1.21.11,1.21.12)` | `21.11.45` | Aufbauend auf `develop_1.21.6`: `ResourceLocation` → `Identifier`, `ResourceKey.location()` → `identifier()`; Migration alter Attachment-Daten bleibt (Welten aus ≤ 1.21.5). Block-Modelle dürfen ab 1.21.11 nur Block-Atlas-Texturen nutzen: die Ei-Textur der Chicken Egg Torch wird über `assets/minecraft/atlases/blocks.json` als `magical_torches:block/torches/chicken_egg_spawning/egg` geladen |
| `develop_26.1` | 26.1 - 26.3 | `[26.1,27)` | `26.1.0.19-beta` (Java 25) | Aufbauend auf `develop_1.21.11`; Tooling Java 25 / Gradle 9.2.1 / moddev 2.0.147 / Lombok 1.18.48. `LegacyAttachmentMigrationHandler` liest die alte `neoforge_data_attachments.dat` aus dem alten Dimensionsordner (NeoForge 26.1 nutzt eine neue Datei, das Welt-Upgrade verschiebt sie nicht) - Listen- und Compound-Format. Cross-fixes: `registerBlock( name, func )`, `PushReactionHelper.DESTROY` (`DESTROY`/`POPPED`), Fledermaus per Registry-ID, `entityTags()`. Loot-Tables mit `conditions` und `condition`. Resource-Overlay `mc_26_2` (ab Format 85 = 26.2) mit dem Medium-Torch-Modell für die umbenannte Textur `quartz_pillar_side` |

Details: `../Docs/migrations/1.21.1-to-1.21.2.md`, Abschnitt 4d.

## Abhängigkeiten

Keine Mod-Abhängigkeiten - eigenständiger Mod.

## Projektstruktur

```
src/main/java/de/geheimagentnr1/magical_torches/
├── MagicalTorches.java                                        # Haupt-Mod-Klasse
├── MagicalTorchesClientSetup.java                             # Client-Dist-Setup
├── config/
│   ├── ServerConfig.java
│   └── SoundMufflersHolder.java
├── elements/
│   ├── blocks/
│   │   ├── BlockWithTooltip.java
│   │   ├── ModBlocks.java
│   │   └── torches/
│   │       ├── chicken_egg_spawning/
│   │       │   └── ChickenEggTorch.java
│   │       ├── sound_muffling/
│   │       │   └── SoundMufflingTorch.java
│   │       └── spawn_blocking/
│   │           ├── AloneTorch.java
│   │           ├── BatTorch.java
│   │           ├── GrandTorch.java
│   │           ├── HostileSpawnBlockingTorch.java
│   │           ├── MediumTorch.java
│   │           ├── MegaTorch.java
│   │           ├── SmallTorch.java
│   │           └── SpawnBlockingTorch.java
│   ├── capabilities/
│   │   ├── CapabilityData.java
│   │   ├── ICapabilityDataFactory.java
│   │   ├── ModAttachments.java
│   │   ├── chicken_egg_spawning/
│   │   │   ├── ChickenEggSpawningCapability.java
│   │   │   └── chicken_egg_blockers/
│   │   │       └── ChickenEggTorchBlocker.java
│   │   ├── sound_muffling/
│   │   │   ├── ISoundMufflerFactory.java
│   │   │   ├── SoundMuffler.java
│   │   │   ├── SoundMufflingCapability.java
│   │   │   └── sound_mufflers/
│   │   │       └── SoundMufflingTorchSoundMuffler.java
│   │   └── spawn_blocking/
│   │       ├── ISpawnBlockerFactory.java
│   │       ├── SpawnBlocker.java
│   │       ├── SpawnBlockingCapability.java
│   │       └── spawn_blockers/
│   │           ├── AloneTorchSpawnBlocker.java
│   │           ├── BatTorchSpawnBlocker.java
│   │           ├── GrandTorchSpawnBlocker.java
│   │           ├── HostileMobSpawnBlocker.java
│   │           ├── MediumTorchSpawnBlocker.java
│   │           ├── MegaTorchSpawnBlocker.java
│   │           └── SmallTorchSpawnBlocker.java
│   └── creative_mod_tabs/
│       └── ModCreativeModeTabs.java
├── handlers/
│   ├── BlockTooltipHandler.java                               # (ab develop_1.21.5) Tooltips über ItemTooltipEvent
│   ├── ClientSoundMufflingHandler.java
│   ├── CommonSoundMufflingHandler.java
│   ├── LegacyAttachmentMigrationHandler.java                  # (ab develop_1.21.6) Migration alter Attachment-Daten
│   └── SpawnBlockingHandler.java
├── helpers/
│   ├── NBTHelper.java
│   ├── RadiusHelper.java
│   ├── ResourceLocationBuilder.java
│   ├── SoundMufflerHelper.java
│   └── SpawnBlockerHelper.java
└── network/
    ├── AddSoundMufflerMsg.java
    ├── InitSoundMufflersMsg.java
    ├── Network.java
    └── RemoveSoundMufflerMsg.java
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
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot"
./gradlew build
```

Die NeoForge-Property heißt hier `neo_version` (Bytecode-Check: `../Docs/testing/tools/bincheck.ps1 -PropNeoForge neo_version`).

### Ingame-Test (pro Jar niedrigste und höchste Version)

Alle 8 Rezepte craften, Tooltips und Item-Texturen, Bat Torch stehend/hängend, Small Torch im Wasser, Spawn-Blocking (Nacht + `/tick sprint 6000`, `execute if entity @e[type=zombie,distance=..16]`), Chicken Egg Torch (Hühner + `/tick sprint 24000`), Sound Muffling (auch direkt nach dem Login), Abbauen beendet die Wirkung, Server-Neustart. Ab 1.21.6 zusätzlich Welt-Upgrade aus 1.21.5 (Log: `Migrated n magical_torches:... entries`).

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Ab `develop_1.21.2` gibt es keine GameTests mehr (Annotations-Framework ab 1.21.5 entfernt, trivialer Smoke-Test samt Run-Config und CI-Job gelöscht).

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |

## Referenzen

- [NeoForge Migration Primer](https://docs.neoforged.net/primer/docs/) — Dokumentiert API-Aenderungen zwischen Minecraft/NeoForge-Versionen; nuetzlich fuer die Pruefung von Breaking Changes beim Upgrade auf neue Versionen

---

## Wissensdatenbank

Versionsübergreifende Migrations- und Entwicklungs-Erkenntnisse (Breaking Changes, Fixes, Testumgebungs-Patterns) werden zentral in [`../Docs/`](../Docs/) gepflegt. Bei neuen relevanten Erkenntnissen dort ergänzen, nicht nur hier.
