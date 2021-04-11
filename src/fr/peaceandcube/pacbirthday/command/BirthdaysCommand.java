package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacpi.date.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BirthdaysCommand implements CommandExecutor, TabExecutor {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdaysToday = config.getString("birthdays_today");
	public String noBirthdayToday = config.getString("no_birthday_today");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 && sender.hasPermission("pacbirthday.today")) {

			Bukkit.getScheduler().runTaskAsynchronously(PACBirthday.getPlugin(PACBirthday.class), () -> {
				List<String> players = BirthdayData.getBirthdays(DateUtils.getCurrentMonthDay());
				if (!players.isEmpty()) {
					String playerNames = "";
					for (String player : players) {
						playerNames += ChatColor.YELLOW + player + " ";
					}

					sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + BirthdaysCommand.this.birthdaysToday, players.size(), playerNames));
				} else {
					sender.sendMessage(ChatColor.RED + BirthdaysCommand.this.noBirthdayToday);
				}
			});

			return true;
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return new ArrayList<>();
	}
}
