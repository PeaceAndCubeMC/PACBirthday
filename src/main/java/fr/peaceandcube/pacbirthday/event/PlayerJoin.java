package fr.peaceandcube.pacbirthday.event;

import fr.peaceandcube.pacbirthday.PACBirthday;
import fr.peaceandcube.pacbirthday.file.Config;
import fr.peaceandcube.pacbirthday.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();

		String currentDay = Utils.getCurrentMonthDay();
		String birthday = PACBirthday.birthdaysFile.getBirthday(player.getUniqueId().toString());

		if (birthday == null) {
			player.sendMessage(Component.text(Config.birthdayNotSet, TextColor.color(0xFFAA00)).append(Component.text("/setbirthday", TextColor.color(0xFFFF00), TextDecoration.BOLD)));
		}

		if (currentDay.equals(birthday)) {
			server.broadcast(Component.text(String.format(Config.birthdayAnnouncement, player.getName())));

			this.changePlayerName(player);

			String uuid = player.getUniqueId().toString();
			int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			int lastRewardedYear = PACBirthday.birthdaysFile.getLastRewardedYear(uuid);

			// If player hasn't been rewarded this year yet
			if (currentYear > lastRewardedYear) {
				PACBirthday.birthdaysFile.incrementRewardedCount(uuid);
				PACBirthday.birthdaysFile.setLastRewardedYear(uuid, currentYear);
				
				// Execute all commands defined
				for (String command : Config.rewards) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), String.format(command, player.getName()));
				}
			}
		}
	}

	private void changePlayerName(Player player) {
		int nameLength = player.getName().length();
		TextColor[] colors = {TextColor.color(0xFF5555), TextColor.color(0xFFAA00), TextColor.color(0x55FF55)};
		Component[] nameParts = new Component[3];
		for (int i = 0; i < 3; i++) {
			int startIndex = i * Math.round((float) nameLength / 3);
			int endIndex = Math.min((i + 1) * Math.round((float) nameLength / 3), nameLength);
			nameParts[i] = Component.text(player.getName().substring(startIndex, endIndex), colors[i]);
		}
		Component newName = Component.text().append(nameParts[0]).append(nameParts[1]).append(nameParts[2]).build();
		player.playerListName(newName);
	}
}
