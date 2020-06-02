package fr.peaceandcube.pacbirthday.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public static List<String> getBirthdays(String birthday) {
		Set<String> uuids = config.getKeys(false);
		if (!uuids.isEmpty()) {
			List<String> players = new ArrayList<>();
			for (String uuid : uuids) {
				if (config.get(uuid).equals(birthday)) {
					players.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
				}
			}
			return players;
		}
		return new ArrayList<>();
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
