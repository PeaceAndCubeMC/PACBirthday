package fr.peaceandcube.pacbirthday.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BirthdayData {
	private static File file;
	private static FileConfiguration config;
	
	public static void loadFile(Plugin plugin) {
		file = new File(plugin.getDataFolder(), "birthdays.yml");
		
		if (!file.exists()) {
			plugin.getDataFolder().mkdirs();
			plugin.saveResource("birthdays.yml", true);
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void saveBirthday(Player player, String birthday) {
		config.set(player.getUniqueId().toString(), birthday);
		trySaveToDisk();
	}
	
	public static String getBirthday(OfflinePlayer player) {
		String uuid = player.getUniqueId().toString();
		if (config.get(uuid) != null) {
			String birthday = config.get(uuid).toString();
			return birthday;
		}
		return null;
	}
	
	public static void trySaveToDisk() {
		try {
			saveToDisk();
		} catch (IOException ex) {
			ex.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "Unable to save birthdays.yml to disk!");
		}
	}
	
	private static void saveToDisk() throws IOException {
		if (config != null && file != null) {
			config.save(file);
		}
	}
}
