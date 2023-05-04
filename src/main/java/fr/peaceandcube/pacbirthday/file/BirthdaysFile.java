package fr.peaceandcube.pacbirthday.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BirthdaysFile extends YamlFile {

	public BirthdaysFile(String name, Plugin plugin) {
		super(name, plugin);
	}

	public String getBirthday(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			return section.getString("date");
		}
		return null;
	}

	public List<String> getBirthdays(String birthday) {
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

	public void setBirthday(String playerUuid, String birthday) {
		ConfigurationSection section = config.createSection(playerUuid);
		section.set("date", birthday);
		section.set("rewarded_count", 0);
		save();
	}

	public int getLastRewardedYear(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			return section.getInt("last_rewarded_year", -1);
		}
		return -1;
	}

	public void setLastRewardedYear(String playerUuid, int year) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			section.set("last_rewarded_year", year);
		}
		save();
	}

	public int getRewardedCount(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			return section.getInt("rewarded_count");
		}
		return 0;
	}

	public void incrementRewardedCount(String playerUuid) {
		ConfigurationSection section = config.getConfigurationSection(playerUuid);
		if (section != null) {
			int currentCount = section.getInt("rewarded_count");
			section.set("rewarded_count", currentCount + 1);
		}
		save();
	}
}
