package fr.peaceandcube.pacbirthday.command;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.file.Config;
import fr.peaceandcube.pacbirthday.util.LocalizedMonth;
import fr.peaceandcube.pacbirthday.util.PlayerMessages;
import fr.peaceandcube.pacbirthday.util.SuggestionProviders;
import fr.peaceandcube.pacbirthday.util.Utils;
import org.bukkit.Bukkit;
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

public class UpdateBirthdayCommand implements CommandExecutor, TabExecutor {
    private static final String PERM_UPDATEBIRTHDAY = "peaceandcube.updatebirthday";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(PERM_UPDATEBIRTHDAY)) {
            if (args.length == 3) {
                Player player = Bukkit.getPlayer(args[0]);

                if (player == null) {
                    sender.sendMessage(PlayerMessages.PLAYER_NOT_FOUND);
                    return true;
                }

                if (Utils.isValidBirthday(args[1], args[2])) {
                    String monthNumber = String.format("%02d", LocalizedMonth.fromString(args[2]).getNumber());
                    PACBirthday.birthdaysFile.setBirthday(player.getUniqueId().toString(), String.format("%02d", Integer.parseInt(args[1])) + "-" + monthNumber);
                    sender.sendMessage(PlayerMessages.success(String.format(Config.birthdayUpdated, player.getName())));
                } else {
                    sender.sendMessage(PlayerMessages.error(Config.birthdayInvalid));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (sender.hasPermission(PERM_UPDATEBIRTHDAY)) {
            if (args.length == 1) {
                return SuggestionProviders.getOnlinePlayers(args[0]);
            } else if (args.length == 2) {
                return IntStream.range(1, 32).mapToObj(Integer::toString).filter(s -> s.startsWith(args[1])).toList();
            } else if (args.length == 3) {
                return Arrays.stream(LocalizedMonth.values()).map(LocalizedMonth::getLocalizedName).filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase())).toList();
            }
        }
        return List.of();
    }
}
