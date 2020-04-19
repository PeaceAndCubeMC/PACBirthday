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
import org.bukkit.entity.Player;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacbirthday.util.Month;

public class SetBirthdayCommand implements CommandExecutor, TabExecutor {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String birthdaySaved = config.getString("birthday_saved");
	public String birthdayAlreadySaved = config.getString("birthday_already_saved");
	public String birthdayInvalid = config.getString("birthday_invalid");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 2 && player.hasPermission("pacbirthday.set")) {
				if (BirthdayData.getBirthday(player) == null) {
					if (this.isValidBirthday(args[0], args[1])) {
						String monthNumber = String.format("%02d", Month.fromString(args[1]).getNumber());
						BirthdayData.saveBirthday(player, String.format("%02d", Integer.parseInt(args[0])) + "-" + monthNumber);
						player.sendMessage(ChatColor.YELLOW + this.birthdaySaved);
					} else {
						player.sendMessage(ChatColor.RED + this.birthdayInvalid);
					}
				} else {
					player.sendMessage(ChatColor.RED + this.birthdayAlreadySaved);
				}
				
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidBirthday(String day, String month) {
		Month validMonth = Month.fromString(month);
		
		if (validMonth != null) {
			if (Integer.parseInt(day) > 0 && Integer.parseInt(day) <= validMonth.getMaxDays()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 && sender.hasPermission("pacbirthday.set")) {
			List<String> days = new ArrayList<>();
			for (int d = 1; d <= 31; d++) {
				if (Integer.toString(d).startsWith(args[0].toLowerCase())) {
					days.add(Integer.toString(d));
				}
			}
			return days;
		}
		
		if (args.length == 2 && sender.hasPermission("pacbirthday.set")) {
			List<String> localizedMonths = new ArrayList<>();
			for (Month month : Month.values()) {
				if (month.getLocalizedName().startsWith(args[1].toLowerCase())) {
					localizedMonths.add(month.getLocalizedName());
				}
			}
			return localizedMonths;
		}
		
		return new ArrayList<>();
	}
}
