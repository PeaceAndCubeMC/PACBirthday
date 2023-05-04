package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.file.Config;
import fr.peaceandcube.pacbirthday.util.LocalizedMonth;
import fr.peaceandcube.pacbirthday.util.PlayerMessages;
import fr.peaceandcube.pacbirthday.util.SuggestionProviders;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BirthdayCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_BIRTHDAY = "peaceandcube.birthday";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_BIRTHDAY)) {
			if (args.length == 1) {
				String target = args[0];
				if (Bukkit.getPlayer(target) != null && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))) {
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
				sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
				return true;
			}
		}
		return false;
	}

	private void sendBirthday(CommandSender sender, OfflinePlayer player) {
		String birthday = PACBirthday.birthdaysFile.getBirthday(player.getUniqueId().toString());
		if (birthday != null) {
			String day = birthday.substring(0, 2);
			String month = LocalizedMonth.fromNumber(Integer.parseInt(birthday.substring(3, 5))).getLocalizedName();
			String message = String.format(Config.playerBirthday, player.getName(), day, month);
			sender.sendMessage(PlayerMessages.result(message));
		} else {
			sender.sendMessage(PlayerMessages.error(Config.playerNotSet));
		}
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (sender.hasPermission(PERM_BIRTHDAY)) {
			if (args.length == 1) {
				return SuggestionProviders.getOnlinePlayers(args[0]);
			}
		}
		return List.of();
	}
}
