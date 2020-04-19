package fr.peaceandcube.pacbirthday.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacbirthday.util.Utils;

public class BirthdaysCommand implements CommandExecutor, TabExecutor {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdaysToday = config.getString("birthdays_today");
	public String noBirthdayToday = config.getString("no_birthday_today");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 && sender.hasPermission("pacbirthday.today")) {
			List<String> players = BirthdayData.getBirthdays(Utils.getCurrentDay());
			if (!players.isEmpty()) {
				String playerNames = "";
				for (String player : players) {
					playerNames += ChatColor.YELLOW + player + " ";
				}
				
				sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + this.birthdaysToday, players.size(), playerNames));
			} else {
				sender.sendMessage(ChatColor.RED + this.noBirthdayToday);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return new ArrayList<>();
	}
}
