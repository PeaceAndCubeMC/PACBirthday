package fr.peaceandcube.pacbirthday.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacbirthday.util.Month;

public class BirthdayCommand implements CommandExecutor, TabExecutor {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String playerBirthday = config.getString("player_birthday");
	public String playerNotFound = config.getString("player_not_found");
	public String playerNotSet = config.getString("player_not_set");
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 && sender.hasPermission("pacbirthday.see")) {
			String target = args[0];
			if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))) {
				Player player = Bukkit.getPlayer(target);
				this.sendBirthday(sender, player);
				return true;
			} else {
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
				if (offlinePlayer.hasPlayedBefore()) {
					this.sendBirthday(sender, offlinePlayer);
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + this.playerNotFound);
			return true;
		}
		return false;
	}
	
	private void sendBirthday(CommandSender sender, OfflinePlayer player) {
		String birthday = BirthdayData.getBirthday(player);
		if (birthday != null) {
			String day = birthday.substring(0, 2);
			String month = Month.fromNumber(Integer.parseInt(birthday.substring(3, 5))).getLocalizedName();
			sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + this.playerBirthday, player.getName(), day, month));
		} else {
			sender.sendMessage(ChatColor.RED + this.playerNotSet);
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 && sender.hasPermission("pacbirthday.see")) {
			List<String> players = new ArrayList<>();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
					players.add(player.getName());
				}
			}
			return players;
		}
		return new ArrayList<>();
	}
}
