package fr.peaceandcube.pacbirthday.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class YamlFile {
    protected final String name;
    protected final File file;
    protected FileConfiguration config;

    public YamlFile(String name, Plugin plugin) {
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);

        if (!this.file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                Files.createFile(this.file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            saveToDisk();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to save " + this.name + " to disk!");
        }
    }

    private void saveToDisk() throws IOException {
        if (this.config != null && this.file != null) {
            this.config.save(this.file);
        }
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
        save();
    }
}
