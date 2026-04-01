# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Hytale server plugin (Java) that dumps all registered item IDs (vanilla + mods) into a text file on server startup. Uses the official Hytale Plugin API (`com.hypixel.hytale`).

## Toolchain

- **Java:** Amazon Corretto 25 (via asdf, see `.tool-versions`)
- **Build:** Gradle 9.2.0 (wrapper included)
- **Plugin Framework:** [ScaffoldIt](https://github.com/scaffoldit) Gradle plugin (`dev.scaffoldit` v0.2.x) — configures Hytale patchline, version, and manifest in `settings.gradle.kts`

## Build Commands

```bash
./gradlew build          # Compile + create JAR
./gradlew buildAll       # Build JARs for all target Hytale versions
./gradlew runServer      # Start local dev server with plugin (devserver/)
./gradlew clean build    # Clean rebuild
```

## Architecture

Single-class plugin — all dump logic lives in `src/main/java/dev/valgard/itemdumper/ItemDumper.java`:
- Extends `JavaPlugin`, overrides `start()`
- Reads `Item.getAssetMap()` from the Hytale registry
- Writes sorted items to `<plugin-data>/item_ids.txt`

`src/main/resources/manifest.json` — plugin metadata (also generated via ScaffoldIt in `settings.gradle.kts`).

`src/main/resources/Common/UI/Custom/Valgard_ItemDumper.png` — MAC (Mod Action Center) icon. Convention: `[Group]_[Name].png`.

`devserver/` — local server instance for testing (config, logs, mods). Used by `runServer`.

## Hytale Plugin API Patterns

- Entry point: class extends `JavaPlugin`, constructor takes `JavaPluginInit`
- Logging via `getLogger()` (Google Flogger style: `atInfo().log(...)`)
- Data path via `getDataDirectory()`
- Asset registries via static `getAssetMap()` methods on respective types
- MAC icon: set `IncludesAssetPack: true` in manifest, place PNG at `Common/UI/Custom/[Group]_[Name].png`
