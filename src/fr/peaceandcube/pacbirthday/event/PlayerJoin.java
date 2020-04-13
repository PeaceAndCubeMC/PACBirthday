package fr.peaceandcube.pacbirthday.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.peaceandcube.pacbirthday.data.BirthdayData;

public class PlayerJoin implements Listener {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdayAnnouncement = config.getString("birthday_announcement");
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();
		
		String currentDay = this.getCurrentDay();
		String birthday = BirthdayData.getBirthday(player);
		
		if (currentDay.equals(birthday)) {
			server.broadcastMessage(String.format(ChatColor.LIGHT_PURPLE + this.birthdayAnnouncement, player.getName()));
		}
	}
	
	private String getCurrentDay() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM");
		return format.format(date);
	}
}
