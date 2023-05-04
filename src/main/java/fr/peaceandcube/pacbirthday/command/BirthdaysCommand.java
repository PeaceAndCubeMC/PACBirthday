package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.file.Config;
import fr.peaceandcube.pacbirthday.util.PlayerMessages;
import fr.peaceandcube.pacbirthday.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BirthdaysCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_BIRTHDAYS = "peaceandcube.birthdays";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_BIRTHDAYS)) {
			if (args.length == 0) {

				Bukkit.getScheduler().runTaskAsynchronously(PACBirthday.getPlugin(PACBirthday.class), () -> {
					List<String> players = PACBirthday.birthdaysFile.getBirthdays(Utils.getCurrentMonthDay());
					if (!players.isEmpty()) {
						StringBuilder playerNames = new StringBuilder();
						for (String player : players) {
							playerNames.append(player).append(" ");
						}

						String message = String.format(Config.birthdaysToday, players.size());
						sender.sendMessage(PlayerMessages.result(message).append(Component.text(playerNames.toString(), TextColor.color(0xFFFF55))));
					} else {
						sender.sendMessage(PlayerMessages.error(Config.noBirthdayToday));
					}
				});

				return true;
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return List.of();
	}
}
