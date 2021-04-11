package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacpi.date.LocalizedMonth;
import fr.peaceandcube.pacpi.player.PlayerErrors;
import fr.peaceandcube.pacpi.player.PlayerSuggestionProviders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BirthdayCommand implements CommandExecutor, TabExecutor {
	public FileConfiguration config = Bukkit.getPluginManager().getPlugin("PACBirthday").getConfig();
	public String playerBirthday = config.getString("player_birthday");
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
			sender.sendMessage(PlayerErrors.PLAYER_NOT_FOUND);
			return true;
		}
		return false;
	}
	
	private void sendBirthday(CommandSender sender, OfflinePlayer player) {
		String birthday = BirthdayData.getBirthday(player.getUniqueId().toString());
		if (birthday != null) {
			String day = birthday.substring(0, 2);
			String month = LocalizedMonth.fromNumber(Integer.parseInt(birthday.substring(3, 5))).getLocalizedName();
			sender.sendMessage(String.format(ChatColor.LIGHT_PURPLE + this.playerBirthday, player.getName(), day, month));
		} else {
			sender.sendMessage(ChatColor.RED + this.playerNotSet);
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 && sender.hasPermission("pacbirthday.see")) {
			return PlayerSuggestionProviders.getOnlinePlayers(args[0]);
		}
		return new ArrayList<>();
	}
}
