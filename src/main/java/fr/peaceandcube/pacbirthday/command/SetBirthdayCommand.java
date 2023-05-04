package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.file.Config;
import fr.peaceandcube.pacbirthday.util.LocalizedMonth;
import fr.peaceandcube.pacbirthday.util.PlayerMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SetBirthdayCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_SETBIRTHDAY = "peaceandcube.setbirthday";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_SETBIRTHDAY)) {
			if (sender instanceof Player player) {
				if (args.length == 2) {
					if (PACBirthday.birthdaysFile.getBirthday(player.getUniqueId().toString()) == null) {
						if (this.isValidBirthday(args[0], args[1])) {
							String monthNumber = String.format("%02d", LocalizedMonth.fromString(args[1]).getNumber());
							PACBirthday.birthdaysFile.setBirthday(player.getUniqueId().toString(), String.format("%02d", Integer.parseInt(args[0])) + "-" + monthNumber);
							player.sendMessage(PlayerMessages.success(Config.birthdaySaved));
						} else {
							player.sendMessage(PlayerMessages.error(Config.birthdayInvalid));
						}
					} else {
						player.sendMessage(PlayerMessages.error(Config.birthdayAlreadySaved));
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidBirthday(String day, String month) {
		LocalizedMonth validMonth = LocalizedMonth.fromString(month);

		if (validMonth != null) {
			return Integer.parseInt(day) > 0 && Integer.parseInt(day) <= validMonth.getMaxDays();
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (sender.hasPermission(PERM_SETBIRTHDAY)) {
			if (args.length == 1) {
				return IntStream.range(1, 32).mapToObj(Integer::toString).filter(s -> s.startsWith(args[0])).toList();
			} else if (args.length == 2) {
				return Arrays.stream(LocalizedMonth.values()).map(LocalizedMonth::getLocalizedName).filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase())).toList();
			}
		}
		return List.of();
	}
}
