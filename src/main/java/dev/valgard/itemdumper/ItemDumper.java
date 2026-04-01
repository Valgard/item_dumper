package dev.valgard.itemdumper;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;

public class ItemDumper extends JavaPlugin {

    public ItemDumper(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void start() {
        DefaultAssetMap<String, Item> itemMap = Item.getAssetMap();
        if (itemMap == null) {
            getLogger().atWarning().log("Item-Registry ist nicht verfügbar");
            return;
        }

        var entries = itemMap.getAssetMap().entrySet();
        Path output = getDataDirectory().resolve("item_ids.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(
                Files.createDirectories(output.getParent()).resolve(output.getFileName()))) {
            writer.write("# Hytale Item IDs — " + Instant.now());
            writer.newLine();
            writer.write("# Format: id | maxStack | hasBlock | consumable");
            writer.newLine();
            writer.write("# Gesamt: " + entries.size() + " Items (Vanilla + alle geladenen Plugins)");
            writer.newLine();
            writer.newLine();

            entries.stream()
                .sorted(Comparator.comparing(e -> String.valueOf(e.getKey())))
                .forEach(entry -> {
                    try {
                        String id = String.valueOf(entry.getKey());
                        Item item = entry.getValue();
                        writer.write("%s | maxStack=%d | hasBlock=%s | consumable=%s".formatted(
                            id,
                            item.getMaxStack(),
                            item.hasBlockType(),
                            item.isConsumable()
                        ));
                        writer.newLine();
                    } catch (IOException e) {
                        getLogger().atWarning().log("Fehler beim Schreiben von %s: %s", entry.getKey(), e.getMessage());
                    }
                });

            getLogger().atInfo().log("Item-Dump fertig: %d Items → %s", entries.size(), output);

        } catch (IOException e) {
            getLogger().atSevere().log("Konnte Datei nicht schreiben: %s", e.getMessage());
        }
    }
}
