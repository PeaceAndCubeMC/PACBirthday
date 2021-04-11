package fr.peaceandcube.pacbirthday.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class BirthdayData {
	private static File file;
	private static FileConfiguration config;
	
	public static void load(Plugin plugin, String name) {
		file = new File(plugin.getDataFolder(), name);
		
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

	public static void save() {
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

	public static String getBirthday(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			String birthday = section.getString("date");
			return birthday;
		}
		return null;
	}

	public static List<String> getBirthdays(String birthday) {
		Set<String> uuids = config.getKeys(false);
		if (!uuids.isEmpty()) {
			List<String> players = new ArrayList<>();
			for (String uuid : uuids) {
				if (config.getConfigurationSection(uuid).getString("date").equals(birthday)) {
					players.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
				}
			}
			return players;
		}
		return new ArrayList<>();
	}
	
	public static void setBirthday(String playerUuid, String birthday) {
		ConfigurationSection section = config.createSection(playerUuid);
		section.set("date", birthday);
		section.set("rewarded_count", 0);
		save();
	}

	public static int getLastRewardedYear(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			return section.getInt("last_rewarded_year", -1);
		}
		return -1;
	}

	public static void setLastRewardedYear(String playerUuid, int year) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			section.set("last_rewarded_year", year);
		}
		save();
	}

	public static int getRewardedCount(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			return section.getInt("rewarded_count");
		}
		return 0;
	}

	public static void incrementRewardedCount(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			int currentCount = section.getInt("rewarded_count");
			section.set("rewarded_count", currentCount + 1);
		}
		save();
	}
}
