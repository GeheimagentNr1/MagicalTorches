- Support for NeoForge 26.3.0.37 and newer: the server config is registered with the new config type (the jar for Minecraft 26.1 - 26.2 crashes there)
- An existing world config in `<world>/serverconfig` is copied once to `<world>/syncedconfig`, because NeoForge 26.3 only reads world configs from there (dedicated servers; in singleplayer move the file by hand)
Add compatibility for minecraft version 26.1 - 26.3

- Torches placed in worlds of older Minecraft versions keep working: the stored torch positions are migrated to the new save format and file when the world is loaded.
- Fixed the recipes of the sound muffling, small, medium, grand and mega torch (they used outdated forge tags and could not be crafted).
