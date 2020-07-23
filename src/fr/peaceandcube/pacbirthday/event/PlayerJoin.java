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

import com.google.common.base.Splitter;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacpi.date.DateUtils;

public class PlayerJoin implements Listener {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdayAnnouncement = config.getString("birthday_announcement");
	public String birthdayNotSet = config.getString("birthday_not_set");
	public List<String> rewards = config.getStringList("rewards");
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();
		
		String currentDay = DateUtils.getCurrentMonthDay();
		String birthday = BirthdayData.getBirthday(player);
		
		if (birthday == null) {
			player.sendMessage(String.format(ChatColor.GOLD + this.birthdayNotSet, ChatColor.YELLOW + "" + ChatColor.BOLD + "/setbirthday"));
		}
		
		if (currentDay.equals(birthday)) {
			server.broadcastMessage(String.format(ChatColor.LIGHT_PURPLE + this.birthdayAnnouncement, player.getName()));
			
			this.changePlayerName(player);
			
			// TODO redo that
			if (!currentDay.equals(this.getLastPlayed(player))) {
				
				// Execute all commands defined
				for (String command : this.rewards) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), String.format(command, player.getName()));
				}
			}
		}
	}
	
	private void changePlayerName(Player player) {
		int nameLength = player.getName().length();
		String newName = "";
		ChatColor[] colors = {ChatColor.RED, ChatColor.GOLD, ChatColor.GREEN};
		int i = 0;
		for (final String substring : Splitter.fixedLength(Math.round(nameLength / 3) + 1).split(player.getName())) {
			newName += colors[i] + substring;
			i++;
		}
		newName += ChatColor.RESET;
		player.setDisplayName(newName);
		player.setPlayerListName(newName);
	}
	
	private String getLastPlayed(OfflinePlayer player) {
		long timestamp = player.getLastPlayed();
		String date = new SimpleDateFormat("dd-MM").format(new Date(timestamp));
		return date;
	}
}
