# CLAUDE.md - Magical Torches

Fügt 8 Fackeln mit magischen Effekten hinzu: Spawn-Blocking im Radius (feindliche Mobs in vier Größen, Fledermäuse, alle Mobs), Blocker für das Eierlegen von Hühnern und Sound-Muffling. Mod-ID `magical_torches`, Package `de.geheimagentnr1.magical_torches`. Client+Server (`displayTest=MATCH_VERSION`, Blöcke und Netzwerkpakete); clientseitiger Code auf NeoForge nur über `MagicalTorchesClientSetup` (`@EventBusSubscriber( value = Dist.CLIENT )`).

Diese Datei ist auf allen `develop_*`-Branches identisch; Quelle: `../Docs/mod-claude/MagicalTorches.md` (Änderungen dort, dann `Docs/tools/sync_mod_claude_md.sh`). Versionsstand: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Versionen

- Forge: `develop_1.18.2` bis `develop_1.21`, dazu `develop_1.21.1_forge`.
- NeoForge: `develop_1.21.1`, `develop_1.21.2` (1.21.2 - 1.21.4), `develop_1.21.5`, `develop_1.21.6` (1.21.6 - 1.21.10), `develop_1.21.11`, `develop_26.1` (ein Jar 26.1 - 26.3).
- Jar-Grenzen: 1.21.2 (Registrierung mit ID, `EntitySpawnReason`), 1.21.5 (`onRemove`), 1.21.6 (`ValueIOSerializable`-Attachments), 1.21.11 (`Identifier`, Block-Atlas), 26.1 (Tooling, Attachment-Datei). Ranges und Tags: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).
- Die NeoForge-Property heißt hier `neo_version` (nicht `neo_forge_version`).

## Abhängigkeiten

- Keine Mod-Abhängigkeiten, keine gebündelten Bibliotheken.
- Forge ab 1.20.1: MinecraftForgeAPI per `jarJar` (siehe [`../Docs/forge/README.md`](../Docs/forge/README.md)).

## Architektur

- `MagicalTorches` - Hauptklasse; registriert Blöcke (`ModBlocks`), Creative-Tab, `ModAttachments`, Netzwerk und die Game-Bus-Handler; Server-Config `ServerConfig.SPEC`.
- `elements/blocks/torches/` - `spawn_blocking/` (Small, Medium, Grand, Mega = feindliche Mobs; Bat; Alone = alle Mobs), `sound_muffling/SoundMufflingTorch`, `chicken_egg_spawning/ChickenEggTorch`; `BlockWithTooltip`.
- `elements/capabilities/` - Level-Daten pro Dimension mit den Fackelpositionen: `SpawnBlockingCapability`, `SoundMufflingCapability`, `ChickenEggSpawningCapability` plus Blocker-/Muffler-Klassen pro Fackeltyp. NeoForge: Level-Attachments (`ModAttachments`: `spawn_blocking`, `sound_muffling`, `chicken_egg_spawning`); Forge: Level-Capabilities (`ModCapabilities`).
- `handlers/` - `SpawnBlockingHandler` (`FinalizeSpawnEvent`, `EntityJoinLevelEvent`), `CommonSoundMufflingHandler` (schickt beim Login die Muffler an den Client), `ClientSoundMufflingHandler` (`PlaySoundEvent`, Reset beim Logout), `BlockTooltipHandler` (ab 1.21.5, `ItemTooltipEvent`), `LegacyAttachmentMigrationHandler` (ab 1.21.6).
- Forge-Branches: andere Handler-Aufteilung (z. B. 1.20.1 `SoundMufflingHandler`, `SpawnBlockingHandler`, `LateConfigInitilizationHandler`; 1.18.2 `ForgeEventHandler`/`ModEventHandler`).
- `network/` - `InitSoundMufflersMsg`, `AddSoundMufflerMsg`, `RemoveSoundMufflerMsg` (Sync der Sound-Muffler an die Clients).
- `helpers/` - Radius-, NBT-, Spawn-Blocker- und Sound-Muffler-Hilfen.
- Ressourcen: Rezepte mit `c:`-Tags, Loot-Tables, ab 1.21.4 `assets/magical_torches/items/*.json`, ab 1.21.11 `assets/minecraft/atlases/blocks.json` (Ei-Textur), ab 26.1 Resource-Overlay `mc_26_2`.

## Funktionen, Befehle, Konfiguration

- Keine Befehle. Wirkung endet beim Abbauen der Fackel.
- Server-Config `world/serverconfig/magical_torches-server.toml`: Radien `alone_torch_range`/`bat_torch_range` (64), `small/medium/grand/mega_torch_range` (16/32/64/128), `blocked_entities` (von den Hostile-Fackeln blockierte Entities), `sound_muffling_torch_range` (64) und `sound_muffling_torch_to_muffle_sounds` (Standard HOSTILE, NEUTRAL, BLOCKS, RECORDS), Hühnerei-Fackel `range` (16) und `should_invert_chicken_egg_blocking`.
- Welt-Daten: Fackelpositionen als Level-Attachments/Capabilities pro Dimension.

## Fallstricke

- **Attachment-Migration:** NeoForge 21.6 liest alte `ListTag`-Attachments nicht mehr (Daten weg) → `LegacyAttachmentMigrationHandler` liest in `LevelEvent.Load` die alte `neoforge_data_attachments.dat` (`DimensionDataStorage.readTagFromDisk`). Ab 26.1 liegt die Datei unter `dimensions/<ns>/<path>/data/neoforge/data_attachments.dat`, das Welt-Upgrade verschiebt die alte nicht - der Handler liest Listen- und Compound-Format aus dem alten Ordner. Log: `Migrated n magical_torches:... entries`.
- 1.21.2: Rezepte mit leerem Tag (alte `forge:`-Tags) laden nicht → `c:`-Tags. Im Release `1.21.1-3.0.1` waren dadurch 5 Rezepte nicht craftbar (gefixt in `1.21.1-3.0.2`).
- 1.21.5: `Block.onRemove` → `affectNeighborsAfterRemoval` (nur bei Blockwechsel mit Nachbar-Updates, nicht bei `/setblock`); `Block.appendHoverText` entfällt.
- 1.21.9 (im Jar 1.21.6 - 1.21.10 per Cross-fix): `noCollission()` → `noOcclusion()`, `isClientSide()`, `level().getServer()`.
- 1.21.11: Block-Modelle nur mit Block-Atlas-Texturen - Ei-Textur der Chicken Egg Torch über `atlases/blocks.json` als `magical_torches:block/torches/chicken_egg_spawning/egg`; sonst lila-schwarz (nur im Client-Log sichtbar).
- 26.x: `registerBlock( name, func )`, `PushReactionHelper.DESTROY`, Fledermaus per Registry-ID, Loot-Tables mit `conditions` und `condition`, Overlay `mc_26_2` (ab Format 85, `min_format`/`max_format`) für die umbenannte Textur `quartz_pillar_side`.

## Testen

- Pro Jar niedrigste und höchste Version: alle 8 Rezepte craften, Tooltips und Item-Texturen, Bat Torch stehend/hängend, Small Torch im Wasser, Spawn-Blocking (Nacht + `/tick sprint 6000`, `execute if entity @e[type=zombie,distance=..16]`), Chicken Egg Torch (Hühner + `/tick sprint 24000`), Sound Muffling (auch direkt nach dem Login), Abbauen beendet die Wirkung, Server-Neustart.
- Ab 1.21.6 Welt-Upgrade aus 1.21.5, ab 26.1 aus 1.21.x testen (Migrations-Log prüfen, Wirkung bleibt).
- Texturen nur im Client-Log prüfbar. Bytecode-Check: `../Docs/testing/tools/bincheck.ps1 -PropNeoForge neo_version`. Allgemein: [`../Docs/testing/README.md`](../Docs/testing/README.md).

## Links

- Wiki: https://github.com/GeheimagentNr1/MagicalTorches/wiki
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/magical-torches (Projekt-ID 389917)
- Modrinth: https://modrinth.com/mod/SUzIRI6Y
- Migration: [`../Docs/migrations/1.21.1-to-1.21.2.md`](../Docs/migrations/1.21.1-to-1.21.2.md) (4d), [`../Docs/migrations/1.21.10-to-1.21.11.md`](../Docs/migrations/1.21.10-to-1.21.11.md), [`../Docs/migrations/1.21.11-to-26.1.md`](../Docs/migrations/1.21.11-to-26.1.md)
