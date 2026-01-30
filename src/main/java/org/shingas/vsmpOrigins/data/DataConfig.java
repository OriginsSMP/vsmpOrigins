package org.shingas.vsmpOrigins.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataConfig {

    private final JavaPlugin plugin;
    private final File dataFile;
    private final Gson gson;

    // Cache
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public DataConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "userData.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        load();
    }

    private void load() {
        try {
            if (!dataFile.exists()) {
                plugin.getDataFolder().mkdirs();
                dataFile.createNewFile();
            }

            Type type = new TypeToken<Map<String, PlayerData>>() {}.getType();
            FileReader reader = new FileReader(dataFile);
            Map<String, PlayerData> rawData = gson.fromJson(reader, type);
            reader.close();

            if (rawData == null) return;

            for (Map.Entry<String, PlayerData> entry : rawData.entrySet()) {
                cache.put(UUID.fromString(entry.getKey()), entry.getValue());
            }

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load userData.json");
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Map<String, PlayerData> rawData = new HashMap<>();

            for (Map.Entry<UUID, PlayerData> entry : cache.entrySet()) {
                rawData.put(entry.getKey().toString(), entry.getValue());
            }

            FileWriter writer = new FileWriter(dataFile);
            gson.toJson(rawData, writer);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save userData.json");
            e.printStackTrace();
        }
    }

    private void safeCheck(UUID uuid) {
        if (!cache.containsKey(uuid)) {
            PlayerData temp = new PlayerData();
            temp.dwarfVision = true;
            temp.vampToggle = false;
            cache.put(uuid, temp);
        };
    }

    public boolean getDwarfVision(UUID uuid) {
        safeCheck(uuid);
        return cache.get(uuid).dwarfVision;
    }

    public boolean getVampToggle(UUID uuid) {
        safeCheck(uuid);
        return cache.get(uuid).vampToggle;
    }

    public void setDwarfVision(UUID uuid, boolean dwarfVision) {
        safeCheck(uuid);
        PlayerData data = cache.get(uuid);
        data.dwarfVision = dwarfVision;
    }

    public void setVampToggle(UUID uuid, boolean vampToggle) {
        safeCheck(uuid);
        PlayerData data = cache.get(uuid);
        data.vampToggle = vampToggle;
    }


}
