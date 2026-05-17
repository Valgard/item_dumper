<p align="center">
  <img src="src/main/resources/Common/UI/Custom/Valgard_ItemDumper.png" alt="Item Dumper Logo" width="128">
</p>

<h1 align="center">Item Dumper</h1>

<p align="center">
  A Hytale server plugin that dumps all registered item IDs (vanilla + mods) into a text file on server startup.
</p>

---

## Features

- Exports **all registered items** from the Hytale item registry, including modded items
- Outputs a sorted, human-readable text file with item metadata
- Runs automatically on server start — no commands needed

## Output Format

The plugin writes to `mods/Valgard_ItemDumper/item_ids.txt`:

```
# Hytale Item IDs — 2026-03-16T00:34:09.123Z
# Format: id | maxStack | hasBlock | consumable
# Gesamt: 3014 Items (Vanilla + alle geladenen Plugins)

hytale:acacia_log | maxStack=64 | hasBlock=true | consumable=false
hytale:ancient_sword | maxStack=1 | hasBlock=false | consumable=false
...
```

## Installation

1. Download the JAR matching your server version from [Releases](../../releases)
2. Place it in your server's `mods/` directory
3. Start (or restart) the server
4. Find the dump at `mods/Valgard_ItemDumper/item_ids.txt`

## Building from Source

Requires **Java 25** (Amazon Corretto) and the Hytale server SDK.

```bash
# Single build
./gradlew build

# Build for all supported Hytale versions
./gradlew buildAll
```

Output JARs are placed in `build/libs/`.

## Compatibility

| Plugin Version | Hytale Server Version |
|---|---|
| 1.0.0 | 2026.02.19-6c1fa8857 |
| 1.0.0 | 2026.03.26-89796e57b |

## License

All rights reserved.
