package fr.peaceandcube.pacbirthday.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacbirthday.util.Utils;

public class PlayerJoin implements Listener {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdayAnnouncement = config.getString("birthday_announcement");
	public List<String> rewards = config.getStringList("rewards");
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();
		
		String currentDay = Utils.getCurrentDay();
		String birthday = BirthdayData.getBirthday(player);
		
		if (currentDay.equals(birthday)) {
			server.broadcastMessage(String.format(ChatColor.LIGHT_PURPLE + this.birthdayAnnouncement, player.getName()));
			
			if (!currentDay.equals(this.getLastPlayed(player))) {
				
				// Execute all commands defined
				for (String command : this.rewards) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), String.format(command, player.getName()));
				}
			}
		}
	}
	
	private String getLastPlayed(OfflinePlayer player) {
		long timestamp = player.getLastPlayed();
		String date = new SimpleDateFormat("dd-MM").format(new Date(timestamp));
		return date;
	}
}
